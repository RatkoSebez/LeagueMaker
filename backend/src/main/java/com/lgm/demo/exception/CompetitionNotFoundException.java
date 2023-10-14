package com.lgm.demo.exception;

import com.lgm.demo.model.constant.ExceptionMessageConstant;

public class CompetitionNotFoundException extends RuntimeException{
    private final Object request;

    public CompetitionNotFoundException(Object request) {
        super(ExceptionMessageConstant.COMPETITION_NOT_FOUND_MESSAGE);
        this.request = request;
    }

    public Object getRequest() {
        return request;
    }
}
