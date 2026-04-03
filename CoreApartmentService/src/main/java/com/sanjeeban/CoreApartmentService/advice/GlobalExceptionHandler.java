package com.sanjeeban.CoreApartmentService.advice;

import com.sanjeeban.CoreApartmentService.exceptions.CustomExcelDataValidationException;
import com.sanjeeban.CoreApartmentService.exceptions.CustomGenericException;
import com.sanjeeban.CoreApartmentService.exceptions.CustomInvalidCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomInvalidCredentialsException.class)
    public Map<String,Object> handleInvalidCredentials(CustomInvalidCredentialsException ex){
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("responseCode", "401");
        errorMap.put("responseMessage", ex.getMessage());
        errorMap.put("exceptionCause", "Invalid Credentials");
        errorMap.put("timestamp", LocalDateTime.now());

        return errorMap;
    }

    @ExceptionHandler(CustomExcelDataValidationException.class)
    public Map<String,Object> handleInvalidExcelDataValidation(CustomExcelDataValidationException ex){
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("responseCode", "401");
        errorMap.put("responseMessage", ex.getMessage());
        errorMap.put("exceptionCause", "Invalid Data Value for Excel Upload");
        errorMap.put("timestamp", LocalDateTime.now());

        return errorMap;
    }



    @ExceptionHandler(CustomGenericException.class)
    public Map<String,Object> handleCustomGenericException(CustomGenericException ex){
        Map<String, Object> errorMap = new HashMap<>();

        errorMap.put("responseCode", "401");
        errorMap.put("responseMessage", ex.getMessage());
        errorMap.put("exceptionCause", "Error while processing request.");
        errorMap.put("timestamp", LocalDateTime.now());

        return errorMap;
    }
    //

}
