package com.lgm.demo.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiErrorResponse{
    private HttpStatus status;
    private Integer statusCode;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private Object request;
    private String exceptionType;
    private List<FieldError> fieldErrors = new ArrayList<>();
    // private String path; // TODO bilo bi lepo da dodas ovo u poruku greske (url koji je pogodjen)

    private ApiErrorResponse(){
        timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(HttpStatus status, String message, String exceptionType, Object request){
        this();
        this.status = status;
        statusCode = status.value();
        this.message = message;
        this.exceptionType = exceptionType;
        this.request = request;
    }

    public void addFieldError(FieldError fieldError){
        Object[] arguments = new Object[1];
        FieldError error = new FieldError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                false,
                fieldError.getCodes(),
                arguments,
                fieldError.getDefaultMessage()
        );
        fieldErrors.add(error);
    }
}
