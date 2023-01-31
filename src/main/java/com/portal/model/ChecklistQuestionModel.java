package com.portal.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
public class ChecklistQuestionModel {

	private Integer id;

	private String question;

	private Date creationDate;

	private Boolean active;

	private Integer cklId;

	public ChecklistQuestionModel(Integer id) {
		this.id = id;
	}

	public ChecklistQuestionModel(Integer id, String question, Date creationDate, Boolean active) {
		this.id = id;
		this.question = question;
		this.creationDate = creationDate;
		this.active = active;
	}

}
