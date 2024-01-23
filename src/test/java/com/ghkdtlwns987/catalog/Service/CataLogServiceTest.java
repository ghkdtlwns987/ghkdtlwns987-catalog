package com.ghkdtlwns987.catalog.Service;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Repository.CatalogRepository;
import com.ghkdtlwns987.catalog.Service.Impl.CatalogServiceImpl;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EntityListeners(AuditingEntityListener.class)
public class CataLogServiceTest {
    private CatalogRepository catalogRepository;
    private CatalogServiceImpl catalogService;

    private final String PRODUCT_ID_1 = "CATALOG-0001";
    private final String PRODUCT_ID_2 = "CATALOG-0002";
    private final String PRODUCT_ID_3 = "CATALOG-0003";
    private final String PRODUCT_NAME = "Berlin";
    private final Integer QTY = 100;
    private final Integer UNITPRICE = 1500;

    Catalog catalog1;
    Catalog catalog2;
    Catalog catalog3;
    RequestCatalogDto requestCatalogDto1;
    RequestCatalogDto requestCatalogDto2;
    RequestCatalogDto requestCatalogDto3;


    @BeforeEach
    void setUp(){
        requestCatalogDto1 = RequestCatalogDto.builder()
                .productId(PRODUCT_ID_1)
                .productName(PRODUCT_NAME)
                .qty(QTY)
                .unitPrice(UNITPRICE)
                .build();
        requestCatalogDto2 = RequestCatalogDto.builder()
                .productId(PRODUCT_ID_2)
                .productName(PRODUCT_NAME)
                .qty(QTY)
                .unitPrice(UNITPRICE)
                .build();
        requestCatalogDto3 = RequestCatalogDto.builder()
                .productId(PRODUCT_ID_3)
                .productName(PRODUCT_NAME)
                .qty(QTY)
                .unitPrice(UNITPRICE)
                .build();

        catalog1 = requestCatalogDto1.toEntity();
        catalog2 = requestCatalogDto2.toEntity();
        catalog3 = requestCatalogDto3.toEntity();

        catalogRepository = Mockito.mock(CatalogRepository.class);
        catalogService = new CatalogServiceImpl(catalogRepository);
    }

    @Test
    void catalog_생성_조회(){
        catalog1 = requestCatalogDto1.toEntity();
        assertThat(catalog1.getProductId()).isEqualTo(PRODUCT_ID_1);
        assertThat(catalog1.getProductName()).isEqualTo(PRODUCT_NAME);
        assertThat(catalog1.getStock()).isEqualTo(QTY);
        assertThat(catalog1.getUnitPrice()).isEqualTo(UNITPRICE);
    }
    @Test
    void 모든_상픔_카테고리_조회(){
        // Mocking repository behavior
        List<Catalog> catalogList = new ArrayList<>();
        catalogList.add(catalog1);
        catalogList.add(catalog2);
        catalogList.add(catalog3);

        when(catalogRepository.findAll()).thenReturn(catalogList);

        // Calling the service method
        List<ResponseCatalogDto> response = catalogService.getAllCatalogs();

        assertThat(response)
                .isNotEmpty()
                .hasSize(3)
                .extracting(ResponseCatalogDto::getProductId, ResponseCatalogDto::getProductName, ResponseCatalogDto::getStock, ResponseCatalogDto::getUnitPrice)
                .containsExactly(
                        tuple(PRODUCT_ID_1, PRODUCT_NAME, QTY, UNITPRICE),
                        tuple(PRODUCT_ID_2, PRODUCT_NAME, QTY, UNITPRICE),
                        tuple(PRODUCT_ID_3, PRODUCT_NAME, QTY, UNITPRICE)
                );
    }
}
