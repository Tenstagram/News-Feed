package com.example.newsfeed.comment.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class LikeNotFoundException extends RuntimeException {
    public LikeNotFoundException() {
        super(ErrorMessage.LIKE_NOT_FOUND.getMessage());
    }
}
