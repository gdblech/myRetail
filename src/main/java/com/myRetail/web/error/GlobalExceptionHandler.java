package com.myRetail.web.error;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataRetrievalFailureException.class)
    public ResponseEntity<String> handleDataRetrievalException(DataRetrievalFailureException ex) {
        return new ResponseEntity<String>("Exception retrieving data: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }


}
