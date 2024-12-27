package com.currency.teamflow.domain.workspace.repository;

import com.currency.teamflow.domain.workspace.dto.UserWorkspaceListResponseDto;
import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

	default Workspace findByIdOrElseThrow(Long workspaceId){
		return findById(workspaceId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_WORKSPACE));
	}


	@Query("SELECT new com.currency.teamflow.domain.workspace.dto.UserWorkspaceListResponseDto(wu.workspace.id, wu.workspace.workspaceName, wu.workspace.workspaceExplanation, wu.role) "
		+ "FROM WorkspaceUser wu "
		+ "WHERE wu.user.id = :userId")
	List<UserWorkspaceListResponseDto> findAllWorkspaceByUserId(Long userId);
}
