package com.example.newsfeed.comment.controller;

import com.example.newsfeed.comment.dto.CommentAddRequestDto;
import com.example.newsfeed.comment.dto.CommentListResponseDto;
import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.dto.CommentUpdateRequestDto;
import com.example.newsfeed.comment.service.CommentService;
import com.example.newsfeed.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;


    // 댓글 작성 => 게시글 id에 작성
    @PostMapping("/posts/{postId}")
    public ResponseEntity<CommentResponseDto> addComment(@PathVariable Long postId,
                                                         @RequestBody CommentAddRequestDto dto,
                                                         @RequestHeader("Authorization") String token) {
        //Bear 제거 후 검증
        String jwt = token.replace("Bearer", "");
        Long memberId = memberService.getMemberIdFromToken(jwt);

        CommentResponseDto response = commentService.addComment(memberId, postId, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // 특정 게시글의 댓글 조회 (베스트 댓글 + 일반 댓글)
    @GetMapping("/posts/{postId}")
    public ResponseEntity<CommentListResponseDto> getComment(@PathVariable Long postId) {

        // CommentListResponseDto 라는 리스트 Dto 를 만들어서 서비스 계층에서 리스트를 통째로 하나의 객체로 취급
        // 서로 다른 2종류의 List<dto> 를 하나로 합쳐야 하기에 이런 방법 채택
        CommentListResponseDto response = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 특정 댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentUpdateRequestDto request,
                                                            @RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer", "");
        Long memberId = memberService.getMemberIdFromToken(jwt);

        CommentResponseDto response = commentService.updateComment(commentId, request, memberId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 특정 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId,
                                                @RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer", "");
        Long memberId = memberService.getMemberIdFromToken(jwt);


        commentService.deleteComment(commentId, memberId);
        return new ResponseEntity<>("댓글이 삭제 되었습니다.", HttpStatus.OK);
    }

    // 댓글 좋아요 누르기
    @PostMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable Long commentId,
                                              @RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer","");
        Long memberId = memberService.getMemberIdFromToken(jwt);

        commentService.likeComment(commentId, memberId);
        return new ResponseEntity<>("좋아요를 눌렀습니다.", HttpStatus.OK);
    }

    // 댓글 좋아요 취소
    @PostMapping("/{commentId}/unlike")
    public ResponseEntity<String> unlikeComment(@PathVariable Long commentId,
                                                @RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer","");
        Long memberId = memberService.getMemberIdFromToken(jwt);

        commentService.unlikeComment(commentId, memberId);
        return new ResponseEntity<>("좋아요가 취소되었습니다.", HttpStatus.OK);
    }


}
