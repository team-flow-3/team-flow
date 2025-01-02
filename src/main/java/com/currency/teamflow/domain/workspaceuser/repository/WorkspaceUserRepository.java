package com.currency.teamflow.domain.workspaceuser.repository;

import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.domain.workspaceuser.dto.WorkspaceUserDto;
import com.currency.teamflow.global.enums.Role;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {

    @Query("SELECT new com.currency.teamflow.domain.workspaceuser.dto.WorkspaceUserDto(w, u) " +
        "FROM Workspace w, user u " +
        "WHERE w.id = :workspaceId AND u.id = :userId")
    Optional<WorkspaceUserDto> findWorkspaceAndUserByIds(@Param("workspaceId") Long workspaceId,
        @Param("userId") Long userId);

    default WorkspaceUserDto findByWorkspaceAndUserByIdsElseThrow(Long workspaceId, Long userId) {
        return findWorkspaceAndUserByIds(workspaceId, userId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND)
        );
    }

    default WorkspaceUser findByWorkspaceOrElseThrow(Long workspaceUserId) {
        return findById(workspaceUserId).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND));
    }

    boolean existsByWorkspaceIdAndUserId(Long workspaceId, Long userId);

    Optional<WorkspaceUser> findById(Long id);

    Optional<WorkspaceUser> findByWorkspaceIdAndUserId(Long workspaceId, Long userId);

    @Query("SELECT wu "
        + "FROM WorkspaceUser wu "
        + "WHERE wu.user.id = :userId AND wu.workspace.id = :workspaceId")
    Optional<WorkspaceUser> findByWorkspaceIdAndUserByIdOrElseThrow(Long userId, Long workspaceId);

    Optional<WorkspaceUser> findByUserIdAndRole(Long userId, Role role);

    default WorkspaceUser findByUserIdAndRoleOrElseThrow(Long userId, Role role) {
        return findByUserIdAndRole(userId, role).orElseThrow(
            () -> new CustomException(ErrorCode.NOT_FOUND));
    }

    Optional<WorkspaceUser> findByUserIdAndWorkspaceId(Long userId, Long workspaceId);

    List<WorkspaceUser> findAllByUserIdInAndWorkspaceId(List<Long> userIds, Long workspaceId);
}
