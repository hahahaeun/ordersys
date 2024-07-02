package com.example.ordersys.domain.product;

import com.example.ordersys.domain.product.exception.ProductNotFoundException;
import com.example.ordersys.domain.product.exception.SoldOutException;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
   // @PersistenceContext
    //private EntityManager entityManager;
    private final ProductRepository productRepository;



    @Transactional
    public synchronized Product buyProduct(final int orderQuantity, final Long productId) throws SoldOutException {
        Product product = findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        //entityManager.persist(product);
        product.decreaseStock(orderQuantity);
        //entityManager.flush();
        return product;
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(final Long productId){
        logger.info("Attempting to find product with id: {}", productId);
        return productRepository.findById(productId);
    }

    @Transactional
    public Long saveProduct(final Product product) {
        Product newProduct = productRepository.save(product);
        return newProduct.getId();
    }
}
