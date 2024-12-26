package com.currency.teamflow.domain.user.dto;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.enums.Auth;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserViewResponseDto {

    private Long id;

    private String email;

    private String nickName;

    private Auth auth;


    public UserViewResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.auth = user.getAuth();
    }
}
