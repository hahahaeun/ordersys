package com.example.ordersys.controller.product;

import com.example.ordersys.domain.product.Product;
import com.example.ordersys.domain.product.ProductService;
import com.example.ordersys.domain.product.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Product getProduct(@RequestParam Long id) {
        Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return product;
    }

    @PostMapping("/save")
    public ResponseEntity<Long> saveProduct(@RequestBody Product product) {
        Long rtnId = productService.saveProduct(product);
        return ResponseEntity.ok(rtnId);
    }
}
