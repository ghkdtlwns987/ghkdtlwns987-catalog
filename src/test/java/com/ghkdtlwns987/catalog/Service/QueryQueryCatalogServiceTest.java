package com.ghkdtlwns987.catalog.Service;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Exception.ErrorCode.ErrorCode;
import com.ghkdtlwns987.catalog.Exception.Exception.ClientException;
import com.ghkdtlwns987.catalog.Repository.CommandCatalogRepository;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import com.ghkdtlwns987.catalog.Service.Impl.QueryQueryCatalogServiceImpl;
import com.ghkdtlwns987.catalog.Service.Inter.CommandCatalogService;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EntityListeners(AuditingEntityListener.class)
public class QueryQueryCatalogServiceTest {
    private QueryCatalogRepository queryCatalogRepository;
    private QueryQueryCatalogServiceImpl queryCatalogService;
    private CommandCatalogRepository commandCatalogRepository;

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

        commandCatalogRepository = Mockito.mock(CommandCatalogRepository.class);
        queryCatalogRepository = Mockito.mock(QueryCatalogRepository.class);
        queryCatalogService = new QueryQueryCatalogServiceImpl(queryCatalogRepository);
    }


    @Test
    void 존재하지_않는_카테고리_조회() {
        // given
        List<Catalog> catalogList = new ArrayList<>();
        when(queryCatalogRepository.findAllCatalogs()).thenReturn(catalogList);

        // when
        List<ResponseCatalogDto> response = queryCatalogService.getAllCatalogs();

        // then
        assertThat(response).hasSize(0);
    }

    @Test
    void 존재하지_않는_productName으로_상품_조회() {
        // given
        List<Catalog> catalogList = new ArrayList<>();

        // when
        when(queryCatalogRepository.findCatalogByProductName(PRODUCT_NAME)).thenReturn(catalogList);
        List<ResponseCatalogDto> responseCatalogDtos = queryCatalogService.getCatalogByProductNames(PRODUCT_NAME);

        // then
        assertThat(responseCatalogDtos).hasSize(0);
    }

    @Test
    void productName_으로_상품_조회() {
        // given
        List<Catalog> catalogList = Arrays.asList(catalog1, catalog2, catalog3);

        // when
        when(queryCatalogRepository.findCatalogByProductName(PRODUCT_NAME)).thenReturn(catalogList);
        List<ResponseCatalogDto> response = queryCatalogService.getCatalogByProductNames(PRODUCT_NAME);


        // then
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

    @Test
    void 존재하지_않는_productId로_상품_조회() {
        final String invalid_product_Id = "CATALOG-00444";
        // when
        when(queryCatalogRepository.findCatalogByProductId(invalid_product_Id))
                .thenThrow(ClientException.class);

        // then
        assertThatThrownBy(() -> queryCatalogService.getCatalogByProductId(invalid_product_Id))
                .isInstanceOf(ClientException.class);
    }

    @Test
    void productId로_상품_조회() {
        // given
        Optional<Catalog> catalogList = Optional.ofNullable(catalog1);

        // when
        when(queryCatalogRepository.findCatalogByProductId(PRODUCT_ID_1)).thenReturn(catalogList);
        ResponseCatalogDto response = queryCatalogService.getCatalogByProductId(PRODUCT_ID_1);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isEqualTo(PRODUCT_ID_1);
        assertThat(response.getProductName()).isEqualTo(PRODUCT_NAME);
        assertThat(response.getStock()).isEqualTo(QTY);
        assertThat(response.getUnitPrice()).isEqualTo(UNITPRICE);
    }

    @Test
    void 모든_상픔_카테고리_조회(){
        // given
        List<Catalog> catalogList = Arrays.asList(catalog1, catalog2, catalog3);
        when(queryCatalogRepository.findAllCatalogs()).thenReturn(catalogList);

        // Calling the service method
        List<ResponseCatalogDto> response = queryCatalogService.getAllCatalogs();

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
