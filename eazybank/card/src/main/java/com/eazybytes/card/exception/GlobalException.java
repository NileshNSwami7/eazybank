package com.eazybytes.card.exception;

import com.eazybytes.card.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value=CardAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto>handleCardAlreadyExistsException(CardAlreadyExistsException cardAlreadyExistsException,
                                                                   WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                cardAlreadyExistsException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto>handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                           WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                resourceNotFoundException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto>handleGlobalException(Exception exception,
                                                       WebRequest webRequest){
                ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                        webRequest.getDescription(false),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        exception.getMessage(),
                        LocalDateTime.now()
                );
                return new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }

   public ResponseEntity<Object>handleMethodArgumentNotValid(MethodArgumentNotValidException
                                                             methodArgumentNotValidException,
                                                             HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             WebRequest webrequest){
        Map<String,String> validationerrors = new HashMap<>();
       List<ObjectError>validationErrorList = methodArgumentNotValidException.getBindingResult().getAllErrors();
       validationErrorList.forEach((error)->{
           String fieldName = ((FieldError) error).getField();
            String validiError = error.getDefaultMessage();
           validationerrors.put(fieldName,validiError);
       });
       return new ResponseEntity<>(validationerrors,HttpStatus.BAD_REQUEST);
   }
}
