package com.ghkdtlwns987.catalog.Persistent;

import com.ghkdtlwns987.catalog.Entity.Catalog;
import com.ghkdtlwns987.catalog.Entity.QCatalog;
import com.ghkdtlwns987.catalog.Repository.QueryCatalogRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class QueryDslQueryCatalogRepository implements QueryCatalogRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Catalog> findCatalogByProductId(String productId) {
        QCatalog qCatalog = QCatalog.catalog;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qCatalog)
                .where(qCatalog.productId.eq(productId))
                .fetchFirst());
    }

    @Override
    public List<Catalog> findCatalogByProductName(String productName) {
        QCatalog qCatalog = QCatalog.catalog;
        return jpaQueryFactory.selectFrom(qCatalog)
                .where(qCatalog.productName.eq(productName))
                .fetch();
    }

    @Override
    public List<Catalog> findAllCatalogs() {
        QCatalog qCatalog = QCatalog.catalog;
        return jpaQueryFactory
                .selectFrom(qCatalog)
                .fetch();
    }

    @Override
    public boolean existsCatalogByProductId(String productId) {
        QCatalog qCatalog = QCatalog.catalog;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qCatalog)
                .where(qCatalog.productId.eq(productId))
                .fetchFirst()).isPresent();
    }
}
