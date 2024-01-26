package com.ghkdtlwns987.catalog.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Exception.Exception.ClientException;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import com.ghkdtlwns987.catalog.Service.Inter.QueryCatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QueryCatalogsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class QueryCatalogControllerTest {
    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";


    private final String productName = "Berlin";
    private final Integer stock = 100;
    private final Integer unitprice = 1500;

    @MockBean
    QueryCatalogService queryCatalogService;
    @Mock
    QueryCatalogRepository queryCatalogRepository;

    @Autowired
    private MockMvc mockMvc;

    Catalog catalog1;
    Catalog catalog2;
    Catalog catalog3;
    ResponseCatalogDto responseCatalogDto;
    @BeforeEach
    void setUp(){
        queryCatalogRepository = Mockito.mock(QueryCatalogRepository.class);
        catalog1 = Catalog.builder()
                .productId(productId1)
                .productName(productName)
                .stock(stock)
                .unitPrice(unitprice)
                .build();
        catalog2 = Catalog.builder()
                .productId(productId2)
                .productName(productName)
                .stock(stock)
                .unitPrice(unitprice)
                .build();
        catalog3 = Catalog.builder()
                .productId(productId3)
                .productName(productName)
                .stock(stock)
                .unitPrice(unitprice)
                .build();

        responseCatalogDto = ResponseCatalogDto.fromEntity(catalog1);
    }

    @Test
    void 상품_조회_실패_유효하지_않은_productId_검색_null_반환() throws Exception {
        final String invalid_productId = "invalid-product-Id";
        // given
        Optional<Catalog> catalog;
        ResponseCatalogDto responses;

        catalog = queryCatalogRepository.findCatalogByProductId(invalid_productId);
        responses = queryCatalogService.getCatalogByProductId(invalid_productId);
        when(queryCatalogRepository.findCatalogByProductId(any())).thenReturn(catalog);
        when(queryCatalogService.getCatalogByProductId(any())).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/catalog/catalogs/" + invalid_productId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("상품 내역을 조회했습니다.")))
                .andExpect(jsonPath("$.data", equalTo(null)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void productId로_상품_조회_성공() throws Exception {
        final String invalid_productId = "invalid-product-Id";
        // given
        Optional<Catalog> catalog = Optional.ofNullable(catalog1);
        ResponseCatalogDto responses = responseCatalogDto;

        queryCatalogRepository.findCatalogByProductId(invalid_productId);
        queryCatalogService.getCatalogByProductId(invalid_productId);
        when(queryCatalogRepository.findCatalogByProductId(any())).thenReturn(catalog);
        when(queryCatalogService.getCatalogByProductId(any())).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/catalog/catalogs/" + invalid_productId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("상품 내역을 조회했습니다.")))
                .andExpect(jsonPath("$.data.productId", equalTo(responses.getProductId())))
                .andExpect(jsonPath("$.data.productName", equalTo(responses.getProductName())))
                .andExpect(jsonPath("$.data.stock", equalTo(responses.getStock())))
                .andExpect(jsonPath("$.data.unitPrice", equalTo(responses.getUnitPrice())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void 상품_조회_실패_유효하지_않은_productName_검색_빈_배열_반환() throws Exception {
        final String invalid_productName = "invalid-product-Name";
        // given
        List<ResponseCatalogDto> responses = new ArrayList<>();

        queryCatalogService.getCatalogByProductNames(invalid_productName);
        when(queryCatalogService.getCatalogByProductNames(any())).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/catalog/catalogs/" + invalid_productName)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("상품 내역을 조회했습니다.")))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
