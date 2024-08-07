package com.example.ordersys.domain.product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);

    Product save(Product product);

    Optional<Product> findByIdForUpdate(Long productId);
}
