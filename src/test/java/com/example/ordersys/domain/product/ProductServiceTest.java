package com.example.ordersys.domain.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        when(productRepository.save(product)).thenReturn(product);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // When
        Product newProduct = productRepository.save(product);
        Optional<Product> productForSale = productRepository.findById(productId);

        // Then
        Product productInstance = productForSale.orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        assertEquals(productId, productInstance.getId());
        assertEquals(newProduct.getId(), productInstance.getId());
    }
}