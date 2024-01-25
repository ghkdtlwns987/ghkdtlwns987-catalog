package com.ghkdtlwns987.catalog.Repository;

import com.ghkdtlwns987.catalog.Entity.Catalog;

import java.util.List;
import java.util.Optional;

public interface QueryCatalogRepository {
    /**
     * productId를 기반으로 상품을 찾는 기능입니다.
     * @param productId
     * @return Optional<Catalog>
     */
    Optional<Catalog> findCatalogByProductId(String productId);

    /**
     * ProductName을 기반으로 상품을 찾는 기능입니다.
     * @param productName
     * @return Optional<Catalog>
     */
    List<Catalog> findCatalogByProductName(String productName);

    /**
     * Catalog를 전체 조회합니다.
     * @return List<Catalog>
     */
    List<Catalog> findAllCatalogs();

    /**
     * productId가 이미 존재하는지 찾는 기능입니다.
     * @param productId
     * @return boolean
     */
    boolean existsCatalogByProductId(String productId);

}
