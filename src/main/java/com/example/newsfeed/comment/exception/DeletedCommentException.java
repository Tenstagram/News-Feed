package com.example.newsfeed.comment.exception;

public class DeletedCommentException extends RuntimeException {
    public DeletedCommentException(String message) {
        super(message);
    }
}
