package com.eazybank.accounts.exceptions;

import com.eazybank.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
@ControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {

    public ResponseEntity<ErrorResponseDto>handleCustomerAlreadyExist(CustomerAlreadyExist customerAlreadyExsit,
                                                                      WebRequest webrequest){
        ErrorResponseDto responseDto = new ErrorResponseDto(
                webrequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                customerAlreadyExsit.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDto,HttpStatus.BAD_REQUEST);
    }
}
