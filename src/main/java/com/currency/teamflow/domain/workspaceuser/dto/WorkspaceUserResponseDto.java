package com.currency.teamflow.domain.workspaceuser.dto;

import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import lombok.Getter;

@Getter
public class WorkspaceUserResponseDto {
    private Long workspaceUserId;
    private Long userId;
    private Long workspaceId;
    private String role;

    public WorkspaceUserResponseDto(WorkspaceUser workspaceUser) {
        this.workspaceUserId = workspaceUser.getId();
        this.userId = workspaceUser.getUser().getId();
        this.workspaceId = workspaceUser.getWorkspace().getId();
        this.role = workspaceUser.getRole().name();
    }
}
