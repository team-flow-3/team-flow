package com.currency.teamflow.domain.user.entity;

import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.global.base.BaseEntity;
import com.currency.teamflow.global.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user_workspace")
public class UserWorkspace extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; // 유저 고유 식별자

	@ManyToOne
	@JoinColumn(name = "workspace_id")
	private Workspace workspace; // 워크스페이스 고유 식별자

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role; // 유저 역할

	public UserWorkspace() {}


}
