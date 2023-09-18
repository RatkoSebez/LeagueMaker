package com.lgm.demo.model.exceptions;

import com.lgm.demo.model.constant.ExceptionMessageConstant;

public class EntityNotFoundException extends RuntimeException{
    private final Object request;

    public EntityNotFoundException(Object request) {
        super(ExceptionMessageConstant.ENTITY_NOT_FOUND_MESSAGE);
        this.request = request;
    }

    public Object getRequest() {
        return request;
    }
}
