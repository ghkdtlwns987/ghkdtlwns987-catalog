package com.ghkdtlwns987.catalog.Controller;

import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Service.Inter.CatalogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CatalogsController {
    private final CatalogService catalogService;

    @GetMapping("/catalogs")
    public ResponseEntity<Iterable<ResponseCatalogDto>> getCategories(){
        List<ResponseCatalogDto> result = catalogService.getAllCatalogs();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}