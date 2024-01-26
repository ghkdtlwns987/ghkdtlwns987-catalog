package com.ghkdtlwns987.catalog.Controller;

import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Global.ResultCode;
import com.ghkdtlwns987.catalog.Global.ResultListResponse;
import com.ghkdtlwns987.catalog.Global.ResultResponse;
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
    public ResponseEntity<ResultListResponse> getCategories(){
        List<ResponseCatalogDto> result = queryCatalogService.getAllCatalogs();
        ResultListResponse resultListResponse = ResultListResponse.of(ResultCode.GET_CATALOG_REQUEST_SUCCESS, result);
        return ResponseEntity.status(HttpStatus.OK).body(resultListResponse);
    }

    @PostMapping("/catalogs/{productId}")
    public ResponseEntity<ResultResponse> getCategoriesByProductId(@PathVariable String productId){
        ResponseCatalogDto result = queryCatalogService.getCatalogByProductId(productId);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.GET_CATALOG_REQUEST_SUCCESS, result);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }

    @GetMapping("/catalogs/{productName}")
    public ResponseEntity<ResultListResponse> getCategoriesByProductName(@PathVariable String productName){
        List<ResponseCatalogDto> result = queryCatalogService.getCatalogByProductNames(productName);
        ResultListResponse resultListResponse = ResultListResponse.of(ResultCode.GET_CATALOG_REQUEST_SUCCESS, result);
        return ResponseEntity.status(HttpStatus.OK).body(resultListResponse);
    }
}
