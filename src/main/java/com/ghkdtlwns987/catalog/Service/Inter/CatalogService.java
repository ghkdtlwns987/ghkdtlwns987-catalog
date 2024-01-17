package com.ghkdtlwns987.catalog.Service.Inter;

import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;

import java.util.List;

public interface CatalogService {
    /**
     * 모든 카탈로그를 조회하는 메서드 입니다.
     * @return
     */
    List<ResponseCatalogDto> getAllCatalogs();
}