package com.ghkdtlwns987.catalog.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Service.Inter.QueryCatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QueryCatalogsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class QueryCatalogsControllerTest {
    private final String productId1 = "CATALOG-0001";
    private final String productId2 = "CATALOG-0002";
    private final String productId3 = "CATALOG-0003";


    private final String productName = "Berlin";
    private final Integer stock = 100;
    private final Integer unitprice = 1500;

    @MockBean
    QueryCatalogService queryCatalogService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Catalog catalog1;
    Catalog catalog2;
    Catalog catalog3;
    ResponseCatalogDto responseCatalogDto1;
    ResponseCatalogDto responseCatalogDto2;
    ResponseCatalogDto responseCatalogDto3;
    List<ResponseCatalogDto> responses;

    @BeforeEach
    void setUp(){
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

        responseCatalogDto1 = ResponseCatalogDto.fromEntity(catalog1);
        responseCatalogDto2 = ResponseCatalogDto.fromEntity(catalog2);
        responseCatalogDto3 = ResponseCatalogDto.fromEntity(catalog3);

        responses = Arrays.asList(responseCatalogDto1, responseCatalogDto2, responseCatalogDto3);
    }

    @Test
    void 상품_조회_실패_유효하지_않은_productId_검색_null_반환() throws Exception {
        final String invalid_productId = "invalid-product-Id";
        // given
        ResponseCatalogDto responses;

        responses = queryCatalogService.getCatalogByProductId(invalid_productId);
        when(queryCatalogService.getCatalogByProductId(any())).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/catalogs/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid_productId))
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
        ResponseCatalogDto responses = responseCatalogDto1;

        queryCatalogService.getCatalogByProductId(invalid_productId);
        when(queryCatalogService.getCatalogByProductId(any())).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/catalogs/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid_productId))
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
        queryCatalogService.getCatalogByProductNames(productName);
        when(queryCatalogService.getCatalogByProductNames(any())).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/catalogs/search/" + invalid_productName)
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

    @Test
    void 상품_전체조회_실패_빈_배열_반환() throws Exception {
        // given
        List<ResponseCatalogDto> responses = new ArrayList<>();

        // when
        queryCatalogService.getAllCatalogs();
        when(queryCatalogService.getAllCatalogs()).thenReturn(responses);

        // then
        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/catalogs")
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("상품 내역을 조회했습니다.")))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(0)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void 상품_조회_성공_유효한_productName_검색() throws Exception {
        // given
        List<ResponseCatalogDto> responses = Arrays.asList(responseCatalogDto1, responseCatalogDto2, responseCatalogDto3);

        queryCatalogService.getCatalogByProductNames(productName);
        when(queryCatalogService.getCatalogByProductNames(any())).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/catalogs/search/" + productName)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("상품 내역을 조회했습니다.")))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(3)))

                .andExpect(jsonPath("$.data[0].productId", equalTo(responses.get(0).getProductId())))
                .andExpect(jsonPath("$.data[0].productName", equalTo(responses.get(0).getProductName())))
                .andExpect(jsonPath("$.data[0].stock", equalTo(responses.get(0).getStock())))
                .andExpect(jsonPath("$.data[0].unitPrice", equalTo(responses.get(0).getUnitPrice())))

                .andExpect(jsonPath("$.data[1].productId", equalTo(responses.get(1).getProductId())))
                .andExpect(jsonPath("$.data[1].productName", equalTo(responses.get(1).getProductName())))
                .andExpect(jsonPath("$.data[1].stock", equalTo(responses.get(1).getStock())))
                .andExpect(jsonPath("$.data[1].unitPrice", equalTo(responses.get(1).getUnitPrice())))

                .andExpect(jsonPath("$.data[2].productId", equalTo(responses.get(2).getProductId())))
                .andExpect(jsonPath("$.data[2].productName", equalTo(responses.get(2).getProductName())))
                .andExpect(jsonPath("$.data[2].stock", equalTo(responses.get(2).getStock())))
                .andExpect(jsonPath("$.data[2].unitPrice", equalTo(responses.get(2).getUnitPrice())))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void 전체_상품_조회_성공() throws Exception {
        // given
        List<ResponseCatalogDto> responses = Arrays.asList(responseCatalogDto1, responseCatalogDto2, responseCatalogDto3);

        queryCatalogService.getAllCatalogs();
        when(queryCatalogService.getAllCatalogs()).thenReturn(responses);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/catalogs")
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("상품 내역을 조회했습니다.")))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(3)))

                .andExpect(jsonPath("$.data[0].productId", equalTo(responses.get(0).getProductId())))
                .andExpect(jsonPath("$.data[0].productName", equalTo(responses.get(0).getProductName())))
                .andExpect(jsonPath("$.data[0].stock", equalTo(responses.get(0).getStock())))
                .andExpect(jsonPath("$.data[0].unitPrice", equalTo(responses.get(0).getUnitPrice())))

                .andExpect(jsonPath("$.data[1].productId", equalTo(responses.get(1).getProductId())))
                .andExpect(jsonPath("$.data[1].productName", equalTo(responses.get(1).getProductName())))
                .andExpect(jsonPath("$.data[1].stock", equalTo(responses.get(1).getStock())))
                .andExpect(jsonPath("$.data[1].unitPrice", equalTo(responses.get(1).getUnitPrice())))

                .andExpect(jsonPath("$.data[2].productId", equalTo(responses.get(2).getProductId())))
                .andExpect(jsonPath("$.data[2].productName", equalTo(responses.get(2).getProductName())))
                .andExpect(jsonPath("$.data[2].stock", equalTo(responses.get(2).getStock())))
                .andExpect(jsonPath("$.data[2].unitPrice", equalTo(responses.get(2).getUnitPrice())))

                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
