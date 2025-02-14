package com.example.newsfeed.controller;

import com.example.newsfeed.dto.response.PostPageResponseDto;
import com.example.newsfeed.dto.request.PostSaveRequestDto;
import com.example.newsfeed.dto.response.PostResponseDto;
import com.example.newsfeed.dto.response.PostSaveResponseDto;
import com.example.newsfeed.dto.request.PostUpdateRequestDto;
import com.example.newsfeed.dto.response.PostUpdateResponseDto;
import com.example.newsfeed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostSaveResponseDto> save(@RequestBody PostSaveRequestDto pdto){
        return ResponseEntity.ok(postService.save(pdto));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll(){
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(postService.findById(id));
    }

    @PatchMapping("/{id}")//로그인세션 id 추가
    public ResponseEntity<PostUpdateResponseDto> updateById(@PathVariable Long id, @RequestBody PostUpdateRequestDto dto){
        return ResponseEntity.ok(postService.updateById(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){//로그인세션 id 추가
        postService.deleteById(id);
        return ResponseEntity.ok("게시물이 삭제되었습니다.");
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
    public ResponseEntity<String> postLike(@PathVariable Long id){
        postService.postLike(id);
        return ResponseEntity.ok("좋아요를 눌렀습니다.");
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> postLikeCancel(@PathVariable Long id){
        postService.postLikeCancel(id);
        return ResponseEntity.ok("좋아요를 취소했습니다.");
    }

}
