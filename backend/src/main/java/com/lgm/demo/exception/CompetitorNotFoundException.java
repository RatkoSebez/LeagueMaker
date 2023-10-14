package com.lgm.demo.exception;

import com.lgm.demo.model.constant.ExceptionMessageConstant;

public class CompetitorNotFoundException extends RuntimeException{
    private final Object request;

    public CompetitorNotFoundException(Object request) {
        super(ExceptionMessageConstant.COMPETITOR_NOT_FOUND_MESSAGE);
        this.request = request;
    }

    public Object getRequest() {
        return request;
    }
}
