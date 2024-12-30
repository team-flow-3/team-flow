package com.currency.teamflow.domain.card.entity;

import com.currency.teamflow.domain.attachment.entity.Attachment;
import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.domain.comment.entity.Comment;
import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "card", indexes = {
		@Index(name = "idx_end_at", columnList = "end_At")
})
public class Card extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardId;

	@NotNull
	private String cardTitle;

	@NotNull
	private String cardExplanation;

	private LocalDate endAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_list_id")
	private BoardList boardList;

	@OneToMany(mappedBy = "card", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Attachment> attachments = new ArrayList<>();

	@OneToMany(mappedBy = "card", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "card", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<CardManager> cardManagers = new ArrayList<>();

	public Card(String cardTitle, String cardExplanation, LocalDate endAt) {
		this.cardTitle = cardTitle;
		this.cardExplanation = cardExplanation;
		this.endAt = endAt;
	}

	public Card() {

	}

	public void addBoardList(BoardList boardList) {
		this.boardList = boardList;
		boardList.getCards().add(this);
	}

	public void updateCardManagers(List<CardManager> cardManagers) {
		this.cardManagers.clear();
		this.cardManagers.addAll(cardManagers);
	}


	public void updateCard(String cardTitle, String cardExplanation, LocalDate endAt, List<CardManager> cardManagers) {
		if(cardTitle != null) {
			this.cardTitle = cardTitle;
		}
		if (cardExplanation != null) {
			this.cardExplanation = cardExplanation;
		}
		if (endAt != null) {
			this.endAt = endAt;
		}
		if (cardManagers != null && !cardManagers.isEmpty()) {
			updateCardManagers(cardManagers);
		}
	}
}
