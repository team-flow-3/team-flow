package com.currency.teamflow.domain.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;

    private String email;

    private String message;

    public UserResponseDto(Long id, String email, String message) {
        this.id = id;
        this.email = email;
        this.message = message;
    }
}
