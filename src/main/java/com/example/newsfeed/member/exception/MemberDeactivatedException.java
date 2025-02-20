package com.example.newsfeed.member.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class MemberDeactivatedException extends RuntimeException {
    public MemberDeactivatedException() {
        super(ErrorMessage.USER_DEACTIVATED.getMessage());
    }
}
