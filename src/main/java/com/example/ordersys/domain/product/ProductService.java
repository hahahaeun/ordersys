package com.example.ordersys.domain.product;


import com.example.ordersys.domain.product.exception.ProductNotFoundException;
import com.example.ordersys.domain.product.exception.SoldOutException;
import com.example.ordersys.domain.user.exception.UserJoinException;
import org.springframework.transaction.annotation.Transactional;
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
                .orElseThrow(() -> new ProductNotFoundException(productId));
        product.decreaseStock(orderQuantity);
        return product;
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(final Long productId){
        return productRepository.findById(productId);
    }

    @Transactional
    public Long saveProduct(final Product product) {
        Product newProduct = productRepository.save(product);
        return newProduct.getId();
    }
}
