package com.example.newsfeed.post.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class PostAlreadyDeletedException extends RuntimeException {
    public PostAlreadyDeletedException() {
        super(ErrorMessage.POST_ALREADY_DELETED.getMessage());
    }
}
