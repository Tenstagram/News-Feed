package com.example.newsfeed.member.repository;

import com.example.newsfeed.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member>findById(Long id);
    Optional<Member> findByEmail(String email);


}
