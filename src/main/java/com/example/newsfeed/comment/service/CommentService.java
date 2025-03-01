package com.example.newsfeed.comment.service;


import com.example.newsfeed.comment.dto.CommentAddRequestDto;
import com.example.newsfeed.comment.dto.CommentListResponseDto;
import com.example.newsfeed.comment.dto.CommentResponseDto;
import com.example.newsfeed.comment.dto.CommentUpdateRequestDto;
import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.comment.entity.LikeComment;
import com.example.newsfeed.comment.exception.*;
import com.example.newsfeed.comment.repository.CommentRepository;
import com.example.newsfeed.comment.repository.LikeCommentRepository;
import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.exception.PostNotFoundException;
import com.example.newsfeed.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    // 댓글 작성 메서드
    @Transactional
    public CommentResponseDto addComment(Long memberId, Long postId, CommentAddRequestDto dto) {

        // 사용자 조회 로직 => 사용자가 유효한지
        Member member = memberRepository.findById(memberId)
                .orElseThrow(AuthenticationException::new);

        // 게시글 조회 로직 => 게시글이 유효한지
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        // 부모 댓글 조회 및 검증
        Comment comment;
        if (dto.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(dto.getParentCommentId())
                    .orElseThrow(CommentNotFoundException::new);

            // 부모 댓글이 있으면 대댓글 생성
            comment = Comment.builder()
                    .member(member)
                    .post(post)
                    .content(dto.getContent())
                    .parentCommentId(parentComment)
                    .build();

        } else { // 부모 댓글 없으면 부모 댓글 생성
            comment = Comment.builder()
                    .member(member)
                    .post(post)
                    .content(dto.getContent())
                    .build();
        }


        // DB에 댓글 객체 저장
        commentRepository.save(comment);

        // 응답 DTO 로 반환
        return toCommentResponseDto(comment);
    }

    // 특정 게시글의 댓글 조회
    @Transactional
    public CommentListResponseDto getCommentsByPostId(Long postId) {

        // 베스트 댓글 3개 리스트 출력
        List<Comment> bestComment = commentRepository.findTop3ByPostIdOrderByLikeCountDesc(postId);

        // 일반 댓글 전체 출력 (페이징 = 0페이지, 20개씩)
        List<Comment> allComment = commentRepository.findAllByPostId(postId, PageRequest.of(0, 20));

        // 각 객체를 DTO 로 변환
        List<CommentResponseDto> bestCommentDto = toCommentResponseListDto(bestComment);
        List<CommentResponseDto> allCommentDto = toCommentResponseListDto(allComment);

        // 변환한 각 DTO 를 응답 DTO 에 빌드하여 반환
        return CommentListResponseDto.builder()
                .bestCommentList(bestCommentDto)
                .allCommentList(allCommentDto)
                .build();
    }

    // 특정 댓글 수정
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto dto, Long memberId) {

        // 댓글 + 사용자 검증 메서드
        Comment comment = verifyingUsersAndComment(commentId, memberId);

        // 댓글 내용 수정
        // builder 는 보통 객체를 생성할 때 사용됨 (새로 엔티티 객체를 생성하거나 응답 DTO 객체를 생성하는 등)
        // 이미 있는 객체의 원래 있던 값을 수정 할때는 Setter 씀
        comment.setContent(dto.getContent());

        // 응답 DTO 로 빌드 하여 반환
        return toCommentResponseDto(commentRepository.save(comment));
    }

    // 특정 댓글 삭제 (soft delete 방식)
    public void deleteComment(Long commentId, Long memberId) {

        // 검증
        Comment comment = verifyingUsersAndComment(commentId, memberId);

        // 실제로 지우는 게 아니라, deletedAt 만 업데이트 함
        comment.setDeletedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    // DB에 저장한 객체(엔티티)를 응답 DTO 로 변환해주는 메서드
    private CommentResponseDto toCommentResponseDto(Comment comment) {

        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .name(comment.getMember().getName())
                .postId(comment.getPost().getPostId())
                .content(comment.getContent())
                .likeCount(comment.getLikeCount())
                .childCount(comment.getChildCount())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                // parentCommentId 값이 null 이면 최상위 댓글, 값이 있으면 대댓글
                .parentCommentId(comment.getParentCommentId() == null ? null : comment.getParentCommentId().getCommentId())
                .build();
    }


    // 댓글 좋아요 누르기
    public void likeComment(Long commentId, Long memberId) {

        // 검증
        Comment comment = verifyCommentForLike(commentId);

        // 사용자 조회 로직 => 사용자가 유효한지
        Member member = memberRepository.findById(memberId)
                .orElseThrow(AuthenticationException::new);

        // 중복 좋아요 체크
        if (likeCommentRepository.existsByComment_commentIdAndMember_id(commentId, memberId)) {
            throw new CommentAlreadyLikedException();
        }

        // 좋아요댓글 객체 생성
        LikeComment likeComment = LikeComment.builder()
                .comment(comment)
                .member(member)
                .build();

        // 좋아요 객체 저장
        likeCommentRepository.save(likeComment);

        // 기존의 카운트를 수정(증가) 시키는 방식이기에 setter 사용
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentRepository.save(comment);
    }

    // 댓글 좋아요 취소
    public void unlikeComment(Long commentId, Long memberId) {

        // 검증
        Comment comment = verifyCommentForLike(commentId);

        // 사용자 조회 로직 => 사용자가 유효한지
        Member member = memberRepository.findById(memberId)
                .orElseThrow(AuthenticationException::new);

        // 좋아요 여부 검증
        LikeComment likeComment = likeCommentRepository.findByComment_commentIdAndMember_id(commentId, memberId)
                .orElseThrow(LikeNotFoundException::new);

        // 좋아요 기록 삭제
        likeCommentRepository.delete(likeComment);

        // 댓글 좋아요 수 감소
        int count = comment.getLikeCount();
        comment.setLikeCount(count > 0 ? count - 1 : 0);
        commentRepository.save(comment);
    }

    // 댓글 리스트 엔티티를 DTO 로 변환
    private List<CommentResponseDto> toCommentResponseListDto(List<Comment> comments) {

        // comments 객체를 스트림으로 toCommentResponseDto 로 변환하고,
        // 복사하여 컬렉션 (여기서는 List) 방식으로 매핑
        return comments.stream()
                .map(this::toCommentResponseDto)
                .collect(Collectors.toList());
    }

    // 같은 검증 방식이 반복 되므로, 리팩터링 하여 하나의 메서드로 뺌
    private Comment verifyingUsersAndComment(Long commentId, Long memberId) {

        // 수정할 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        // 사용자 인증
        if (!comment.getMember().getId().equals(memberId)) {
            // 아무 메세지나 입력해도 전역 핸들러에서 메세지 관리됨
            throw new AuthenticationException();
        }

        // 삭제된 댓글인지 검사
        if (comment.getDeletedAt() != null) {
            throw new DeletedCommentException();
        }

        return comment;
    }

    // 댓글 검증
    private Comment verifyCommentForLike(Long commentId) {

        // 수정할 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        // 삭제된 댓글인지 검사
        if (comment.getDeletedAt() != null) {
            throw new DeletedCommentException();
        }

        return comment;
    }

}
