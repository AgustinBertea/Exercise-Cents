package com.cents.exercise.controller;

import com.cents.exercise.exception.CalculatorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CalculatorControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CalculatorException.class)
    public Map<String, String> handleCalculatorException(CalculatorException ex){
        return getErrorsMap(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, String> handleMissingParams(MissingServletRequestParameterException ex) {
        return getErrorsMap(ex);
    }

    private static Map<String, String> getErrorsMap(Exception ex) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("errorMessage", ex.getMessage());
        return errorsMap;
    }
}