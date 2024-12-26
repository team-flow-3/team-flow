package com.currency.teamflow.domain.comment.entity;

import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.domain.user.entity.User;
import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column(columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;

	public Comment(User user, Card card, String comment) {
		this.user = user;
		this.card = card;
		this.comment = comment;
	}

	public Comment() {

	}

	public void updateComment(String comment) {
		this.comment = comment;
	}
}
