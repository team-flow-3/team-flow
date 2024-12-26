package com.currency.teamflow.domain.workspaceuser.service;

import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.domain.user.repository.UserRepository;
import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.domain.workspace.repository.WorkspaceRepository;
import com.currency.teamflow.domain.workspaceuser.dto.WorkspaceUserDto;
import com.currency.teamflow.domain.workspaceuser.dto.WorkspaceUserResponseDto;
import com.currency.teamflow.domain.workspaceuser.repository.WorkspaceUserRepository;
import com.currency.teamflow.global.enums.Auth;
import com.currency.teamflow.global.enums.Role;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkspaceUserService {

    private final WorkspaceUserRepository workspaceUserRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;

    public WorkspaceUserService(WorkspaceUserRepository workspaceUserRepository,
                                UserRepository userRepository,
                                WorkspaceRepository workspaceRepository) {

        this.workspaceUserRepository = workspaceUserRepository;
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
    }

    @Transactional
    public WorkspaceUserResponseDto assignWorkspaceAdmin(Long workspaceId, Long userId) {

        WorkspaceUserDto dto = workspaceUserRepository.findByWorkspaceAndUserByIdsElseThrow(workspaceId, userId);

        // WorkspaceUser 생성
        WorkspaceUser workspaceUser = new WorkspaceUser(dto.getUser(), dto.getWorkspace(), Role.WORKSPACE);

        // 역할 설정
        workspaceUser.setRole(Role.WORKSPACE);
        workspaceUserRepository.save(workspaceUser);

        // 반환
        return new WorkspaceUserResponseDto(workspaceUser);
    }


}

