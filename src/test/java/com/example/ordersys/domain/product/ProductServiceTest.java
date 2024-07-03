package com.example.ordersys.domain.product;

import com.example.ordersys.domain.product.dto.ProductDto;
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

        when(productRepository.save(any(Product.class))).thenReturn(product);
        //when
        ProductDto productDto = new ProductDto(product);
        Long rtnId = productService.saveProduct(productDto);
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
    @DisplayName("상품 구매 테스트")
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
        ProductDto productDto = new ProductDto(product);
        Long rtnId = productService.saveProduct(productDto);
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

}