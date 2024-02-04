package com.ghkdtlwns987.catalog.Exception.Exception;

import com.ghkdtlwns987.catalog.Exception.ErrorCode.ErrorCode;

public class ProductIdNotExistsException extends BuisinessException{
    public ProductIdNotExistsException(){
        super(ErrorCode.PRODUCT_ID_NOT_EXISTS);
    }

}
