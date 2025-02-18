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

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void likePost(Long memberId, Long postId) {//좋아요
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

//        if(postLikeRepository.existsByPost(post)){
//            if(postLikeRepository.countByMember(member)>=1){
//            throw new IllegalArgumentException("좋아요는 한 번만 누를 수 있습니다.");
//        }}
        if(postLikeRepository.countByPostAndMember(post,member)>=1){
              throw new IllegalArgumentException("좋아요는 한 번만 누를 수 있습니다.");
        }

        PostLike postLike = new PostLike(member, post);

        PostLike savePostLike= postLikeRepository.save(postLike);

        post.likeCount();

    }

    @Transactional
    public void postLikeCancel(Long memberId, Long postId) {//좋아요 취소
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

//        if (!memberRepository.existsById(memberId)) {
//            throw new IllegalArgumentException("해당 유저는 존재하지 않습니다.");
//        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지 않습니다."));

        if(postLikeRepository.countByPostAndMember(post,member)==0){
                throw new IllegalArgumentException("좋아요 먼저 누르세요");
            }else{
                postLikeRepository.deleteByMemberAndPost(member,post);
            }

        post.cancelLikeCount();

    }

}
