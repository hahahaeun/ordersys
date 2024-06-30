package com.example.ordersys.domain.product;

import com.example.ordersys.domain.product.exception.SoldOutException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
<<<<<<< HEAD
import lombok.*;

@Entity
@Table(name="TB_Product")
=======
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_PRODUCT")
>>>>>>> origin/master
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Product {
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock", nullable = false)
    private int stock;
<<<<<<< HEAD
=======

>>>>>>> origin/master
    public Product(Long id, String name, int price, int stock){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
<<<<<<< HEAD
    //재고상태 변경
=======
>>>>>>> origin/master
    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        }
        this.stock -= quantity;
    }
<<<<<<< HEAD
=======

>>>>>>> origin/master
}
