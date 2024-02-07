package com.ghkdtlwns987.catalog.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "catalog")
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    /* 상품 Id */
    @Column(name = "product_id", nullable = false, length = 120, unique = true)
    private String productId;

    /* 상품명 */
    @Column(name = "product_name", nullable = false)
    private String productName;

    /* 재고 */
    @Column(name = "stock", nullable = false)
    private Integer stock;

    /* 상품 가격 */
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    /* 주문 시간 */
    @Column(name = "orderAt", updatable = false)
    @CreationTimestamp
    private LocalDateTime orderAt;


    @Builder
    public Catalog(Long Id, String productId, String productName, Integer stock, Integer unitPrice){
        this.Id = Id;
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.unitPrice = unitPrice;
    }

    /**
     * 재고 차감
     * @param stock
     */
    public void updateStock(Integer stock){
        this.stock = stock;
    }

    /**
     * 개당 가격 수정
     * @param unitPrice
     */
    public void updateUnitPrice(Integer unitPrice){
        this.unitPrice = unitPrice;
    }

    /**
     * 상품명 수정
     * @param productName
     */
    public void updateName(String productName){
        this.productName = productName;
    }
}
