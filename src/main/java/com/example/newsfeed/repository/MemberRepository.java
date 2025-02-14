package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {


}
