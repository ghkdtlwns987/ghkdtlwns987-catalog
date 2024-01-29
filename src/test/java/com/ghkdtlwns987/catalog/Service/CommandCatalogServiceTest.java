package com.ghkdtlwns987.catalog.Service;

import com.ghkdtlwns987.catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Exception.ErrorCode.ErrorCode;
import com.ghkdtlwns987.catalog.Exception.Exception.ProductIdAlreadyExistsException;
import com.ghkdtlwns987.catalog.Repository.CommandCatalogRepository;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import com.ghkdtlwns987.catalog.Service.Impl.CommandCatalogServiceImpl;

import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@EntityListeners(AuditingEntityListener.class)
@ExtendWith(MockitoExtension.class)
public class CommandCatalogServiceTest {

    private CommandCatalogRepository commandCatalogRepository;
    private CommandCatalogServiceImpl commandCatalogService;

    private final String PRODUCT_ID_1 = "CATALOG-0001";
    private final String PRODUCT_NAME = "Berlin";
    private final Integer QTY = 100;
    private final Integer UNITPRICE = 1500;
    private final String ORDERID = UUID.randomUUID().toString();
    private final String USERID = UUID.randomUUID().toString();

    Catalog catalog1;
    RequestCatalogDto requestCatalogDto1;

    @BeforeEach
    void setUp(){
        requestCatalogDto1 = RequestCatalogDto.builder()
                .productId(PRODUCT_ID_1)
                .productName(PRODUCT_NAME)
                .qty(QTY)
                .unitPrice(UNITPRICE)
                .orderId(ORDERID)
                .userId(USERID)
                .build();


        commandCatalogRepository = Mockito.mock(CommandCatalogRepository.class);
        commandCatalogService = new CommandCatalogServiceImpl(commandCatalogRepository);
    }

    @Test
    void catalog_생성_실패_이미_등록된_productId() throws Exception{
        // given
        when(commandCatalogService.createCatalog(any())).thenThrow(ProductIdAlreadyExistsException.class);
        commandCatalogService.createCatalog(requestCatalogDto1);
        commandCatalogService.createCatalog(requestCatalogDto1);

        // then
        ProductIdAlreadyExistsException error = assertThrows(ProductIdAlreadyExistsException.class,
                () -> commandCatalogService.createCatalog(requestCatalogDto1));

        assertThat(error.getErrorCode()).isEqualTo(ErrorCode.PRODUCT_ID_ALREADY_EXISTS);
        verify(commandCatalogRepository, never()).save(any(Catalog.class));
    }


    @Test
    void catalog_생성_성공(){
        // given
        Catalog savedCatalog = Catalog.builder()
                .productId(PRODUCT_ID_1)
                .productName(PRODUCT_NAME)
                .stock(QTY)
                .unitPrice(UNITPRICE)
                .build();

        when(commandCatalogRepository.save(any(Catalog.class))).thenReturn(savedCatalog);



        // when
        ResponseCatalogDto response = commandCatalogService.createCatalog(requestCatalogDto1);


        assertThat(response.getProductId()).isEqualTo(savedCatalog.getProductId());
        assertThat(response.getProductName()).isEqualTo(savedCatalog.getProductName());
        assertThat(response.getStock()).isEqualTo(savedCatalog.getStock());
        assertThat(response.getUnitPrice()).isEqualTo(savedCatalog.getUnitPrice());

        verify(commandCatalogRepository, times(1)).save(any(Catalog.class));
    }

}
