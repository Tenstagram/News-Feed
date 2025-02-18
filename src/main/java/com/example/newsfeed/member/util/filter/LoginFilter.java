package com.example.newsfeed.member.util.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Slf4j
public class LoginFilter implements Filter {


    private static final String[] WHITE_LIST = {"/","/auth/signup","/auth/login","/auth/logout"};

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();


        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        log.info("로그인 필터 로직 실행");

        //TODO JWT 토큰 디코딩

        if (!isWhiteList(requestURI)) {

            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute("token") == null) {
                throw new RuntimeException("로그인 해주세요.");
            }

        }
        filterChain.doFilter(servletRequest,servletResponse);

    }

    private boolean isWhiteList(String requestURI) {

        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);

    }
}