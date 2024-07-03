package com.example.ordersys.domain.product;

import com.example.ordersys.domain.product.dto.ProductDto;
import com.example.ordersys.domain.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductServiceIntegrationTest {
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("재고가 29개인 상품을 10개의 스레드가 3개씩 동시에 구매했을 때 하나의 구매가 실패한다.")
    void testConcurrentBuyProduct() throws Exception {
        // Given
        Long productId = 1L;
        Product product = Product.builder()
                .price(1000)
                .stock(29)
                .name("Test")
                .build();

        ProductDto productDto = new ProductDto(product);
        Long rtnId = productService.saveProduct(productDto);

        AtomicInteger failCount = new AtomicInteger(0); //실패 카운트 - 여러 스레드에서 카운트
        ExecutorService executorService = Executors.newFixedThreadPool(10); //10개의 고정된 스레드 풀을 생성

        var startLatch = new CountDownLatch(1);//다른 스레드들이 어떤 작업을 완료할 때까지 기다릴 수 있게 하는 동기화 도구입니다.(모든 스레드가 동시에 시작하도록)
        var endLatch = new CountDownLatch(10);//10개의 스레드가 모두 작업을 완료할 때까지 기다림

        Runnable task = () -> {
            try {
                startLatch.await();
                productService.buyProduct(3, productId);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                failCount.incrementAndGet();
            } finally {
                endLatch.countDown();
            }
        };

        // 10개 스레드 실행
        for (int i = 0; i < 10; i++) {
            executorService.submit(task);
        }

        startLatch.countDown();//모든 스레드가 동시에 시작
        endLatch.await();//10개의 스레드가 모두 작업을 완료할 때까지 기다림

        // When
        Optional<Product> productForSale = productRepository.findById(productId);

        // Then
        Product productInstance = productForSale.orElseThrow(() -> new ProductNotFoundException(productId));
        assertEquals(productId, productInstance.getId());

        System.out.println(failCount.get());
        System.out.println(productInstance.getStock());
        assertEquals(1, failCount.get());
        assertThat(productInstance.getStock()).isNotEqualTo(0);
    }
}
