package com.ghkdtlwns987.catalog.Service.Impl;

import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Repository.CatalogRepository;
import com.ghkdtlwns987.catalog.Service.Inter.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;
    @Override
    public Iterable<Catalog> getAllCatalogs() {
        Iterable<Catalog> catalogs = catalogRepository.findAll();

        return catalogs;
    }
}
