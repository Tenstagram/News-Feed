package com.example.newsfeed.relationship.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    FRIEND_REQUEST_AUTHORIZATION("해당 친구 요청을 수락할 권한이 없습니다."),
    FRIEND_REQUEST_ALREADY_PROCESSED("이미 처리된 친구 요청입니다."),
    BLOCKED_USER("차단된 사용자입니다."),
    FRIEND_REQUEST_ALREADY_EXISTS("이미 친구 요청을 보냈거나 상대방이 요청을 보냈습니다."),
    FRIEND_REQUEST_REJECTION_AUTHORIZATION("해당 친구 요청을 거절할 권한이 없습니다."),
    FOLLOW_NOT_FOUND("팔로우 관계가 존재하지 않습니다."),
    SELF_FRIEND_REQUEST("자신에게 친구 요청을 보낼 수 없습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

}
