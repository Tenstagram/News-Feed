package com.example.newsfeed.util.config;


import com.example.newsfeed.member.util.filter.JwtFilter;
import com.example.newsfeed.member.util.filter.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;

    @Bean
    public FilterRegistrationBean<JwtFilter> loginFilter() {
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtFilter(jwtUtil)); // Filter 등록
        filterRegistrationBean.setOrder(1); // Filter 순서 1 설정
        filterRegistrationBean.addUrlPatterns("/*"); // 전체 URL에 Filter 적용

        return filterRegistrationBean;
    }
}