package com.example.ordersys.infrastructure.product;

import com.example.ordersys.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
