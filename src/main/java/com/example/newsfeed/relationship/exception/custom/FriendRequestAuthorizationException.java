package com.example.newsfeed.relationship.exception.custom;

import com.example.newsfeed.relationship.exception.ErrorMessage;

public class FriendRequestAuthorizationException extends RuntimeException {
    public FriendRequestAuthorizationException() {
        super(ErrorMessage.FRIEND_REQUEST_AUTHORIZATION.getMessage());
    }
}
