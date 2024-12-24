package com.currency.teamflow.domain.workspace.entity;

import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
	private Long workspaceExplanation;//워크스페이스 설명

	public Workspace() {
	}
}
