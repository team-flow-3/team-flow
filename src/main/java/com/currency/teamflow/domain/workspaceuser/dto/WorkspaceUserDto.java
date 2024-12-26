package com.currency.teamflow.domain.workspaceuser.dto;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.workspace.entity.Workspace;
import lombok.Getter;

@Getter
public class WorkspaceUserDto {

    private final Workspace workspace;
    private final User user;

    public WorkspaceUserDto(Workspace workspace, User user) {
        this.workspace = workspace;
        this.user = user;
    }
}
