package com.eazybank.accounts.exceptions;

import com.eazybank.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
public class GlobalExceptions extends ResponseEntityExceptionHandler {


    public ResponseEntity<Object>handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                              HttpStatusCode status,WebRequest request){
        Map<String,String> validationErrors = new HashMap<>();
        List<ObjectError>validationErrorList  = ex.getBindingResult().getAllErrors();
        validationErrorList.forEach((error)->{
            String fieldName = ((FieldError)error).getField();
            String validMessage = error.getDefaultMessage();
            validationErrors.put(fieldName,validMessage);
        });
        return new ResponseEntity<>(validationErrors,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto>handleGlobalExceptions(Exception exception,
                                                                  WebRequest webrequest){
        ErrorResponseDto responseDto = new ErrorResponseDto(
                webrequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(CustomerAlreadyExist.class)
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>handleResouceNotFoundException(ResourceNotFoundException resourceNotFound,
                                                                       WebRequest webrequest){
    ErrorResponseDto responseDto = new ErrorResponseDto(
            webrequest.getDescription(false),
            HttpStatus.NOT_FOUND,
            resourceNotFound.getMessage(),
            LocalDateTime.now()
    );
    return new ResponseEntity<>(responseDto,HttpStatus.NOT_FOUND);
    }
}
