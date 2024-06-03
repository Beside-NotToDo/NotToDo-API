package io.nottodo.advice;


import io.nottodo.exception.InvalidDateRangeException;
import io.nottodo.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse exceptionHandler(MethodArgumentNotValidException e) {
        
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        
        
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), "잘못된 요청입니다.");
        
        fieldErrors.forEach(err -> errorResponse.addValidation(err.getField(), err.getDefaultMessage()));
        
        return errorResponse;
    }
    
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDateRangeException.class)
    public ErrorResponse handleInvalidDateRangeException(InvalidDateRangeException e) {
        return new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage());
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage());
    }
}