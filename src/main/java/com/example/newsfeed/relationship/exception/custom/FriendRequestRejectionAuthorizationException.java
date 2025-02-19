package com.example.newsfeed.relationship.exception.custom;

import com.example.newsfeed.relationship.exception.ErrorMessage;

public class FriendRequestRejectionAuthorizationException extends RuntimeException {
    public FriendRequestRejectionAuthorizationException() {
        super(ErrorMessage.FRIEND_REQUEST_REJECTION_AUTHORIZATION.getMessage());
    }
}
