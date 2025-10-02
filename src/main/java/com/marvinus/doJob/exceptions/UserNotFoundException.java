package com.marvinus.doJob.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
