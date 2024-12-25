package com.currency.teamflow.domain.card.entity;

import com.currency.teamflow.domain.attachment.entity.Attachment;
import com.currency.teamflow.domain.boardlist.entity.BoardList;
import com.currency.teamflow.domain.comment.entity.Comment;
import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "card")
public class Card extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardId;

	@NotNull
	private String cardTitle;

	@NotNull
	private String cardExplanation;

	private LocalDateTime endAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "list_id")
	private BoardList boardList;

	@OneToMany(mappedBy = "card")
	private List<Attachment> attachments = new ArrayList<>();

	@OneToMany(mappedBy = "card")
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "card")
	private List<CardManager> cardManagers = new ArrayList<>();

	public Card(String cardTitle, String cardExplanation, LocalDateTime endAt) {
		this.cardTitle = cardTitle;
		this.cardExplanation = cardExplanation;
		this.endAt = endAt;
	}

	public Card() {

	}

	public void addCardManagers(List<CardManager> cardManagers) {
		this.cardManagers.addAll(cardManagers);
	}
}
