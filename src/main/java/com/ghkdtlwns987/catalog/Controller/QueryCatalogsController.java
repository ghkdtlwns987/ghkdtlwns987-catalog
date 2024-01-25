package com.ghkdtlwns987.catalog.Controller;

import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Service.Inter.QueryCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class QueryCatalogsController {
    private final QueryCatalogService queryCatalogService;

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalogDto>> getCategories(){
        List<ResponseCatalogDto> result = queryCatalogService.getAllCatalogs();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/catalogs/{productId}")
    public ResponseEntity<ResponseCatalogDto> getCategoriesByProductId(@PathVariable String productId){
        ResponseCatalogDto result = queryCatalogService.getCatalogByProductId(productId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/catalogs/{productName}")
    public ResponseEntity<List<ResponseCatalogDto>> getCategoriesByProductName(@PathVariable String productName){
        List<ResponseCatalogDto> result = queryCatalogService.getCatalogByProductNames(productName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
