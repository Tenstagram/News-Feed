package com.example.newsfeed.comment.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class DeletedCommentException extends RuntimeException {
    public DeletedCommentException() {
        super(ErrorMessage.COMMENT_ALREADY_DELETED.getMessage());
    }
}
