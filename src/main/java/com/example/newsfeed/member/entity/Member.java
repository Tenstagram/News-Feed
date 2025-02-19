package com.example.newsfeed.member.entity;

import com.example.newsfeed.post.entity.PostLike;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Entity
@Table(name = "members")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column()
    private String profileUrl;

    @OneToMany(mappedBy="member")//좋아요 연관관계 필드 추가
    private List<PostLike> postLike = new ArrayList<>();;

    @Column()
    private int follower;

    //회원 탈퇴, 회원가입 시
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    public Member(String name, String email, String password, MemberStatus status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public Member() {

    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public void updateName(String newName) {
        this.name = newName;
    }

    public void updateImage(String newProfileUrl) {
        this.profileUrl = newProfileUrl;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void delete() {
        this.status = MemberStatus.DELETED;
    }

}
