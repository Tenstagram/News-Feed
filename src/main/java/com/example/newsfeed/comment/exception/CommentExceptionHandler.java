package com.example.newsfeed.comment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 전역 인증 관리 핸들
// 예외 발생 흐름: 각 코드에서 예외 발생 -> 각 예외 클래스로 넘어감 -> GlobalExceptionHandler 으로 넘어와서 최종적으로 예외 던짐
@ControllerAdvice
public class CommentExceptionHandler {

    // 404 NOT_FOUND 통합 예외
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // 댓글 좋아요 관련 예외
    @ExceptionHandler(RepetitionLikeException.class)
    public ResponseEntity<String> repetitionLikeException(RepetitionLikeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // 사용자 인증 관련 => 401 UNAUTHORIZED
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> authenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자를 인증할 수 없습니다.");
    }

    // 400 BAD_REQUEST
    @ExceptionHandler(DeletedCommentException.class)
    public ResponseEntity<String> deletedCommentException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 삭제된 댓글입니다.");
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









}
