package com.lgm.demo.exception;

import com.lgm.demo.exception.*;
import com.lgm.demo.model.constant.ExceptionMessageConstant;
import com.lgm.demo.model.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler{

    // custom response when validation fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                ExceptionMessageConstant.METHOD_ARGUMENT_NOT_VALID_MESSAGE,
                exception.getClass().getSimpleName(),
                exception.getTarget()
        );
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        for(FieldError fieldError: fieldErrors)
            apiErrorResponse.addFieldError(fieldError);
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST,
                ExceptionMessageConstant.METHOD_ARGUMENT_NOT_VALID_MESSAGE,
                exception.getClass().getSimpleName(),
                "Input is List"
        );
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(value=EntityNotFoundException.class)
    public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                exception.getRequest()
        );
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(value=UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> usernameAlreadyExistsException(UsernameAlreadyExistsException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                exception.getRequest()
        );
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(value=EmailAlreadyExistsException.class)
    public ResponseEntity<Object> emailAlreadyExistsException(EmailAlreadyExistsException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                exception.getRequest()
        );
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(value=IncorrectPasswordException.class)
    public ResponseEntity<Object> incorrectPasswordException(IncorrectPasswordException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                exception.getRequest()
        );
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(value=CompetitionNotFoundException.class)
    public ResponseEntity<Object> competitionNotFoundException(CompetitionNotFoundException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                exception.getRequest()
        );
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(value=CompetitorNotFoundException.class)
    public ResponseEntity<Object> competitorNotFoundException(CompetitorNotFoundException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                exception.getRequest()
        );
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(value=InvalidMatchScoreException.class)
    public ResponseEntity<Object> invalidMatchScoreException(InvalidMatchScoreException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                exception.getRequest()
        );
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }

    @ExceptionHandler(value=IsNotAdminOfCompetitionException.class)
    public ResponseEntity<Object> isNotAdminOfCompetitionException(IsNotAdminOfCompetitionException exception){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                exception.getRequest()
        );
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getStatus());
    }
}
