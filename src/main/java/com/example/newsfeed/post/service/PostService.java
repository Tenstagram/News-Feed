package com.example.newsfeed.post.service;

import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.post.dto.request.PostPageRequestDto;
import com.example.newsfeed.post.dto.request.PostSaveRequestDto;
import com.example.newsfeed.post.dto.request.PostStateChangeRequestDto;
import com.example.newsfeed.post.dto.request.PostUpdateRequestDto;
import com.example.newsfeed.post.dto.response.PostPageResponseDto;
import com.example.newsfeed.post.dto.response.PostResponseDto;
import com.example.newsfeed.post.dto.response.PostSaveResponseDto;
import com.example.newsfeed.post.dto.response.PostUpdateResponseDto;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.entity.State;
import com.example.newsfeed.post.repository.PostRepository;
import com.example.newsfeed.relationship.entity.RelationshipStatus;
import com.example.newsfeed.relationship.repository.RelationshipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final RelationshipRepository relationshipRepository;
    private final MediaUrlService mediaUrlService;
    private final State STATE_DELETE = State.DELETE;

    @Transactional
    public PostSaveResponseDto save(Long userId, PostSaveRequestDto dto, List<MultipartFile> mediaUrl) {

        String fileNameList = getFileName(mediaUrl);

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        Post post = new Post(member, dto.getTitle(), fileNameList, dto.getDescription());

        Post savePost = postRepository.save(post);

        try {
            for (MultipartFile m : mediaUrl) {
                mediaUrlService.upload(post, m);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new PostSaveResponseDto(savePost.getPostId(),
                savePost.getMember().getName(),
                savePost.getTitle(),
                savePost.getMediaUrl(),
                savePost.getDescription(),
                savePost.getState(),
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
                        post.getMember().getName(),
                        post.getMediaUrl(),
                        post.getDescription(),
                        post.getState(),
                        post.getLikeCount(),
                        commentRepository.countByPost(post)))
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> findAllExcludingBlockedUsers(Long memberId) {
        List<Long> blockedMemberIds = relationshipRepository.findBySenderIdAndStatus(memberId, RelationshipStatus.BLOCKED)
                .stream().map(r -> r.getReceiver().getId())
                .toList();

        // 차단한 사용자가 없을 경우 모든 게시물 반환
        List<Post> posts = blockedMemberIds.isEmpty()
                ? postRepository.findAll()
                : postRepository.findAllExcludingBlockedUsers(blockedMemberIds);

        return posts.stream()
                .map(PostResponseDto::of)
                .toList();
    }

    public List<PostResponseDto> findLatestPostsByFriends(Long memberId) {
        // 내가 속한 관계 중 BLOCKED 상태가 아닌 관계에서 상대방 id 가져오기
        List<Long> friendIds = relationshipRepository.findFriendIdsExcludingStatus(memberId, RelationshipStatus.BLOCKED);

        // 해당 유저의 친구 id에 맞는 게시물 가져오기
        List<Post> posts = postRepository.findPostsByFriends(friendIds);

        return posts.stream()
                .map(PostResponseDto::of)
                .toList();
    }

    @Transactional
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        if (post.getState() == STATE_DELETE) //삭제상태인지 확인
            throw new IllegalArgumentException("해당 게시글은 삭제됐습니다.");

        return new PostResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getMember().getName(),
                post.getMediaUrl(),
                post.getDescription(),
                post.getState(),
                post.getLikeCount(),
                commentRepository.countByPost(post));
    }

    @Transactional
    public PostUpdateResponseDto updateById(Long id, PostUpdateRequestDto dto, List<MultipartFile> mediaUrl) throws IOException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        if (post.getState() == STATE_DELETE) //삭제상태인지 확인
            throw new IllegalArgumentException("해당 게시글은 삭제됐습니다.");

        String fileName = getFileName(mediaUrl);

        post.update(dto.getTitle(), fileName,
                dto.getDescription(), dto.getState());

        mediaUrlService.deleteByPost(post);

        try {
            for (MultipartFile m : mediaUrl) {
                mediaUrlService.upload(post, m);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new PostUpdateResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getMediaUrl(),
                post.getDescription(),
                post.getState(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    @Transactional
    public void deleteById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        mediaUrlService.deleteByPost(post);

        postRepository.deleteById(id);
    }

    @Transactional
    public Page<PostPageResponseDto> findAllPage(int page, int size, int pageSort, PostPageRequestDto dto) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Post> pageS=null;
        if (pageSort == 1) {//생성일자 기준 내림차순
            pageS = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        }
        if (pageSort == 2) {//수정일자 기준 최신순
            pageS = postRepository.findAllByOrderByUpdatedAtDesc(pageable);
        }
        if (pageSort == 3) {//좋아요순으로 페이지 정렬
            pageS = postRepository.findAllByOrderByLikeCountDesc(pageable);
        }
        if (pageSort == 4) {//기간별 페이지 정렬
            LocalDateTime startDate = dto.getStartDate().atStartOfDay();
            LocalDateTime endDate = dto.getEndDate().atStartOfDay();
            pageS = postRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        }

        return pageS.map(post ->
                new PostPageResponseDto(post.getPostId(),
                        post.getTitle(),
                        post.getMediaUrl(),
                        post.getDescription(),
                        post.getLikeCount(),
                        commentRepository.countByPost(post),
                        post.getCreatedAt(),
                        post.getUpdatedAt()));
    }

    @Transactional
    public String changeState(Long id, PostStateChangeRequestDto dto) {//상태변경
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        post.changeState(dto.getState());

        if (dto.getState() == STATE_DELETE) {//만약 삭제상태로 변경한다면
            post.setDeletedAt();
        }

        return post.getState().getValue();
    }

    @Transactional
    public String getFileName(List<MultipartFile> mediaUrl) {//받은 이미지 이름들을 모아 String으로 변경
        StringBuffer fileName = new StringBuffer();

        for (MultipartFile m : mediaUrl) {
            fileName.append(m.getOriginalFilename() + ",");
        }
        fileName.deleteCharAt(fileName.length() - 1);

        return fileName.toString();
    }

}