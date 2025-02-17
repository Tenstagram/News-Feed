package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name="post")
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private String mediaUrl;

    private String description;

    private String name;

//    @ManyToOne
//    @JoinColumn(name="member_id")
//    private Member member;

    private int likeCount;

    private int commentCount;

    private State state;

    public Post(String title, String mediaUrl, String description){
        this.title=title;
        this.mediaUrl=mediaUrl;
        this.description=description;
    }
    public void update(String title, String mediaUrl, String description){
        this.title=title;
        this.mediaUrl=mediaUrl;
        this.description=description;
    }
    public void plusLikeCount(){
        likeCount++;
    }
    public void cancelLikeCount(){
        likeCount--;
    }

}
