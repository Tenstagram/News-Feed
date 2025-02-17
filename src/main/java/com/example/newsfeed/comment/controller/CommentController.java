package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.CommentAddRequestDto;
import com.example.newsfeed.comment.dto.CommentListResponseDto;
import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.dto.CommentUpdateRequestDto;
import com.example.newsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    // 댓글 작성 => 게시글 id에 작성
//    @PostMapping("/posts/{postId}")
//    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long postId,
//                                                         @RequestBody CommentAddRequestDto dto,
//                                                         @SessionAttribute(name = "memberId") Long memberId) {
//
//        // HttpSession session 객체 활용 방식 말고 @SessionAttribute 어노테이션으로도 가능
//        // 주의: 어노테이션으로 할 때, memberId 가 null 이면, AuthenticationException 까지 안가고
//        // Spring 에서 바로 ServletRequestBindingException 발생시킴
//        // => GlobalExceptionHandler 에서 ServletRequestBindingException 도 추가 관리 해줘야됨
//        // ----------
//        // getAttribute 는 Optional 을 사용하지 않고 바로 Object 를 반환함
//        // => 그래서 .orElseThrow() 사용 불가능함
//        // => Long memberId = Optional.ofNullable((Long) session.getAttribute("memberId"))
//        //    .orElseThrow(() -> new AuthenticationException("사용자를 찾을 수 없습니다."));
//        // 이렇게 Optional 으로 감싸주면 .orElseThrow() 쓸 수 있어서 짧게 표현 가능
//        // Optional.ofNullable(): null 을 허용 해서 Optional 로 감싸주는 Optional 클래스의 메서드
//
//        // 컨트롤러 계층에서 이미 memberId 에 대해 AuthenticationException 예외를 던지고 있다면, 굳이 서비스 계층에서 또 던질 필요가 있는가?
//        // => 아님, 컨트롤러에서는 세션에 대한 memberId 예외 처리이고, 서비스는 DB 에서의 memberId 예외 처리임.
//        // => 세션의 memberId 가 유효하다고 해도, DB 에서는 유효하다고 보장할 수 없음. + @Transactional 사용하기 위함인것도 있음
////        Long memberId = (Long) session.getAttribute("memberId");
////        if (memberId == null) {
////            // 세션에서 memberId을 조회하고 없으면 예외 처리
////            throw new AuthenticationException("사용자를 찾을 수 없습니다.");
////        }
//
//        // 2가지 반환 방법 다 가능
////        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(memberId, postId, dto));
//
//        CommentResponseDto response = commentService.addComment(memberId, postId, dto);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }


    // 특정 게시글의 댓글 조회 (베스트 댓글 + 일반 댓글)
    // required = false 할 경우, 세션이 없어도 조회가능
    // => 단, 로그인 하면 세션에 따라 추가 로직 가능 (차단 필터링 등)
    @GetMapping("/post/{postId}")
    public ResponseEntity<CommentListResponseDto> getComment(@PathVariable Long postId
            , @SessionAttribute(name = "memberId", required = false) Long memberId) {

        // CommentListResponseDto 라는 리스트 Dto 를 만들어서 서비스 계층에서 리스트를 통째로 하나의 객체로 취급
        // 서로 다른 2종류의 List<dto> 를 하나로 합쳐야 하기에 이런 방법 채택
        CommentListResponseDto response = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 특정 댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                            @RequestBody CommentUpdateRequestDto request,
                                            @SessionAttribute(name = "memberId") Long memberId) {

        CommentResponseDto response = commentService.updateComment(commentId, request, memberId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 특정 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @SessionAttribute(name = "memberId") Long memberId) {
        commentService.deleteComment(commentId, memberId);
        return new ResponseEntity<>("댓글이 삭제 되었습니다.", HttpStatus.OK);
    }



}
