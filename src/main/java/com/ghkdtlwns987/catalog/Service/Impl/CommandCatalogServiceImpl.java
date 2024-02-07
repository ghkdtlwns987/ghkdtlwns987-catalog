package com.ghkdtlwns987.catalog.Service.Impl;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Exception.ErrorCode.ErrorCode;
import com.ghkdtlwns987.catalog.Exception.Exception.ClientException;
import com.ghkdtlwns987.catalog.Exception.Exception.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.catalog.Repository.CommandCatalogRepository;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import com.ghkdtlwns987.catalog.Service.Inter.CommandCatalogService;
import com.ghkdtlwns987.catalog.Service.Inter.QueryCatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandCatalogServiceImpl implements CommandCatalogService {
    private final CommandCatalogRepository commandCatalogRepository;
    private final QueryCatalogRepository queryCatalogRepository;
    @Override
    @Transactional
    public ResponseCatalogDto createCatalog(RequestCatalogDto requestCatalogDto) {
        Catalog catalog = requestCatalogDto.toEntity();
        Catalog savedCatalog = commandCatalogRepository.save(catalog);
        return ResponseCatalogDto.fromEntity(savedCatalog);
    }

    @Override
    public ResponseCatalogDto updateCatalogStock(RequestCatalogDto requestCatalogDto) {
        Catalog catalog = queryCatalogRepository.findCatalogByProductId(requestCatalogDto.getProductId())
                .orElseThrow(() -> new ClientException(
                        ErrorCode.PRODUCT_ID_NOT_EXISTS,
                        ErrorCode.PRODUCT_ID_NOT_EXISTS.getMessage()
                ));
        catalog.updateStock(requestCatalogDto.getQty());
        return ResponseCatalogDto.fromEntity(catalog);
    }

    @Override
    public ResponseCatalogDto updateCatalogName(RequestCatalogDto requestCatalogDto) {
        Catalog catalog = queryCatalogRepository.findCatalogByProductId(requestCatalogDto.getProductId())
                .orElseThrow(() -> new ClientException(
                        ErrorCode.PRODUCT_ID_NOT_EXISTS,
                        ErrorCode.PRODUCT_ID_NOT_EXISTS.getMessage()
                ));
        catalog.updateName(requestCatalogDto.getProductName());
        return ResponseCatalogDto.fromEntity(catalog);
    }

    @Override
    public ResponseCatalogDto updateCatalogUnitPrice(RequestCatalogDto requestCatalogDto) {
        Catalog catalog = queryCatalogRepository.findCatalogByProductId(requestCatalogDto.getProductId())
                .orElseThrow(() -> new ClientException(
                        ErrorCode.PRODUCT_ID_NOT_EXISTS,
                        ErrorCode.PRODUCT_ID_NOT_EXISTS.getMessage()
                ));
        catalog.updateUnitPrice(requestCatalogDto.getUnitPrice());
        return ResponseCatalogDto.fromEntity(catalog);       }
}
