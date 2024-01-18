package com.ghkdtlwns987.catalog.Service.Impl;

import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Repository.CatalogRepository;
import com.ghkdtlwns987.catalog.Service.Inter.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogServiceImpl implements CatalogService {
    private final CatalogRepository catalogRepository;

    @Override
    public List<ResponseCatalogDto> getAllCatalogs() {
        Iterable<Catalog> catalogIterable = catalogRepository.findAll();
        List<Catalog> catalogList = new ArrayList<>();
        catalogIterable.forEach(catalogList::add);

        return catalogList.stream()
                .map(ResponseCatalogDto::fromEntity)
                .collect(Collectors.toList());
    }
}



