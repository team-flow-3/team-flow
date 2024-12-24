package com.currency.teamflow.domain.attachment.entity;

import com.currency.teamflow.global.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "attachment")
public class Attachment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attachmentId;

	@NotNull
	private String file_type;

	@NotNull
	private String file_url;

	public Attachment(String file_type, String file_url) {
		this.file_type = file_type;
		this.file_url = file_url;
	}

	public Attachment() {

	}
}
