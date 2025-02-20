package com.example.newsfeed.member.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super(ErrorMessage.USER_NOT_FOUND.getMessage());
    }
}
