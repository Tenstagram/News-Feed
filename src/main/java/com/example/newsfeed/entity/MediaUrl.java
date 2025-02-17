package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "media_url")
public class MediaUrl extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long mediaUrlID;//DB 고유 파일 아이디

    private String mediaName;//파일 원래 이름

    private String storedMediaName;//DB에 저장할 파일 이름

    private String mediaUrl;//파일 경로

    @ManyToOne
    @JoinColumn(name="post_id", nullable =false)
    private Post post;

    public MediaUrl(String mediaName, String storedMediaName, String mediaUrl) {
        this.mediaName = mediaName;
        this.storedMediaName = storedMediaName;
        this.mediaUrl = mediaUrl;
    }

    public MediaUrl(String mediaName, String storedMediaName, String mediaUrl,Post post){
        this.mediaName= mediaName;
        this.storedMediaName=storedMediaName;
        this.mediaUrl=mediaUrl;
        this.post=post;
    }

    public void updateMedia(String mediaName, String storedMediaName, String mediaUrl,Post post){
        this.mediaName= mediaName;
        this.storedMediaName=storedMediaName;
        this.mediaUrl=mediaUrl;
        this.post=post;
    }

    public MediaUrl() {

    }

}
