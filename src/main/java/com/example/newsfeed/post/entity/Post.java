package com.example.newsfeed.post.entity;

import com.example.newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy ="post")
    private List<PostLike> postLike = new ArrayList<>();;

    private long likeCount=0;

    private long commentCount=0;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private State state;

    public Post(Member member, String title, String mediaUrl, String description){
        this.member=member;
        this.title=title;
        this.mediaUrl=mediaUrl;
        this.description=description;
        this.state=State.PUBLIC;
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
