package com.ghkdtlwns987.catalog.Global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // Order
    REGISTER_CATALOG_REQUEST_SUCCESS(200, "O001", "상품 등록이 접수되었습니다."),
    UPDATE_CATALOG_REQUEST_SUCCESS(200, "O002", "상품 정보가 수정되었습니다."),
    GET_CATALOG_REQUEST_SUCCESS(200, "O001", "상품 내역을 조회했습니다."),
    ;
    private int status;
    private final String code;
    private final String message;
}