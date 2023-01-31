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
public class ChecklistAnswerPhotoModel {

	private Integer id;

	private String fileName;

	private Date creationDate;

	private ChecklistAnswerModel answer;

	public ChecklistAnswerPhotoModel(Integer answer) {
		this.answer = new ChecklistAnswerModel(answer);
	}

}
