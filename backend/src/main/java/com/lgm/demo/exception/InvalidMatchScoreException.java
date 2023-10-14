package com.lgm.demo.exception;

import com.lgm.demo.model.constant.ExceptionMessageConstant;

public class InvalidMatchScoreException extends RuntimeException{
    private final Object request;

    public InvalidMatchScoreException(Object request) {
        super(ExceptionMessageConstant.INVALID_MATCH_SCORE_MESSAGE);
        this.request = request;
    }

    public Object getRequest() {
        return request;
    }
}
