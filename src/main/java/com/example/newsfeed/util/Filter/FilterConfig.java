package com.example.newsfeed.util.Filter;

import jakarta.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    // WebConfig 랑 빈 이름이 중복되어서 loginFilter2 으로 이름 변경해놨습니다.
    @Bean
    public FilterRegistrationBean<Filter> loginFilter2() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
