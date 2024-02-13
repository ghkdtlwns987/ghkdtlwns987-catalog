package com.ghkdtlwns987.catalog.Aop;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Exception.ErrorCode.ErrorCode;
import com.ghkdtlwns987.catalog.Exception.Exception.ClientException;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CatalogValidation {
    private final QueryCatalogRepository queryCatalogRepository;

    @Before("execution(* com.ghkdtlwns987.catalog.Service.Inter.CommandCatalogService.createCatalog(..)) " + "&& args(requestCatalogDto)")
    public void checkProductIdExists(RequestCatalogDto requestCatalogDto) {
        if (queryCatalogRepository.existsCatalogByProductId(requestCatalogDto.getProductId())) {
            throw new ClientException(ErrorCode.PRODUCT_ID_ALREADY_EXISTS, ErrorCode.PRODUCT_ID_NOT_EXISTS.getMessage());
        }
    }
}
