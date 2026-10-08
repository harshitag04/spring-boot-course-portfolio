package org.example.librarymanagementservice.exceptions;

public class InternalServerErrorException extends RuntimeException{

    public InternalServerErrorException(String message){
        super(message);
    }
}
