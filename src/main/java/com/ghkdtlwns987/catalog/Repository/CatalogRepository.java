package com.ghkdtlwns987.catalog.Repository;

import com.ghkdtlwns987.catalog.Entity.Catalog;
import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<Catalog, Long> {
    /**
     * productId를 기반으로 Catalog 정보를 불러옵니다.
     * @param productId
     * @return
     */
    Catalog findByProductId(String productId);
}
