package com.example.newsfeed.member.util.filter;

import java.util.Base64;

public class SercerKeyGenerator {
    public static void main(String[] args) {
        String secret = "your-secret-key"; // 원하는 비밀 키
        String base64EncodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());
        System.out.println("Base64 Encoded Secret Key: " + base64EncodedSecret);
    }
}

