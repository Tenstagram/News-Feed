package com.example.newsfeed.service;

import com.example.newsfeed.dto.response.PostPageResponseDto;
import com.example.newsfeed.dto.request.PostSaveRequestDto;
import com.example.newsfeed.dto.response.PostResponseDto;
import com.example.newsfeed.dto.response.PostSaveResponseDto;
import com.example.newsfeed.dto.request.PostUpdateRequestDto;
import com.example.newsfeed.dto.response.PostUpdateResponseDto;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService{
    
    private final PostRepository postRepository;

    @Transactional
    public PostSaveResponseDto save(PostSaveRequestDto dto) {
        Post post= new Post(dto.getTitle(),
                dto.getMediaUrl(),
                dto.getDescription());

        Post savePost=postRepository.save(post);

        return new PostSaveResponseDto(savePost.getPostId(),
                savePost.getName(),//getMember.getName()으로 나중에 수정
                savePost.getTitle(),
                savePost.getMediaUrl(),
                savePost.getDescription(),
                savePost.getLikeCount(),
                savePost.getCommentCount(),
                savePost.getCreatedAt());
    }

    @Transactional
    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(post -> new PostResponseDto(
                        post.getPostId(),
                        post.getTitle(),
                        post.getMediaUrl(),
                        post.getDescription(),
                        post.getLikeCount(),
                        post.getCommentCount()))
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getMediaUrl(),
                post.getDescription(),
                post.getLikeCount(),
                post.getCommentCount());
    }

    @Transactional
    public PostUpdateResponseDto updateById(Long id, PostUpdateRequestDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        post.update(dto.getTitle(),
                dto.getDescription());

        return new PostUpdateResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getDescription(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    public void deleteById(Long id) {
        if(!postRepository.existsById(id))
            throw new IllegalArgumentException("해당 게시글은 존재하지 않습니다.");

        postRepository.deleteById(id);

    }

    @Transactional
    public Page<PostPageResponseDto> findAllPage(int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        Page<Post> pageS = postRepository.findAllByOrderByCreatedAtDesc (pageable);

        return pageS.map(post ->
                new PostPageResponseDto(post.getPostId(),
                        post.getTitle(),
                        post.getMediaUrl(),
                        post.getDescription(),
                        post.getLikeCount(),
                        post.getCommentCount()));
        //                        commentRepository.countByPostId(post.getPostId()),

    }

    public void postLike(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        post.plusLikeCount();
    }

    public void postLikeCancel(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        post.cancelLikeCount();
    }
}
