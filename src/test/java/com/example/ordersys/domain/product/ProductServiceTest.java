package com.example.ordersys.domain.product;

import com.example.ordersys.domain.product.exception.ProductNotFoundException;
import com.example.ordersys.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    @DisplayName("상품 저장 테스트")
    void testProductSave() throws InterruptedException {
        //given
        Long productId = 2L;
        Product product = Product.builder()
                .id(productId)
                .price(1000)
                .stock(29)
                .name("Test")
                .build();

        //when(productRepository.save(product)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        //when
        Long rtnId = productService.saveProduct(product);
        //then
        assertEquals(rtnId,productId);

    }

    @Test
    @DisplayName("재고 확인 테스트")
    void testProductCheck() throws Exception {
        //given
        Long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .price(1000)
                .stock(29)
                .name("Test")
                .build();


        // Mock 동작 정의
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        Product newProduct = productRepository.save(product);
        Optional<Product> productForSale = productRepository.findById(productId);

        // Then
        Product productInstance = productForSale.orElseThrow(() -> new ProductNotFoundException(productId));
        assertEquals(productId, productInstance.getId());
        assertEquals(newProduct.getId(), productInstance.getId());
    }


    @Test
    @DisplayName("재고구매 테스트")
    void testBuyProduct() throws Exception {
        //given
        Long productId = 2L;
        int intStock = 3;
        Product product = Product.builder()
                .id(productId)
                .price(1000)
                .stock(29)
                .name("Test")
                .build();


        // Mock 동작 정의
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        // ->상품 저장
        Long rtnId = productService.saveProduct(product);
        // ->상품 확인
        Optional<Product> productForSale = productRepository.findById(productId);
        // ->상품 구매
        Product buyProduct = productService.buyProduct(intStock, productId);

        // Then
        assertEquals(rtnId,productId);
        Product productInstance = productForSale.orElseThrow(() -> new ProductNotFoundException(productId));
        assertEquals(productId, productInstance.getId());
        assertEquals(buyProduct.getId(), productInstance.getId());

        assertEquals(26, buyProduct.getStock());
    }

    @Test
    @DisplayName("재고가 29개인 상품을 10개의 스레드가 3개씩 동시에 구매했을 때 하나의 구매가 실패한다.")
    void testConcurrentBuyProduct() throws Exception {
        // Given
        Long productId = 2L;
        Product product = Product.builder()
                .id(productId)
                .price(1000)
                .stock(29)
                .name("Test")
                .build();

        // Mock 동작 정의
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        //when
        Long rtnId = productService.saveProduct(product);

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
        assertEquals(rtnId, productInstance.getId());

        System.out.println(failCount.get());
        System.out.println(productInstance.getStock());
        assertEquals(1, failCount.get());
        assertThat(productInstance.getStock()).isNotEqualTo(0);
    }
}