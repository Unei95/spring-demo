package com.github.unei95.spring.demo.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException (Long id) {
        super("Could not find user" + id);
    }

}
