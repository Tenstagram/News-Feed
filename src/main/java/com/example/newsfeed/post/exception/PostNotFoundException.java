package com.example.newsfeed.post.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super(ErrorMessage.POST_NOT_FOUND.getMessage());
    }
}
