package com.example.newsfeed.member.util.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key signingKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        if (secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("JWT Secret Key is missing or invalid.");
        }
        try {
            log.info("Initializing JwtUtil with secretKey.");
            byte[] keyBytes = Base64.getDecoder().decode(secretKey);
            signingKey = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            log.error("Failed to decode JWT secret key: {}", e.getMessage());
            throw e;
        }
    }

    // JWT 토큰 생성
    public String generateToken(String username) {
        Date now = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(now)
                        .setExpiration(new Date(now.getTime() + TOKEN_EXPIRATION_TIME))
                        .signWith(signingKey, signatureAlgorithm)
                        .compact();
    }

    // JWT 유효성 검증 및 사용자 이름 추출
    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // 사용자 이름 반환
        } catch (JwtException e) {
            log.error("JWT 검증 실패: {}", e.getMessage());
            return null; // 유효하지 않은 토큰일 경우 null 반환
        }
    }

    // 토큰 검증 및 사용자 이름 추출
    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token.replace(BEARER_PREFIX, "").trim())
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return null;
        }
    }
}