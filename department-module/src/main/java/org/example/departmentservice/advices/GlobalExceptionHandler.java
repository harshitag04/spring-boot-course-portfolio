package org.example.departmentservice.advices;

import org.example.departmentservice.exceptions.ResourceNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<APIResponse> buildErrorResponseEntity(APIError apiError) {
        return new ResponseEntity<>(new APIResponse(apiError), apiError.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFound(ResourceNotFoundException exception){
       APIError apiError = APIError.builder()
               .status(HttpStatus.NOT_FOUND)
               .message(exception.getMessage())
               .build();
       return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleInternalServerError(Exception exception){
        APIError apiError = APIError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse> handleMethodInputValidation(MethodArgumentNotValidException exception){
        List<String> errors =
                exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        APIError apiError = APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation failed")
                .suberrors(errors)
                .build();
        return buildErrorResponseEntity(apiError);
    }
}
