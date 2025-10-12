package com.microservices.accounts.exception;

import com.microservices.accounts.dto.ErrorResponseDTO;
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

@ControllerAdvice // used to tell the controller to invoke a suitable exception handler from here
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Here we are extending it to ResponseEntity handler so that spring will get to know what to do if input validation fails
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders httpHeaders, HttpStatusCode httpStatusCode,WebRequest webRequest){
        Map<String,String> validationError= new HashMap<>();
        List<ObjectError> validationErrorList= exception.getBindingResult().getAllErrors();

        validationErrorList.forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String validationMessage=error.getDefaultMessage();
            validationError.put(fieldName,validationMessage);
        });
        return new ResponseEntity<>(validationError,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerAlreadyExistException.class) // tells the controller to execute the handler for this particular exception
    public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExistException(CustomerAlreadyExistException customerAlreadyExistException, WebRequest webRequest){
        ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(
                webRequest.getDescription(false), // gets us the api path through which the request was sent
                HttpStatus.BAD_REQUEST,
                customerAlreadyExistException.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception,WebRequest webRequest){
        ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(
          webRequest.getDescription(false),
          HttpStatus.NOT_FOUND,
          exception.getMessage(),
          LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.NOT_FOUND);
    }

    // global runtime exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception exception,WebRequest webRequest){
        ErrorResponseDTO errorResponseDTO=new ErrorResponseDTO(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
