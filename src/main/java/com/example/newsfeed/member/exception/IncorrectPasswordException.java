package com.example.newsfeed.member.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super(ErrorMessage.INCORRECT_PASSWORD.getMessage());
    }
}
