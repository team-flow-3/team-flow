package com.currency.teamflow.domain.workspace.repository;

import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.global.error.errorcode.ErrorCode;
import com.currency.teamflow.global.error.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

	default Workspace findByIdOrElseThrow(Long workspaceId){
		return findById(workspaceId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
	}

}
