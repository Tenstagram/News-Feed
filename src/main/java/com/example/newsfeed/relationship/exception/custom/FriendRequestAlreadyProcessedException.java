package com.example.newsfeed.relationship.exception.custom;

import com.example.newsfeed.common.exception.ErrorMessage;

public class FriendRequestAlreadyProcessedException extends RuntimeException {
    public FriendRequestAlreadyProcessedException() {
        super(ErrorMessage.FRIEND_REQUEST_ALREADY_PROCESSED.getMessage());
    }
}
