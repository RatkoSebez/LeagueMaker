package com.lgm.demo.exception;

import com.lgm.demo.model.constant.ExceptionMessageConstant;

public class IncorrectPasswordException extends RuntimeException{
    private final Object request;

    public IncorrectPasswordException(Object request) {
        super(ExceptionMessageConstant.INCORRECT_PASSWORD_MESSAGE);
        this.request = request;
    }

    public Object getRequest() {
        return request;
    }
}
