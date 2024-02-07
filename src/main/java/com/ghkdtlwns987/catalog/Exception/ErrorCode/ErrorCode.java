package com.ghkdtlwns987.catalog.Exception.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ORDER_ID_ALREADY_EXISTS("C001", HttpStatus.INTERNAL_SERVER_ERROR, "ORDER ID IS ALREADY EXISTS"),
    PRODUCT_ID_ALREADY_EXISTS("C002", HttpStatus.INTERNAL_SERVER_ERROR, "PRODUCT ID IS ALREADY EXISTS"),
    PRODUCT_ID_NOT_EXISTS("C003", HttpStatus.INTERNAL_SERVER_ERROR, "PRODUCT ID IS NOT EXISTS"),

    USERID_IS_NOT_EXISTS("O001", HttpStatus.BAD_REQUEST, "USER ID NOT EXISTS"),
    ORDER_ID_NOT_EXISTS("O001", HttpStatus.BAD_REQUEST, "ORDER ID NOT EXISTS"),
    INVALID_TYPE_VALUE("C004", HttpStatus.BAD_REQUEST, "INVALID TYPE VALUE"),
    OUT_OF_STOCK("C005", HttpStatus.BAD_REQUEST, "OUT OF STOCK"),

    ;

    private final String code;
    private final HttpStatus status;
    private final String message;
}
