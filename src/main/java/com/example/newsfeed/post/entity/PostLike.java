package com.example.newsfeed.post.entity;


import com.example.newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="PostLike")
public class PostLike extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long postLikeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="post_id", nullable =false)
    private Post post;

    public PostLike(Member member, Post post){
        this.member=member;
        this.post=post;
    }

    public PostLike() {

    }
}
