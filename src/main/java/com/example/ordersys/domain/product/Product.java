package com.example.ordersys.domain.product;

import com.example.ordersys.domain.product.exception.SoldOutException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_PRODUCT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock", nullable = false)
    private int stock;

    public Product(Long id, String name, int price, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        }
        this.stock -= quantity;
    }

}
