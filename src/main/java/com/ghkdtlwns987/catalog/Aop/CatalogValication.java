package com.ghkdtlwns987.catalog.Aop;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Exception.Exception.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CatalogValication {
    private final QueryCatalogRepository queryCatalogRepository;

    @Before("execution(* com.ghkdtlwns987.catalog.Service.Inter.CommandCatalogService.createCatalog(..)) " + "&& args(requestCatalogDto)")
    public void checkProductIdExists(RequestCatalogDto requestCatalogDto) {
        if (queryCatalogRepository.existsCatalogByProductId(requestCatalogDto.getProductId())) {
            throw new ProductIdAlreadyExistsException();
        }
    }
}
