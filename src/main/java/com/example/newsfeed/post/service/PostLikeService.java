package com.example.newsfeed.post.service;

import com.example.newsfeed.member.entity.Member;
import com.example.newsfeed.member.repository.MemberRepository;
import com.example.newsfeed.post.entity.PostLike;
import com.example.newsfeed.post.entity.Post;
import com.example.newsfeed.post.repository.PostLikeRepository;
import com.example.newsfeed.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    //- 사용자가 게시물이나 댓글에 좋아요를 남기거나 취소할 수 있습니다.
    //- 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없습니다.
    //- 같은 게시물에는 사용자당 한 번만 좋아요가 가능합니다.<-이게 걸림..

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void likePost(Long memberId, Long postId) {//좋아요
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        if (postLikeRepository.existsByMemberId(memberId))
            throw new IllegalArgumentException("좋아요는 한 번만 누를 수 있습니다.");

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        PostLike postLike = new PostLike(member, post);

        PostLike savePostLike= postLikeRepository.save(postLike);

        post.likeCount();

    }

    @Transactional
    public void postLikeCancel(Long memberId, Long postId) {//좋아요 취소
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        if (!memberRepository.existsById(memberId)) {
            throw new IllegalArgumentException("해당 유저는 존재하지 않습니다.");
        }

        if (!postLikeRepository.existsByMemberId(memberId)){
            throw new IllegalArgumentException("좋아요 먼저 누르세요");
        }

        postLikeRepository.deleteByMemberId(memberId);

        post.cancelLikeCount();

    }

}
