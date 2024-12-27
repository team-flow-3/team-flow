package com.currency.teamflow.domain.workspace.entity;

import com.currency.teamflow.domain.user.entity.WorkspaceUser;
import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;


@Getter
@Entity
@Table(name = "workspace")
public class Workspace extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String workspaceName;//워크스페이스 이름

	@NotNull
	private String workspaceExplanation;//워크스페이스 설명

	@OneToMany(mappedBy = "workspace")
	private List<WorkspaceUser> workspaceUsers = new ArrayList<>();

	public Workspace() {
	}

	public Workspace(String workspaceName, String workspaceExplanation) {
		this.workspaceName = workspaceName;
		this.workspaceExplanation = workspaceExplanation;
	}

	//워크스페이스 정보 수정
	public void updateWorkspace(String workspaceName, String workspaceExplanation) {
		this.workspaceName = workspaceName;
		this.workspaceExplanation = workspaceExplanation;
	}
}
