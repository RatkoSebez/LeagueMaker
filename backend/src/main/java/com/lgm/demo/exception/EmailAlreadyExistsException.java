package com.lgm.demo.exception;

import com.lgm.demo.model.constant.ExceptionMessageConstant;

public class EmailAlreadyExistsException extends RuntimeException{
    private final Object request;

    public EmailAlreadyExistsException(Object request){
        super(ExceptionMessageConstant.EMAIL_ALREADY_EXISTS_MESSAGE);
        this.request = request;
    }

    public Object getRequest(){
        return request;
    }
}
