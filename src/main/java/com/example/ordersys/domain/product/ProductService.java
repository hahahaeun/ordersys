package com.example.ordersys.domain.product;

<<<<<<< HEAD

import com.example.ordersys.domain.product.exception.ProductNotFoundException;
import com.example.ordersys.domain.product.exception.SoldOutException;
import com.example.ordersys.domain.user.exception.UserJoinException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

=======
import com.example.ordersys.domain.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
>>>>>>> origin/master
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
<<<<<<< HEAD
    private final ProductRepository productRepository;
=======
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

>>>>>>> origin/master
    @Transactional
    public synchronized Product buyProduct(final int orderQuantity, final Long productId) throws Exception {

        Product product = findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        product.decreaseStock(orderQuantity);
        return product;
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(final Long productId){
<<<<<<< HEAD
=======
        logger.info("Attempting to find product with id: {}", productId);
>>>>>>> origin/master
        return productRepository.findById(productId);
    }

    @Transactional
    public Long saveProduct(final Product product) {
        Product newProduct = productRepository.save(product);
        return newProduct.getId();
    }
}
