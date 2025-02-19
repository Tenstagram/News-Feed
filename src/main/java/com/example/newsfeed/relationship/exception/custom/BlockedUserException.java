package com.example.newsfeed.relationship.exception.custom;

import com.example.newsfeed.relationship.exception.ErrorMessage;

public class BlockedUserException extends RuntimeException {
    public BlockedUserException() {
        super(ErrorMessage.BLOCKED_USER.getMessage());
    }
}
