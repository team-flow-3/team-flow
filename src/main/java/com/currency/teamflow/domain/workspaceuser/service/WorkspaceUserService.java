package com.currency.teamflow.domain.workspaceuser.service;

import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.domain.user.repository.UserRepository;
import com.currency.teamflow.domain.workspace.repository.WorkspaceRepository;
import com.currency.teamflow.domain.workspaceuser.dto.WorkspaceUserDto;
import com.currency.teamflow.domain.workspaceuser.dto.WorkspaceUserResponseDto;
import com.currency.teamflow.domain.workspaceuser.repository.WorkspaceUserRepository;
import com.currency.teamflow.global.enums.Role;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkspaceUserService {

    private final WorkspaceUserRepository workspaceUserRepository;


    public WorkspaceUserService(WorkspaceUserRepository workspaceUserRepository
                              ) {

        this.workspaceUserRepository = workspaceUserRepository;

    }

    /**
     * ADMIN이 특정 워크스페이스의 사용자를 WORKSPACE_ADMIN으로 설정
     * @param workspaceId 워크스페이스 ID
     * @param userId      사용자 ID
     * @return WorkspaceUserResponseDto
     */
    @Transactional
    public WorkspaceUserResponseDto assignWorkspaceAdmin(Long workspaceId, Long userId) {

        WorkspaceUserDto dto = workspaceUserRepository.findByWorkspaceAndUserByIdsElseThrow(workspaceId, userId);

        Optional<WorkspaceUser> workspaceUserOptional =
            workspaceUserRepository.findByUserIdAndWorkspaceId(userId, workspaceId);

        // 이미 존재하는 경우 예외 처리
        if(workspaceUserOptional.isPresent() && workspaceUserOptional.get().getRole() == Role.WORKSPACE_ADMIN){
            throw new CustomException(ErrorCode.DUPLICATE_VALUE);
        }

        // 이미 존재하지만 권한이 다른 경우 권한 변경
        workspaceUserOptional.ifPresent(workspaceUser -> workspaceUser.setRole(Role.WORKSPACE_ADMIN));

        // WorkspaceUser 생성
        WorkspaceUser workspaceUser = new WorkspaceUser(dto.getUser(), dto.getWorkspace(), Role.WORKSPACE_ADMIN);

        // 역할 설정
        workspaceUser.setRole(Role.WORKSPACE_ADMIN);
        workspaceUserRepository.save(workspaceUser);

        // 반환
        return new WorkspaceUserResponseDto(workspaceUser);
    }


    /**
     * ADMIN이 특정 워크스페이스 관리자를 삭제
     * @param userId 워크스페이스 사용자 ID
     * @return 삭제 완료 메시지
     */
    @Transactional
    public void deleteWorkspaceAdmin(Long userId) {
        // WorkspaceUser 조회
        WorkspaceUser workspaceUser = workspaceUserRepository.findByUserIdAndRoleOrElseThrow(userId, Role.WORKSPACE_ADMIN);

        // 삭제 처리
        workspaceUserRepository.delete(workspaceUser);
    }

    /**
     * 로그인 사용자와 워크스페이스 ID로 WorkspaceUser를 조회
     */
    public WorkspaceUser findWorkspaceUser(Long workspaceId, Long userId) {
        return workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }
}

