package com.ghkdtlwns987.catalog.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalogDto
{
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;


    public ResponseCatalogDto fromEntity(Catalog catalog){
        return new ResponseCatalogDto(
                catalog.getProductId(),
                catalog.getProductName(),
                catalog.getStock(),
                catalog.getUnitPrice()
        );
    }
}
