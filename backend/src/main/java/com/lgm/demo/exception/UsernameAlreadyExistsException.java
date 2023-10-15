package com.lgm.demo.exception;

import com.lgm.demo.model.constant.ExceptionMessageConstant;

public class UsernameAlreadyExistsException extends RuntimeException{
    private final Object request;

    public UsernameAlreadyExistsException(Object request){
        super(ExceptionMessageConstant.USERNAME_ALREADY_EXISTS_MESSAGE);
        this.request = request;
    }

    public Object getRequest(){
        return request;
    }
}
