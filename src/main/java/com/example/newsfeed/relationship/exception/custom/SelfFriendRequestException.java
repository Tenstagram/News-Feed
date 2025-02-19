package com.example.newsfeed.relationship.exception.custom;

import com.example.newsfeed.relationship.exception.ErrorMessage;

public class SelfFriendRequestException extends RuntimeException {
    public SelfFriendRequestException() {
        super(ErrorMessage.SELF_FRIEND_REQUEST.getMessage());
    }
}
