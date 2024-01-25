package com.ghkdtlwns987.catalog.Service.Inter;

import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;

import java.util.List;

public interface QueryCatalogService {
    /**
     * 모든 카탈로그를 조회하는 메서드 입니다.
     * @return
     */
    List<ResponseCatalogDto> getAllCatalogs();

    /**
     * 상품 정보를 기반으로 카탈로그를 조회합니다.
     * @param productName
     * @return List<ResponseCatalogDto>
     */
    List<ResponseCatalogDto> getCatalogByProductNames(String productName);

    /**
     * 상품 Id를 기반으로 카탈로그를 조회힙니다.
     *
     * @param productId
     * @return List<ResponseCatalogDto>
     */
    ResponseCatalogDto getCatalogByProductId(String productId);
}
