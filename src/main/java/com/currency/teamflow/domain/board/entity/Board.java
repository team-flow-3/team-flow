package com.currency.teamflow.domain.board.entity;

import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
@Table(name = "board")
public class Board extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//연관관계 - N:1
	@ManyToOne
	@JoinColumn(name = "workspace_id")
	private Workspace workspace;//워크스페이스 id(외래키)

	@NotNull
	private String boardTitle;//보드 제목

	@NotNull
	private String boardBackgroundColor;//보드 배경색

	@NotNull
	private String imageUrl;//보드 이미지

	public Board() {
	}
}
