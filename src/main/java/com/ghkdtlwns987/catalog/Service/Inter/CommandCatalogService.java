package com.ghkdtlwns987.catalog.Service.Inter;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;

public interface CommandCatalogService {
    ResponseCatalogDto createCatalog(RequestCatalogDto orderRequestDto);

}
