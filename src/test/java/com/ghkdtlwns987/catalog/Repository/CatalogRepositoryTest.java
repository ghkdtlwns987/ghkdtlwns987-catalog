package com.ghkdtlwns987.catalog.Repository;

import com.ghkdtlwns987.catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.catalog.Entity.Catalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CatalogRepositoryTest {
    @Autowired
    CatalogRepository catalogRepository;

    private final String PRODUCT_ID_1 = "CATALOG-0001";
    private final String PRODUCT_ID_2 = "CATALOG-0002";
    private final String PRODUCT_ID_3 = "CATALOG-0003";


    private final String PRODUCT_NAME = "Berlin";
    private final Integer STOCK = 100;
    private final Integer UNITPRICE = 1500;

    Catalog catalog1;
    Catalog catalog2;
    Catalog catalog3;
    @BeforeEach
    void setUp(){
        catalog1 = Catalog.builder()
                .Id(1L)
                .productId(PRODUCT_ID_1)
                .productName(PRODUCT_NAME)
                .stock(STOCK)
                .unitPrice(UNITPRICE)
                .build();

        catalog2 = Catalog.builder()
                .Id(2L)
                .productId(PRODUCT_ID_2)
                .productName(PRODUCT_NAME)
                .stock(STOCK)
                .unitPrice(UNITPRICE)
                .build();

        catalog3 = Catalog.builder()
                .Id(3L)
                .productId(PRODUCT_ID_3)
                .productName(PRODUCT_NAME)
                .stock(STOCK)
                .unitPrice(UNITPRICE)
                .build();
    }
    @Test
    void 상품_저장_테스트() throws Exception{
        Catalog savedCatalog = catalogRepository.save(catalog1);

        assertThat(savedCatalog.getId()).isNotNull();
        assertThat(savedCatalog.getProductId()).isEqualTo(PRODUCT_ID_1);
        assertThat(savedCatalog.getProductName()).isEqualTo(PRODUCT_NAME);
        assertThat(savedCatalog.getStock()).isEqualTo(STOCK);
        assertThat(savedCatalog.getUnitPrice()).isEqualTo(UNITPRICE);
    }

    @Test
    void 상품_전체_불러오기_테스트() throws Exception{
        catalogRepository.save(catalog1);
        catalogRepository.save(catalog2);
        catalogRepository.save(catalog3);

        Iterable<Catalog> catalogs = catalogRepository.findAll();

        List<Catalog> catalogList = new ArrayList<>();
        catalogs.forEach(catalogList::add);

        // Check the size of the retrieved catalogs
        assertThat(catalogList).hasSize(3);

        // Check individual catalogs
        assertThat(catalogList.get(0)).isEqualToComparingFieldByField(catalog1);
        assertThat(catalogList.get(1)).isEqualToComparingFieldByField(catalog2);
        assertThat(catalogList.get(2)).isEqualToComparingFieldByField(catalog3);
    }
}
