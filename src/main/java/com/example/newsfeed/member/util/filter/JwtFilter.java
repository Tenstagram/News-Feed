package com.example.newsfeed.member.util.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class JwtFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/auth/signup", "/auth/login", "/auth/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        log.info("JWT 필터 실행 - 요청 URI: {}", requestURI);

        if (!isWhiteList(requestURI)) {
            String token = httpRequest.getHeader("Authorization");

            if (token == null || !token.startsWith("Bearer ")) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 필요합니다.");
                return;
            }

            // "Bearer " 제거 후 검증
            String jwt = token.substring(7);
            String username = JwtUtil.validateToken(jwt);

            if (username == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                return;
            }

            log.info("인증된 사용자: {}", username);
        }

        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}