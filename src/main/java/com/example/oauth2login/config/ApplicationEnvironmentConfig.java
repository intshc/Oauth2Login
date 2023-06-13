package com.example.oauth2login.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/application-API-Key.yml")
@Getter
public class ApplicationEnvironmentConfig {

    @Value("${naver.clientId}")
    private String clientId;
    @Value("${naver.clientSecret}")
    private String clientSecret;
    @Value("${naver.baseUrl}")
    private String baseUrl;
    @Value("${naver.redirectUrl}")
    private String redirectUrl;

}
