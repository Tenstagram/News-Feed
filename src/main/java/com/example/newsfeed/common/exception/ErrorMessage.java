package com.example.newsfeed.common.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    // Relationship 관련
    FRIEND_REQUEST_AUTHORIZATION("해당 친구 요청을 수락할 권한이 없습니다."),
    FRIEND_REQUEST_ALREADY_PROCESSED("이미 처리된 친구 요청입니다."),
    BLOCKED_USER("차단된 사용자입니다."),
    FRIEND_REQUEST_ALREADY_EXISTS("이미 친구 요청을 보냈거나 상대방이 요청을 보냈습니다."),
    FRIEND_REQUEST_REJECTION_AUTHORIZATION("해당 친구 요청을 거절할 권한이 없습니다."),
    FOLLOW_NOT_FOUND("팔로우 관계가 존재하지 않습니다."),
    SELF_FRIEND_REQUEST("자신에게 친구 요청을 보낼 수 없습니다."),

    // 인증/인가 관련
    USER_AUTHENTICATION_FAILED("사용자를 인증할 수 없습니다."),
    LOGIN_REQUIRED("로그인이 필요합니다."),
    INCORRECT_PASSWORD("비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),

    // Comment 관련
    COMMENT_ALREADY_DELETED("이미 삭제된 댓글입니다."),
    COMMENT_ALREADY_LIKED("이미 좋아요한 댓글입니다."),
    LIKE_NOT_FOUND("좋아요 기록이 없습니다."),
    COMMENT_NOT_FOUND("댓글을 찾을 수 없습니다."),

    // Post 관련
    POST_NOT_FOUND("게시글을 찾을 수 없습니다."),
    POST_ALREADY_DELETED("이미 삭제된 게시글입니다."),

    // Member 관련
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    USER_DEACTIVATED("탈퇴한 회원입니다."),
    PROFILE_IMAGE_REQUIRED("프로필 이미지 URL을 입력해야 합니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

}
