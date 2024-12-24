package com.currency.teamflow.domain.user.dto;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.enums.Auth;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRegisterResponseDto {

    private  Long id;

    private  String email;

    private  String nickName;

    private  Auth auth;

    private  LocalDateTime createdAt;

    public UserRegisterResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.auth = user.getAuth();
        this.createdAt = user.getCreatedAt();
    }
}
