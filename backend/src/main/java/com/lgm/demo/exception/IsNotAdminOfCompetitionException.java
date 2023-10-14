package com.lgm.demo.exception;

import com.lgm.demo.model.constant.ExceptionMessageConstant;

public class IsNotAdminOfCompetitionException extends RuntimeException{
    private final Object request;

    public IsNotAdminOfCompetitionException(Object request) {
        super(ExceptionMessageConstant.IS_NOT_ADMIN_OF_COMPETITION_MESSAGE);
        this.request = request;
    }

    public Object getRequest() {
        return request;
    }
}
