package com.currency.teamflow.domain.user.dto;


import com.currency.teamflow.global.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RoleUpdateDto {

    @NotNull(message = "멤버 ID는 필수입니다.")
    private Long memberId;

    @NotNull(message = "변경할 역할은 필수입니다.")
    private Role newRole;

}
