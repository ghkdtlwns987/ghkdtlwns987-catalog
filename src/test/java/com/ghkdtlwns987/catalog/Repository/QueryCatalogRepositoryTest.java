package com.ghkdtlwns987.catalog.Repository;

import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.IntegrationTest;
import com.ghkdtlwns987.catalog.Persistent.QueryDslQueryCatalogRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@Transactional
public class QueryCatalogRepositoryTest extends IntegrationTest {
    @Autowired
    QueryCatalogRepository queryCatalogRepository;

    @Autowired
    QueryDslQueryCatalogRepository queryDslQueryCatalogRepository;

    @Autowired
    EntityManager em;

    private final String PRODUCT_ID_1 = "CATALOG-0010";
    private final String PRODUCT_ID_2 = "CATALOG-0011";
    private final String PRODUCT_ID_3 = "CATALOG-0012";


    private final String PRODUCT_NAME = "Berlin";
    private final Integer STOCK = 100;
    private final Integer UNITPRICE = 1500;

    Catalog catalog1;
    Catalog catalog2;
    Catalog catalog3;
    @BeforeEach
    void setUp(){
        catalog1 = Catalog.builder()
                .productId(PRODUCT_ID_1)
                .productName(PRODUCT_NAME)
                .stock(STOCK)
                .unitPrice(UNITPRICE)
                .build();

        catalog2 = Catalog.builder()
                .productId(PRODUCT_ID_2)
                .productName(PRODUCT_NAME)
                .stock(STOCK)
                .unitPrice(UNITPRICE)
                .build();

        catalog3 = Catalog.builder()
                .productId(PRODUCT_ID_3)
                .productName(PRODUCT_NAME)
                .stock(STOCK)
                .unitPrice(UNITPRICE)
                .build();

        em.persist(catalog1);
        em.persist(catalog2);
        em.persist(catalog3);
    }
    @Test
    void 상품_전체_불러오기_아무런_상품도_존재하지_않음(){
        em.remove(catalog1);
        em.remove(catalog2);
        em.remove(catalog3);
        List<Catalog> catalogs = queryCatalogRepository.findAllCatalogs();

        assertThat(catalogs).isEmpty();
    }

    @Test
    void productI_검색_존재하지_않는_productId() {
        final String invalid_productId = "Invalid_Product_Id";
        // when
        boolean result = queryCatalogRepository.existsCatalogByProductId(invalid_productId);

        // then
        assertThat(result).isEqualTo(false);
    }
    @Test
    void productId_이미_존재하는_productId_인지_확인() throws Exception{
        // when
        boolean result = queryCatalogRepository.existsCatalogByProductId(PRODUCT_ID_1);

        // then
        assertThat(result).isEqualTo(true);
    }
    @Test
    void productId_검색_실패_productId가_존재하지_않음(){
        final String invalid_productId = "Invalid_Product_Id";
        // when
        Optional<Catalog> catalog = queryCatalogRepository.findCatalogByProductId(invalid_productId);

        // then
        assertThat(catalog).isNotPresent();
        assertThat(catalog).isEmpty();
    }

    @Test
    void productName_검색_실패_productName이_존재하지_않음(){
        final String invalid_productName = "Invalid_Product_Name";

        // when
        List<Catalog> catalog = queryCatalogRepository.findCatalogByProductName(invalid_productName);

        // then
        assertThat(catalog).hasSize(0);
    }
    @Test
    void productId를_기반으로_상품_검색() {
        // when
        Optional<Catalog> catalog = queryCatalogRepository.findCatalogByProductId(PRODUCT_ID_1);

        // then
        assertThat(catalog).isPresent();
        assertThat(catalog.get().getProductId()).isEqualTo(PRODUCT_ID_1);
    }

    @Test
    void productName을_기반으로_상품_검색(){
        // when
        List<Catalog> catalogs = queryCatalogRepository.findCatalogByProductName(PRODUCT_NAME);

        // then
        assertThat(catalogs).hasSize(3);
        assertThat(catalogs)
                .isNotEmpty()
                .extracting(Catalog::getProductId, Catalog::getProductName, Catalog::getStock, Catalog::getUnitPrice)
                .containsExactly(
                        tuple(PRODUCT_ID_1, PRODUCT_NAME, STOCK, UNITPRICE),
                        tuple(PRODUCT_ID_2, PRODUCT_NAME, STOCK, UNITPRICE),
                        tuple(PRODUCT_ID_3, PRODUCT_NAME, STOCK, UNITPRICE)
                );
    }

    @Test
    void 상품_전체_불러오기_테스트(){
        List<Catalog> catalogs = queryCatalogRepository.findAllCatalogs();

        assertThat(catalogs)
                .isNotEmpty()
                .extracting(Catalog::getProductId, Catalog::getProductName, Catalog::getStock, Catalog::getUnitPrice)
                .containsExactly(
                        tuple(PRODUCT_ID_1, PRODUCT_NAME, STOCK, UNITPRICE),
                        tuple(PRODUCT_ID_2, PRODUCT_NAME, STOCK, UNITPRICE),
                        tuple(PRODUCT_ID_3, PRODUCT_NAME, STOCK, UNITPRICE)
                );
    }
}
