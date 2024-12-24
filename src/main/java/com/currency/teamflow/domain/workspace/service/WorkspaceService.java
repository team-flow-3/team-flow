package com.currency.teamflow.domain.workspace.service;

import com.currency.teamflow.domain.workspace.dto.WorkspaceResponseDto;
import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceService {

	private final WorkspaceRepository workspaceRepository;

	/**
	 * 워크스페이스 생성 API
	 * - 관리자 전용
	 */
	@Transactional
	public WorkspaceResponseDto createWorkspace(String workspaceName, String workspaceExplanation) {
		Workspace workspace = new Workspace(workspaceName, workspaceExplanation);
		//워크스페이스 저장
		Workspace savedWorkspace = workspaceRepository.save(workspace);

		return WorkspaceResponseDto.toDto(savedWorkspace);
	}
}
