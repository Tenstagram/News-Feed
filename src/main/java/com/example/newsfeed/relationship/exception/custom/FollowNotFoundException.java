package com.example.newsfeed.relationship.exception.custom;

import com.example.newsfeed.relationship.exception.ErrorMessage;

public class FollowNotFoundException extends RuntimeException {
    public FollowNotFoundException() {
        super(ErrorMessage.FOLLOW_NOT_FOUND.getMessage());
    }
}
