package com.example.ordersys.domain.product.dto;

import com.example.ordersys.domain.product.Product;
import com.example.ordersys.domain.user.dto.UserDto;

public record ProductDto(
        Long id,
        String name,
        int price,
        int stock
) {
    public ProductDto(Product product){
        this(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }

    public static ProductDto of(Product product){
        return new ProductDto(product);
    }

    public static ProductDto from(Product product){
        return new ProductDto(product);
    }
    public Product toEntity(){
        return Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }

//    public static UserDto of(Long idx, String loginId, String password) {
//        return new UserDto(idx, loginId, password);
//    }
//    public static UserDto of(String loginId, String password) {
//        return new UserDto(null, loginId, password);
//    }
}
