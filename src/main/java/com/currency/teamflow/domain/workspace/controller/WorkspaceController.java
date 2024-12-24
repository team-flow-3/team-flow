package com.currency.teamflow.domain.workspace.controller;

import com.currency.teamflow.domain.workspace.dto.WorkspaceRequestDto;
import com.currency.teamflow.domain.workspace.dto.WorkspaceResponseDto;
import com.currency.teamflow.domain.workspace.service.WorkspaceService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

		private final WorkspaceService workspaceService;

	/**
	 * 워크스페이스 생성 API
	 * - 관리자 전용
	 */
	@PostMapping
	public ResponseEntity<WorkspaceResponseDto> createWorkspace(
		@RequestBody WorkspaceRequestDto workspaceRequestDto) {

		WorkspaceResponseDto workspaceResponseDto = workspaceService.createWorkspace(
			workspaceRequestDto.getWorkspaceName(),
			workspaceRequestDto.getWorkspaceExplanation()
		);

		return new ResponseEntity<>(workspaceResponseDto, HttpStatus.CREATED);
	};
}
