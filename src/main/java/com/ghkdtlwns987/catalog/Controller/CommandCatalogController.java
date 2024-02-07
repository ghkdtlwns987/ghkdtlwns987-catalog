package com.ghkdtlwns987.catalog.Controller;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Global.ResultCode;
import com.ghkdtlwns987.catalog.Global.ResultResponse;
import com.ghkdtlwns987.catalog.Service.Inter.CommandCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CommandCatalogController {
    private final CommandCatalogService commandCatalogService;

    @PostMapping("/catalogs")
    public ResponseEntity<ResultResponse> createCatalog(@RequestBody RequestCatalogDto request) {
        ResponseCatalogDto result = commandCatalogService.createCatalog(request);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.REGISTER_CATALOG_REQUEST_SUCCESS, result);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponse);
    }
}
