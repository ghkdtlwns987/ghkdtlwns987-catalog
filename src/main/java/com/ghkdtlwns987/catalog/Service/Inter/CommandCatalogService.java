package com.ghkdtlwns987.catalog.Service.Inter;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;

public interface CommandCatalogService {
    /**
     * 상품을 생성합니다.
     * @param requestCatalogDto
     * @return
     */
    ResponseCatalogDto createCatalog(RequestCatalogDto requestCatalogDto);


    /**
     * 재고를 수정합니다.
     * 주문이 완료되면 개수를 줄입니다.
     * @param requestCatalogDto
     * @return
     */
    ResponseCatalogDto updateCatalogStock(RequestCatalogDto requestCatalogDto);

    /**
     * 상품명을 수정합니다.
     * @param requestCatalogDto
     * @return
     */

    ResponseCatalogDto updateCatalogName(RequestCatalogDto requestCatalogDto);

    /**
     * 상품 개수를 수정합니다.
     * @param requestCatalogDto
     * @return
     */

    ResponseCatalogDto updateCatalogUnitPrice(RequestCatalogDto requestCatalogDto);

}
