package com.example.employeemanagement.controller;

import com.example.employeemanagement.controller.view.ExceptionErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionErrorResponse handleException(Exception e) throws Exception {
        logger.error(e.getMessage(),e);
        return new ExceptionErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),e.getMessage());
    }
}
