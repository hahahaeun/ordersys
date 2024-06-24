package com.example.ordersys.domain.product;

import com.example.ordersys.domain.user.exception.UserJoinException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public synchronized Product buyProduct(final int orderQuantity, final Long productId) throws Exception {
        Product product = findById(productId)
                .orElseThrow();
        product.decreaseStock(orderQuantity);
        return product;
    }

    @Transactional
    public Optional<Product> findById(final Long productId){
        return productRepository.findById(productId);
    }
}
