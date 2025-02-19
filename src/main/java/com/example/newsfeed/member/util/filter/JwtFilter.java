package com.example.newsfeed.member.util.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/auth/signup", "/auth/login", "/auth/logout"};
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        log.info("JWT 필터 실행 - 요청 URI: {}", requestURI);

        // 화이트리스트에 포함된 URI는 필터를 통과
        if (isWhiteListed(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        String token = resolveToken(httpRequest);

        if (token == null) {
            log.warn("Authorization 헤더가 없거나 잘못된 형식입니다.");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효한 토큰이 필요합니다.");
            return;
        }

        // 토큰 검증
        String username = jwtUtil.extractUsername(token);
        if (username == null) {
            log.warn("유효하지 않은 토큰입니다.");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return;
        }

        log.info("인증된 사용자: {}", username);
        chain.doFilter(request, response);
    }

    // 화이트리스트 URI 확인
    private boolean isWhiteListed(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

    // Authorization 헤더에서 Bearer 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }
        return null;
    }
}