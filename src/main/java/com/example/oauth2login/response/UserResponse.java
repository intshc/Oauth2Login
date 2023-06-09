package com.example.oauth2login.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;

    @Builder
    public UserResponse(Long id) {
        this.id = id;
    }
}
