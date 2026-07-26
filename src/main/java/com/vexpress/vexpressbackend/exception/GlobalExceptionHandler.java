package com.vexpress.vexpressbackend.exception;

import com.vexpress.vexpressbackend.dto.EmployeeResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//API RESPONSE WRAPPER//
import com.vexpress.vexpressbackend.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //Invalid json body//
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleInvalidRequestBody(HttpMessageNotReadableException exception){
        String error = exception.getMostSpecificCause().getMessage();
        if(error.contains("START_OBJECT")) {
            return ApiResponse.error(
                    "Expected a JSON array but received a JSON object."
            );
        }
        return ApiResponse.error(error);
    }

    //Duplicate data @Column(unique=true)//
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleDuplicateData(
            DataIntegrityViolationException exception){

        logger.error("Database constraint violation", exception);
        return ApiResponse.error("Duplicate data found. Email may already exist.");
    }

   /* //Generic Exception Handler//
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleException(
            Exception exception){

        logger.error("Unexpected Exception", exception);
        return ApiResponse.error("Something went wrong. Please contact support.(similar to white label error)");
    }*/

    //API REPSONSE WRAPPER//
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));
        return ApiResponse.error("Validation Failed", errors);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Long> handleEmployeeNotFound(
            EmployeeNotFoundException exception) {

        return ApiResponse.error(exception.getMessage(), exception.getId());
    }

    //Method for handling UserNotFoundException and returning a standardized API response
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleUserNotFound(UserNotFoundException exception) {

        return ApiResponse.error(exception.getMessage());

    }

    //Method for invalid credentials//
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleInvalidCredentials(
            InvalidCredentialsException exception) {

        return ApiResponse.error(exception.getMessage());

    }
}