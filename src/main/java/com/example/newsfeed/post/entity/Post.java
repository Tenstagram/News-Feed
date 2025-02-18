package com.example.newsfeed.post.entity;

import com.example.newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    private int likeCount=0;

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

   public void likeCount(){
        likeCount++;
   }

   public void cancelLikeCount(){
        likeCount--;
   }
}
