package com.example.newsfeed.post.controller;

import com.example.newsfeed.post.dto.request.PostStateChangeRequestDto;
import com.example.newsfeed.post.dto.response.PostPageResponseDto;
import com.example.newsfeed.post.dto.request.PostSaveRequestDto;
import com.example.newsfeed.post.dto.response.PostResponseDto;
import com.example.newsfeed.post.dto.response.PostSaveResponseDto;
import com.example.newsfeed.post.dto.request.PostUpdateRequestDto;
import com.example.newsfeed.post.dto.response.PostUpdateResponseDto;
import com.example.newsfeed.post.service.PostService;
import com.example.newsfeed.util.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostSaveResponseDto> save(HttpServletRequest request, @Valid @RequestPart(name = "postRequest") PostSaveRequestDto dto, @RequestPart(name = "file") List<MultipartFile> mediaUrl) throws IOException {

        HttpSession session = request.getSession();// 테스트용 세션 생성(수정할  예정)

        session.setAttribute("LOGIN_USER", "1"); // 테스트용 서버 메모리에 세션 저장(수정)

        return ResponseEntity.ok(postService.save(dto, mediaUrl));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostUpdateResponseDto> update(@Valid
            @SessionAttribute(name = Const.LOGIN_USER) Long userId, @PathVariable Long id,
            @RequestPart(name = "postUpdateRequest") PostUpdateRequestDto dto, @RequestPart(name = "file") List<MultipartFile> mediaUrl) throws IOException {

        if (userId == 1) {//본인인지 체크 후 맞으면 실행
            return ResponseEntity.ok(postService.updateById(id, dto, mediaUrl));
        } else {//틀리면 에러
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정할 권한이 없습니다.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> changeState(//상태변경 메서드
                                              @SessionAttribute(name = Const.LOGIN_USER) Long userId, @PathVariable Long id,
                                              @RequestBody PostStateChangeRequestDto dto) {
        if (userId == 1) {//본인인지 체크 후 맞으면 실행
            return ResponseEntity.ok(postService.changeState(id, dto) + "게시물입니다.");
        } else {//틀리면 에러
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "수정할 권한이 없습니다.");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@SessionAttribute(name = Const.LOGIN_USER) Long userId,
                       @PathVariable Long id) {
        if (userId == 1) {
            postService.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "삭제할 권한이 없습니다.");
        }
    }

    @GetMapping("/page")
    public ResponseEntity<Page<PostPageResponseDto>> findAllPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostPageResponseDto> result = postService.findAllPage(page, size);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<String> postLike(@PathVariable Long id) {
        postService.postLike(id);
        return ResponseEntity.ok("좋아요를 눌렀습니다.");
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> postLikeCancel(@PathVariable Long id) {
        postService.postLikeCancel(id);
        return ResponseEntity.ok("좋아요를 취소했습니다.");
    }

}
