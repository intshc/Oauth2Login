package com.example.oauth2login.config;

import com.example.oauth2login.response.UserResponse;
import com.example.oauth2login.service.OAuth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2UserService oAuth2UserService;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(config -> config.anyRequest().permitAll())
                .oauth2Login(oauth2Configurer ->
                        oauth2Configurer.loginPage("/login")
                                .successHandler(successHandler())
                                .userInfoEndpoint()
                                .userService(oAuth2UserService))
                .build();

    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

                UserResponse userResponse = UserResponse.builder()
                        .id((Long) defaultOAuth2User.getAttributes().get("id"))
                        .build();

                response.setContentType("text/html; charset=utf-8");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(response.getWriter(), userResponse);

            }
        };
    }
}
