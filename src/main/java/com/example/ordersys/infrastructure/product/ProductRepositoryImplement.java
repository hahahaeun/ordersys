package com.example.ordersys.infrastructure.product;

import com.example.ordersys.domain.product.Product;
import com.example.ordersys.domain.product.ProductRepository;

import java.util.Optional;

public class ProductRepositoryImplement implements ProductRepository {
ProductJpaRepository productJpaRepository;
    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id);
    }
    @Override
    public Product save(Product product){
        return productJpaRepository.save(product);
    }

}
