package com.ghkdtlwns987.catalog.Exception.Exception;

import com.ghkdtlwns987.catalog.Exception.ErrorCode.ErrorCode;

public class OutOfStockExceptoin extends BuisinessException{
    public OutOfStockExceptoin(){
        super(ErrorCode.OUT_OF_STOCK);
    }
}
