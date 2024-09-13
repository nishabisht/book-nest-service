package com.v1.book_nest.exception;


public class ApplicationException extends RuntimeException{
    private final String message;

    public ApplicationException(String message){
        super(message);
        this.message=message;
    }

    public String getMessage(){
        return message;
    }
}
