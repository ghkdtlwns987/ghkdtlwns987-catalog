package com.ghkdtlwns987.catalog.Service.Impl;

import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Exception.Exception.ClientException;
import com.ghkdtlwns987.catalog.Exception.ErrorCode.ErrorCode;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import com.ghkdtlwns987.catalog.Service.Inter.QueryCatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryQueryCatalogServiceImpl implements QueryCatalogService {
    private final QueryCatalogRepository queryCatalogRepository;

    @Override
    public List<ResponseCatalogDto> getAllCatalogs() {
        List<Catalog> catalogList = queryCatalogRepository.findAllCatalogs();
        return catalogList.stream()
                .map(ResponseCatalogDto::fromEntity)
                .toList();
    }

    @Override
    public List<ResponseCatalogDto> getCatalogByProductNames(String productName) {
        List<Catalog> catalogList = queryCatalogRepository.findCatalogByProductName(productName);
        return catalogList.stream()
                .map(ResponseCatalogDto::fromEntity)
                .toList();
    }

    @Override
    public ResponseCatalogDto getCatalogByProductId(String productId) {
        Catalog catalog = queryCatalogRepository.findCatalogByProductId(productId)
                .orElseThrow(() -> new ClientException(
                        ErrorCode.PRODUCT_ID_NOT_EXISTS,
                        //"ProductId : " + productId
                        ErrorCode.PRODUCT_ID_NOT_EXISTS.getMessage()
                ));
        return ResponseCatalogDto.fromEntity(catalog);
    }


}



