package com.currency.teamflow.domain.board.entity;

import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.domain.workspace.entity.Workspace;
import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
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

	private String imageUrl;//보드 이미지

	@OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.REMOVE)
	private List<BoardList> boardLists = new ArrayList<>();

	public Board() {
	}

	public Board( Workspace workspace, String boardTitle, String boardBackgroundColor, String imageUrl) {
		this.workspace = workspace;
		this.boardTitle = boardTitle;
		this.boardBackgroundColor = boardBackgroundColor;
		this.imageUrl = imageUrl;
	}

	public void saveImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
