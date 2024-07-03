package com.example.ordersys.controller.product;

import com.example.ordersys.domain.product.Product;
import com.example.ordersys.domain.product.ProductService;
import com.example.ordersys.domain.product.dto.ProductDto;
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
    @PostMapping("/getTest")
    public String test(@RequestBody ProductDto productDto){
        return productDto.toString();
    }
    //1. 상품 추가, 삭제, 확인, 리스트, 테스트

    //상품 확인
    @GetMapping("/{id}")
    public ProductDto getItem(@PathVariable Long id){
         Product product = productService.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
         return new ProductDto(product);
    }
    //상품 추가
    @PostMapping("/save")
    public ResponseEntity<Long> save(@RequestBody ProductDto productDto){
        Long rtnId = productService.saveProduct(productDto);
        return ResponseEntity.ok(rtnId);
    }
    //2. 동시성 테스트
}
