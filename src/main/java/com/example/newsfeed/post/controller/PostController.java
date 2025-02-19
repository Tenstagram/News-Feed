package com.example.newsfeed.post.controller;

import com.example.newsfeed.member.service.MemberService;
import com.example.newsfeed.post.dto.request.PostPageRequestDto;
import com.example.newsfeed.post.dto.request.PostSaveRequestDto;
import com.example.newsfeed.post.dto.request.PostStateChangeRequestDto;
import com.example.newsfeed.post.dto.request.PostUpdateRequestDto;
import com.example.newsfeed.post.dto.response.PostPageResponseDto;
import com.example.newsfeed.post.dto.response.PostResponseDto;
import com.example.newsfeed.post.dto.response.PostSaveResponseDto;
import com.example.newsfeed.post.dto.response.PostUpdateResponseDto;
import com.example.newsfeed.post.service.PostLikeService;
import com.example.newsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;
    private final MemberService memberService;

    //게시물 생성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostSaveResponseDto> save(
            @RequestHeader("Authorization") String token,
            @Valid
            @RequestPart(name = "postRequest") PostSaveRequestDto dto,
            @RequestPart(name = "file") List<MultipartFile> mediaUrl,
            BindingResult result) throws IOException {

        String jwt = token.replace("Bearer", "");
        Long userId = memberService.getMemberIdFromToken(jwt);

        return ResponseEntity.ok(postService.save(userId, dto, mediaUrl));
    }

    //게시물 전체 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    //게시물 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long postId) {

        String jwt = token.replace("Bearer", "");
        Long userId = memberService.getMemberIdFromToken(jwt);
        return ResponseEntity.ok(postService.findById(postId));
    }

    //게시물 수정
    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostUpdateResponseDto> update(
                                                        @RequestHeader("Authorization") String token,
                                                        @PathVariable Long postId, @RequestParam Long memberId,
                                                        @Valid
                                                        @RequestPart(name = "postUpdateRequest") PostUpdateRequestDto dto,
                                                        @RequestPart(name = "file") List<MultipartFile> mediaUrl, BindingResult result) throws IOException {

        String jwt = token.replace("Bearer", "");
        Long userId = memberService.getMemberIdFromToken(jwt);

        if (userId == memberId) {//본인인지 체크 후 맞으면 실행
            return ResponseEntity.ok(postService.updateById(postId, dto, mediaUrl));
        } else {//틀리면 에러
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정할 권한이 없습니다.");
        }
    }

    //게시물 상태 변경(공개,비공개,삭제됨)
    @PatchMapping("/{postId}")
    public ResponseEntity<String> changeState(//상태변경 메서드
                                              @RequestHeader("Authorization") String token,@PathVariable Long postId,
                                              @RequestParam Long memberId,
                                              @RequestBody PostStateChangeRequestDto dto) {

        String jwt = token.replace("Bearer", "");
        Long userId = memberService.getMemberIdFromToken(jwt);

        if (userId == memberId) {//본인인지 체크 후 맞으면 실행
            return ResponseEntity.ok(postService.changeState(postId, dto) + "게시물입니다.");
        } else {//틀리면 에러
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정할 권한이 없습니다.");
        }
    }

    //게시물 삭제
    @DeleteMapping("/{postId}")
    public void delete(@RequestHeader("Authorization") String token,
                       @RequestParam Long memberId,
                       @PathVariable Long postId) {
        String jwt = token.replace("Bearer", "");
        Long userId = memberService.getMemberIdFromToken(jwt);
        if (userId == memberId) {
            postService.deleteById(postId);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제할 권한이 없습니다.");
        }
    }

    //페이지 조회
    @GetMapping("/page")
    public ResponseEntity<Page<PostPageResponseDto>> findAllPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam int pageSort, @RequestBody PostPageRequestDto dto
    ) {
        Page<PostPageResponseDto> result = postService.findAllPage(page, size, pageSort, dto);

        return ResponseEntity.ok(result);
    }

    //게시물 좋아요
    @PostMapping("/{postId}/likes")
    public ResponseEntity<String> postLike(@RequestHeader("Authorization") String token,  @PathVariable Long postId, @RequestParam Long memberId) {
        String jwt = token.replace("Bearer", "");
        Long userId = memberService.getMemberIdFromToken(jwt);

        if (userId != memberId) {
            postLikeService.likePost(memberId, postId);
            return ResponseEntity.ok("좋아요를 눌렀습니다.");
        } else return ResponseEntity.ok("작성자는 좋아요를 누를 수 없습니다.");
    }

    //게시물 좋아요 취소
    @DeleteMapping("/{postId}/cancel")
    public ResponseEntity<String> postLikeCancel(@RequestHeader("Authorization") String token,@PathVariable Long postId, @RequestParam Long memberId) {
        String jwt = token.replace("Bearer", "");
        Long userId = memberService.getMemberIdFromToken(jwt);

        if (userId != memberId) {
            postLikeService.postLikeCancel(memberId, postId);
            return ResponseEntity.ok("좋아요를 취소했습니다.");
        } else return ResponseEntity.ok("작성자는 좋아요를 누를 수 없습니다.");
    }
}
