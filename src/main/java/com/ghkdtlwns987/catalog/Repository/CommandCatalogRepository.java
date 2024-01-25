package com.ghkdtlwns987.catalog.Repository;

import com.ghkdtlwns987.catalog.Entity.Catalog;
import org.springframework.data.repository.CrudRepository;

public interface CommandCatalogRepository extends CrudRepository<Catalog, Long> {
    /**
     * 상품을 저장합니다.
     * @param catalog
     * @return Catalog
     */
    Catalog save(Catalog catalog);
}
