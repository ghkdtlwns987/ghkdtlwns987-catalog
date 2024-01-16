package com.ghkdtlwns987.catalog.Dto;

import com.ghkdtlwns987.catalog.Entity.Catalog;
import lombok.*;


/**
 * Catalog에 사용할 Dto 입니다.
 * 각각 상품 Id, 수량, 개당 가격, 최종 가격, 주문 Id, userId 입니다.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCatalogDto
{
    /* 상품 Id */
    private String productId;

    /* 상품 명 */
    private String productName;

    /* 남은 수량 */
    private Integer qty;

    /* 개당 가격 */
    private Integer unitPrice;

    /* 주문 Id */
    private String orderId;

    /* 유저 Id */
    private String userId;

    public Catalog toEntity(){
        return Catalog.builder()
                .productId(productId)
                .productName(productName)
                .stock(qty)
                .unitPrice(unitPrice)
                .build();
    }
}
