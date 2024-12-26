package com.currency.teamflow.domain.attachment.entity;

import com.currency.teamflow.domain.card.entity.Card;
import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
@Table(name = "attachment")
public class Attachment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attachmentId;

	@NotNull
	private String fileName;

	@NotNull
	private String uuidFileName;

	@NotNull
	private String fileType;

	@NotNull
	private String fileUrl;

	@ManyToOne
	@JoinColumn(name = "card_id")
	private Card card;

	public Attachment(String fileName, String uuidFileName, String fileType, String fileUrl, Card card) {
		this.fileName = fileName;
		this.uuidFileName = uuidFileName;
		this.fileType = fileType;
		this.fileUrl = fileUrl;
		updateCard(card);
	}

	public Attachment() {

	}

	public void updateCard(Card card) {
		this.card = card;
		card.getAttachments().add(this);
	}
}
