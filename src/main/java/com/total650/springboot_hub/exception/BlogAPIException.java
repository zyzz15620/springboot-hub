package com.total650.springboot_hub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//We throw this exception whenever we write some business logic or validate request params
@ResponseStatus(value = HttpStatus.NOT_FOUND) //Return NOT_FOUND by default
public class BlogAPIException extends RuntimeException {
    private HttpStatus status;
    public String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
