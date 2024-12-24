package com.currency.teamflow.domain.user.entity;

import com.currency.teamflow.global.base.BaseEntity;
import com.currency.teamflow.global.enums.Auth;
import com.currency.teamflow.global.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String email; // 이메일

	@NotNull
	private String nickName; // 닉네임

	@NotNull
	private String password; //비밀번호

	@OneToMany(mappedBy = "user")
	private List<UserWorkspace> userWorkspaces = new ArrayList<>();

	@NotNull
	@Enumerated(EnumType.STRING)
	private Auth auth; // 유저 권한

	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status; //유저 상태

	public User() {}

}
