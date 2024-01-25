package com.ghkdtlwns987.catalog.Persistent;

import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Repository.CommandCatalogRepository;
import org.springframework.data.repository.Repository;

public interface JpaCatalogRepository extends Repository<Catalog, Long>, CommandCatalogRepository {

}
