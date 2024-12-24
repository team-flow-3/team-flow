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

	private String comment;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;

	public Comment(String comment, User user) {
		this.comment = comment;
		this.user = user;
	}

	public Comment() {

	}

	public void updateComment(String comment) {
		this.comment = comment;
	}
}
