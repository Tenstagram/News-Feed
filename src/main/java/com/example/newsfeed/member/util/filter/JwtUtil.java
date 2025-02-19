package com.example.newsfeed.member.util.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtUtil {


    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간


    // JWT 생성
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 사용자 정보
                .setIssuedAt(new Date()) // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간
                .compact();
    }

    // JWT 검증 및 파싱
    public static String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject(); // username 반환
        } catch (JwtException e) {
            log.error("JWT 검증 실패: {}", e.getMessage());
            return null;
        }
    }
}