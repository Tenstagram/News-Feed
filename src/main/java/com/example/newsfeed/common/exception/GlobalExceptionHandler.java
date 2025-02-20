package com.example.newsfeed.common.exception;

import com.example.newsfeed.comment.exception.*;
import com.example.newsfeed.member.exception.IncorrectPasswordException;
import com.example.newsfeed.member.exception.MemberDeactivatedException;
import com.example.newsfeed.member.exception.MemberNotFoundException;
import com.example.newsfeed.member.exception.ProfileImageRequiredException;
import com.example.newsfeed.post.exception.PostAlreadyDeletedException;
import com.example.newsfeed.post.exception.PostNotFoundException;
import com.example.newsfeed.relationship.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 전역 인증 관리 핸들
// 예외 발생 흐름: 각 코드에서 예외 발생 -> 각 예외 클래스로 넘어감 -> GlobalExceptionHandler 으로 넘어와서 최종적으로 예외 던짐
@ControllerAdvice
public class GlobalExceptionHandler {

    // 삭제된 게시물 예외
    @ExceptionHandler(PostAlreadyDeletedException.class)
    public ResponseEntity<ErrorResponse> PostAlreadyDeletedException(PostAlreadyDeletedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // 게시물을 찾을 수 없을 때 예외
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> PostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    // 댓글을 찾을 수 없을 때 예외
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> CommentNotFoundException(CommentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    // 삭제된 댓글 예외
    @ExceptionHandler(DeletedCommentException.class)
    public ResponseEntity<ErrorResponse> DeletedCommentException(DeletedCommentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // 댓글 중복 좋아요 관련 예외
    @ExceptionHandler(CommentAlreadyLikedException.class)
    public ResponseEntity<ErrorResponse> CommentAlreadyLikedException(CommentAlreadyLikedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    // 좋아요 NOT_FOUND 예외
    @ExceptionHandler(LikeNotFoundException.class)
    public ResponseEntity<ErrorResponse> LikeNotFoundException(LikeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    // 사용자 인증 관련 => 401 UNAUTHORIZED
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> AuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    // 비밀번호 인증 예외
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> IncorrectPasswordException(IncorrectPasswordException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
    }

    // 사용자를 찾을 수 없을 때 예외
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> MemberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    // 탈퇴한 사용자일때 예외
    @ExceptionHandler(MemberDeactivatedException.class)
    public ResponseEntity<ErrorResponse> MemberDeactivatedException(MemberDeactivatedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // 프로필 이미지 URL 입력 예외
    @ExceptionHandler(ProfileImageRequiredException.class)
    public ResponseEntity<ErrorResponse> MemberNotFoundException(ProfileImageRequiredException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // 다른 예외는 그때 그때 메세지를 get 받을 수 있는 데,
    // ServletRequestBindingException 는 바로 터져서 메세지를 get 받을 틈이 없음
    // => 그래서 핸들러 단위에서 미리 반환시킬 메세지를 입력해놓음
    // @SessionAttribute 의 세션 인증 관련 => 401 UNAUTHORIZED
    // 전역핸들러에서 메세지를 관리하면 굳이 (ServletRequestBindingException e) 이 인자를 받을 필요도 없음
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<String> servletRequestBindingException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
    }

    // 이미 차단된 사용자 관련 예외
    @ExceptionHandler(BlockedUserException.class)
    public ResponseEntity<ErrorResponse> BlockedUserException(BlockedUserException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage()));
    }

    // 관계를 찾을 수 없는 경우 예외
    @ExceptionHandler(FollowNotFoundException.class)
    public ResponseEntity<ErrorResponse> FollowNotFoundException(FollowNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    // 친구 요청이 이미 존재하는 경우 예외
    @ExceptionHandler(FriendRequestAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> FriendRequestAlreadyExistsException(FriendRequestAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage()));
    }

    // 이미 친구가 된 경우 예외
    @ExceptionHandler(FriendRequestAlreadyProcessedException.class)
    public ResponseEntity<ErrorResponse> FriendRequestAlreadyProcessedException(FriendRequestAlreadyProcessedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    // 친구 요청 수락 권한 예외
    @ExceptionHandler(FriendRequestAuthorizationException.class)
    public ResponseEntity<ErrorResponse> FriendRequestAuthorizationException(FriendRequestAuthorizationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage()));
    }

    // 친구 요청 거절 권한 예외
    @ExceptionHandler(FriendRequestRejectionAuthorizationException.class)
    public ResponseEntity<ErrorResponse> FriendRequestRejectionAuthorizationException(FriendRequestRejectionAuthorizationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage()));
    }

    // 자기 자신에게 친구 요청 시 예외
    @ExceptionHandler(SelfFriendRequestException.class)
    public ResponseEntity<ErrorResponse> SelfFriendRequestException(SelfFriendRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

}
