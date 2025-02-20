package com.example.newsfeed.member.exception;

import com.example.newsfeed.common.exception.ErrorMessage;

public class ProfileImageRequiredException extends RuntimeException {
    public ProfileImageRequiredException() {
        super(ErrorMessage.PROFILE_IMAGE_REQUIRED.getMessage());
    }
}
