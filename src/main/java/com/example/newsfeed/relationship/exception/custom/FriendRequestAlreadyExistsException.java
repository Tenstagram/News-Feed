package com.example.newsfeed.relationship.exception.custom;

import com.example.newsfeed.relationship.exception.ErrorMessage;

public class FriendRequestAlreadyExistsException extends RuntimeException {
    public FriendRequestAlreadyExistsException() {
        super(ErrorMessage.FRIEND_REQUEST_ALREADY_EXISTS.getMessage());
    }
}
