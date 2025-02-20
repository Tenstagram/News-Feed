package com.example.newsfeed.comment.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class CommentAlreadyLikedException extends RuntimeException {
    public CommentAlreadyLikedException() {
        super(ErrorMessage.COMMENT_ALREADY_LIKED.getMessage());
    }
}
