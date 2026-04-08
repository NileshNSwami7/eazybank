package com.eazybytes.loans.exception;

import com.eazybytes.loans.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public ResponseEntity<Object>handleMethodArgumentNotHandle(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest ){
        Map<String,String>validationerrors = new HashMap<>();
        List<ObjectError>validationList = ex.getBindingResult().getAllErrors();
        validationList.forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String validationMessage = error.getDefaultMessage();
            validationerrors.put(fieldName,validationMessage);
        });
        return new ResponseEntity<>(validationerrors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto>handleGlobalException(Exception exception,
                                                                 WebRequest webrequest){
        ErrorResponseDto responseDto = new ErrorResponseDto(
                webrequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = LoanAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDto>handleLoanAlreadyExistException(LoanAlreadyExistException loanAlreadyExistException,
                                                               WebRequest webRequest) {
        ErrorResponseDto responseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                loanAlreadyExistException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value=ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>handleResourceNotFoundExeption(ResourceNotFoundException resourceNotFoundException,
                                                                          WebRequest webRequest) {
        ErrorResponseDto responseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                resourceNotFoundException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
}
