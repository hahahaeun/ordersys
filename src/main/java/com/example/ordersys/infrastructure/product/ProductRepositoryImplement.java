package com.example.ordersys.infrastructure.product;

import com.example.ordersys.domain.product.Product;
import com.example.ordersys.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImplement implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;

    @Override
    public Optional<Product> findById(Long id){
        return productJpaRepository.findById(id);
    }

    @Override
    public Product save(Product product){
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByIdForUpdate(Long id){return productJpaRepository.findByIdForUpdate(id);}

}
