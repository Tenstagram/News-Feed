package com.example.newsfeed.comment.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super(ErrorMessage.USER_AUTHENTICATION_FAILED.getMessage());
    }
}
