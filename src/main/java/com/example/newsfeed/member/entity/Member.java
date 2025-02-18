package com.example.newsfeed.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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

    @Column(nullable = false)
    private int follower;

    //회원 탈퇴, 회원가입 시
    @Enumerated(EnumType.STRING)
    @ColumnDefault("ACTIVATE")
    private MemberStatus status;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
        this.name = UUID.randomUUID().toString();
        this.email = UUID.randomUUID().toString(); //아 맞다!!!
        this.password = UUID.randomUUID().toString();
        this.profileUrl = UUID.randomUUID().toString();
    }
}
