package com.example.oauth2login.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //유저 정보 조회
        OAuth2User oAuth2User = super.loadUser(userRequest);

        //권한 부여
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

        Map<String, Object> attributes = oAuth2User.getAttributes();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            log.info("userInfo key {}", entry.getKey());
            log.info("userInfo value {}", entry.getValue());
            log.info("@@@");
        }
        // nameAttributeKey
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //kakao는 id, naver는 response 반환

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), userNameAttributeName);
    }
}