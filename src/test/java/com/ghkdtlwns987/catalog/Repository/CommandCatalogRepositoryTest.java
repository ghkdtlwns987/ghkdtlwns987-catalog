package com.ghkdtlwns987.catalog.Repository;

import com.ghkdtlwns987.catalog.Entity.Catalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CommandCatalogRepositoryTest {
    @Autowired
    CommandCatalogRepository commandCatalogRepository;

    private final String PRODUCT_ID_1 = "CATALOG-0001";
    private final String PRODUCT_ID_2 = "CATALOG-0002";
    private final String PRODUCT_ID_3 = "CATALOG-0003";


    private final String PRODUCT_NAME = "Berlin";
    private final Integer STOCK = 100;
    private final Integer UNITPRICE = 1500;

    Catalog catalog1;
    @BeforeEach
    void setUp(){
        catalog1 = Catalog.builder()
                .Id(1L)
                .productId(PRODUCT_ID_1)
                .productName(PRODUCT_NAME)
                .stock(STOCK)
                .unitPrice(UNITPRICE)
                .build();
    }
    @Test
    void 상품_저장_테스트() {
        Catalog savedCatalog = commandCatalogRepository.save(catalog1);

        assertThat(savedCatalog.getId()).isNotNull();
        assertThat(savedCatalog.getProductId()).isEqualTo(PRODUCT_ID_1);
        assertThat(savedCatalog.getProductName()).isEqualTo(PRODUCT_NAME);
        assertThat(savedCatalog.getStock()).isEqualTo(STOCK);
        assertThat(savedCatalog.getUnitPrice()).isEqualTo(UNITPRICE);
    }
}
