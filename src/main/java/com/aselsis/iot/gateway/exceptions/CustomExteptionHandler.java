package com.aselsis.iot.gateway.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExteptionHandler {

    @ExceptionHandler(CustomerNotNullExteption.class)
    public ResponseEntity<?> customerNotNullException(CustomerNotNullExteption customerNotNullExteption){
        List<String> detail = new ArrayList<>();
        detail.add(customerNotNullExteption.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("User Bulunamadi, User Not Null",detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNameExistsException.class)
    public ResponseEntity<?> userNameExistsException(UserNameExistsException e){
        List<String> detail = new ArrayList<>();
        detail.add(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Username already exists",detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CantDeleteUserException.class)
    public ResponseEntity<?> cantDeleteUserException(CantDeleteUserException e){
        List<String> detail = new ArrayList<>();
        detail.add(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("The user cannot be deleted or has already been deleted.",detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NotValidDateException.class)
    public ResponseEntity<?> notValidDateException(NotValidDateException e){
        List<String> detail = new ArrayList<>();
        detail.add(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "You entered the date in the wrong format. It must be entered as YYYY-MM-DD.",detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CantPutUserException.class)
    public ResponseEntity<?> cantPutUserException(CantPutUserException e){
        List<String> detail = new ArrayList<>();
        detail.add(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "Can't find a user to update. ",detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CantDeleteDAKException.class)
    public ResponseEntity<?> cantDeleteDAKException(CantDeleteDAKException e){
        List<String> detail = new ArrayList<>();
        detail.add(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "Can't find a user to delete. ",detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(CantFindApiKeyException.class)
    public ResponseEntity<?> cantFindApiKeyException(CantFindApiKeyException e){
        List<String> detail = new ArrayList<>();
        detail.add(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
                "Can't find Api Key",detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }



}
