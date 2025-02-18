package com.example.newsfeed.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private State state;

    public Post(String title, String mediaUrl, String description, State state){
        this.title=title;
        this.mediaUrl=mediaUrl;
        this.description=description;
        this.state=state;
    }

    public void update(String title, String mediaUrl, String description ,State state){
        this.title=title;
        this.mediaUrl=mediaUrl;
        this.description=description;
        this.state=state;
    }

    public void changeState(State state){
        this.state=state;
    }

    public void plusLikeCount(){
        likeCount++;
    }

    public void cancelLikeCount(){
        likeCount--;
    }

}
