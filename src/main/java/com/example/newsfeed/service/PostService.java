package com.example.newsfeed.service;

import com.example.newsfeed.dto.response.PostPageResponseDto;
import com.example.newsfeed.dto.request.PostSaveRequestDto;
import com.example.newsfeed.dto.response.PostResponseDto;
import com.example.newsfeed.dto.response.PostSaveResponseDto;
import com.example.newsfeed.dto.request.PostUpdateRequestDto;
import com.example.newsfeed.dto.response.PostUpdateResponseDto;
import com.example.newsfeed.entity.MediaUrl;
import com.example.newsfeed.entity.Post;
import com.example.newsfeed.repository.MediaUrlRepository;
import com.example.newsfeed.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService{
    
    private final PostRepository postRepository;
    @Autowired
    private final MediaUrlRepository mediaUrlRepository;

    private final String fullPathName = "Media/" ;



    @Transactional
    public PostSaveResponseDto save(PostSaveRequestDto dto, MultipartFile mediaUrl) {


        Post post= new Post(dto.getTitle(),mediaUrl.getOriginalFilename(), dto.getDescription());

        Post savePost=postRepository.save(post);

        String url= null;
        try {
            url = upload(post, mediaUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
    public PostUpdateResponseDto updateById(Long id, PostUpdateRequestDto dto,MultipartFile mediaUrl) throws IOException {
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        post.update(dto.getTitle(), mediaUrl.getOriginalFilename(),
                dto.getDescription());

        MediaUrl media=updateMediaByPostId(post, mediaUrl);

        media.setPost(post);

        return new PostUpdateResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getMediaUrl(),
                post.getDescription(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    public void deleteById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        MediaUrl media= mediaUrlRepository.findByPost(post);

        mediaUrlRepository.deleteById(media.getMediaUrlID());

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

    public MediaUrl updateMediaByPostId(Post post, MultipartFile mediaUrl) throws IOException{
        MediaUrl media=mediaUrlRepository.findByPost(post);

        String originalFilename = mediaUrl.getOriginalFilename();//원래 파일명
        String storedFilename = UUID.randomUUID() + "_" + originalFilename;//db 저장용 파일명

        // 파일 저장 경로 지정
        Path filePath = Paths.get(fullPathName + storedFilename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, mediaUrl.getBytes());

        media.updateMedia(originalFilename,storedFilename,filePath.toString(),post);

        return media;

    }

    public String upload(Post post, MultipartFile image) throws IOException {


//        String path = mediaUrlHandler.save(MediaUrl);
//        MediaUrl MediaUrlEntity = new MediaUrl();
//        MediaUrlEntity.setPath(path);
//        mediaUrlRepository.save(MediaUrlEntity);

        String originalFilename = image.getOriginalFilename();//원래 파일명
        String storedFilename = UUID.randomUUID() + "_" + originalFilename;//db 저장용 파일명

        // 파일 저장 경로 지정
        Path filePath = Paths.get(fullPathName + storedFilename);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, image.getBytes());

        //파일을 media table에 저장
        MediaUrl MediaUrlEntity = new MediaUrl(originalFilename,storedFilename,filePath.toString(),post);

        MediaUrl saveMediaUrl= mediaUrlRepository.save(MediaUrlEntity);

        return  saveMediaUrl.getMediaUrl();

    }
}
