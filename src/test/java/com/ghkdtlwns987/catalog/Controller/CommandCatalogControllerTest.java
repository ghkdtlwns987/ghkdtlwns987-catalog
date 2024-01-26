package com.ghkdtlwns987.catalog.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Exception.Exception.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.catalog.Repository.CommandCatalogRepository;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import com.ghkdtlwns987.catalog.Service.Inter.CommandCatalogService;
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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommandCatalogController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommandCatalogControllerTest {
    private final String productId1 = "CATALOG-0001";

    private final String productName = "Berlin";

    private final Integer qty = 1000;

    private final Integer unitPrice = 3000;

    private final String orderId = "c9323a3f-246f-4a7f-9b9d-1a468ad6a18f";

    private final String userId = "1561d7eb-ab64-48a1-95c8-80d1602bd826i";
    
    RequestCatalogDto requestCatalogDto1;

    @MockBean
    CommandCatalogService commandCatalogService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;
    @Mock
    CommandCatalogRepository commandCatalogRepository;
    @Mock
    QueryCatalogRepository queryCatalogRepository;

    Catalog catalog;
    ResponseCatalogDto response1;
    @BeforeEach
    void setUp(){
        commandCatalogRepository = Mockito.mock(CommandCatalogRepository.class);
        requestCatalogDto1 = RequestCatalogDto.builder()
                .productId(productId1)
                .productName(productName)
                .qty(qty)
                .unitPrice(unitPrice)
                .orderId(orderId)
                .userId(userId)
                .build();

        catalog = Catalog.builder()
                .productId(productId1)
                .productName(productName)
                .stock(qty)
                .unitPrice(unitPrice)
                .build();

        response1 = ResponseCatalogDto.fromEntity(catalog);
    }
    
    @Test
    void 주문_생성_요청_실패_이미_존재하는_productId() throws Exception {
        // given
        RequestCatalogDto request = requestCatalogDto1;

        queryCatalogRepository.existsCatalogByProductId(productId1);
        when(queryCatalogRepository.existsCatalogByProductId(any())).thenReturn(true);
        when(commandCatalogService.createCatalog(any(RequestCatalogDto.class))).thenThrow(new ProductIdAlreadyExistsException());

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/catalog/catalogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data", equalTo(null)))
                .andExpect(jsonPath("$.errorMessage[0]", equalTo("PRODUCT ID IS ALREADY EXISTS")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(queryCatalogRepository, times(1)).existsCatalogByProductId(any());
        verify(commandCatalogService, times(1)).createCatalog(any(RequestCatalogDto.class));
        verify(commandCatalogRepository, never()).save(any(Catalog.class));
    }
    

    @Test
    void 상품_등록_성공() throws Exception {
        // given
        RequestCatalogDto request = requestCatalogDto1;
        ResponseCatalogDto response = response1;
        Catalog order = catalog;
        queryCatalogRepository.existsCatalogByProductId(productId1);
        commandCatalogRepository.save(order);
        when(queryCatalogRepository.existsCatalogByProductId(any())).thenReturn(false);
        when(commandCatalogService.createCatalog(any(RequestCatalogDto.class))).thenReturn(response);
        when(commandCatalogRepository.save(any(Catalog.class))).thenReturn(order);

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/catalog/catalogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("O001")))
                .andExpect(jsonPath("$.message", equalTo("상품 등록이 접수되었습니다.")))
                .andExpect(jsonPath("$.data.productId", equalTo(response.getProductId())))
                .andExpect(jsonPath("$.data.productName", equalTo(response.getProductName())))
                .andExpect(jsonPath("$.data.stock", equalTo(response.getStock())))
                .andExpect(jsonPath("$.data.unitPrice", equalTo(response.getUnitPrice())))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(queryCatalogRepository, times(1)).existsCatalogByProductId(any());
        verify(commandCatalogService, times(1)).createCatalog(any(RequestCatalogDto.class));
        verify(commandCatalogRepository, times(1)).save(any(Catalog.class));
    }
}
