package com.example.newsfeed.comment.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
// JPA Auditing 활성화 => createdAt, updatedAt 자동 처리
@EnableJpaAuditing
public class AuditingConfig {
}
