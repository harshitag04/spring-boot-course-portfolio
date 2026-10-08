package org.example.librarymanagementservice.advices;

import org.example.librarymanagementservice.exceptions.InternalServerErrorException;
import org.example.librarymanagementservice.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    public ResponseEntity<APIResponse<?>> buildResponseEntity(APIError error){
        return new ResponseEntity<>(new APIResponse(error), error.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception){
        APIError apiError = APIError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<APIResponse<?>> handleInternalServerErrorException(InternalServerErrorException exception){
        APIError apiError = APIError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleMethodInputException(MethodArgumentNotValidException exception){
        APIError apiError = APIError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return buildResponseEntity(apiError);
    }

}
