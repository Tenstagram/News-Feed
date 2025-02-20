package com.example.newsfeed.comment.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super(ErrorMessage.COMMENT_NOT_FOUND.getMessage());
    }
}
