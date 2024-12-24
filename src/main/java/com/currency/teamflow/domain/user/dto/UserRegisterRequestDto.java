package com.currency.teamflow.domain.user.dto;

import com.currency.teamflow.global.enums.Auth;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterRequestDto {

    @NotBlank(message = "아이디는 이메일 형식이어야 합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 영문/숫자/특수문자를 포함해야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickName;

    private Auth auth;

    public UserRegisterRequestDto(String email, String password, String nickName, String auth) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.auth = Auth.valueOf(auth);
    }
}
