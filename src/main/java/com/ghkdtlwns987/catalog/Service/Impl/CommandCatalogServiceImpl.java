package com.ghkdtlwns987.catalog.Service.Impl;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Exception.Exception.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.catalog.Repository.CommandCatalogRepository;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import com.ghkdtlwns987.catalog.Service.Inter.CommandCatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandCatalogServiceImpl implements CommandCatalogService {
    private final QueryCatalogRepository queryCatalogRepository;
    private final CommandCatalogRepository commandCatalogRepository;
    @Override
    @Transactional
    public ResponseCatalogDto createCatalog(RequestCatalogDto requestCatalogDto) {
        if(productIdAlreadyExists(requestCatalogDto)){
            throw new ProductIdAlreadyExistsException();
        }

        Catalog catalog = requestCatalogDto.toEntity();
        Catalog savedCatalog = commandCatalogRepository.save(catalog);
        return ResponseCatalogDto.fromEntity(savedCatalog);
    }
    private boolean productIdAlreadyExists(RequestCatalogDto requestCatalogDto){
        if(queryCatalogRepository.existsCatalogByProductId(requestCatalogDto.getProductId())){
            return true;
        }
        return false;
    }

}
