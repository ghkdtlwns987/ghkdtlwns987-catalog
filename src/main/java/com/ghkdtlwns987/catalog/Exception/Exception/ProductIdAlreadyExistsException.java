package com.ghkdtlwns987.catalog.Exception.Exception;

import com.ghkdtlwns987.catalog.Exception.ErrorCode.ErrorCode;

public class ProductIdAlreadyExistsException extends BuisinessException {
    public ProductIdAlreadyExistsException(){
        super(ErrorCode.PRODUCT_ID_ALREADY_EXISTS);
    }
}