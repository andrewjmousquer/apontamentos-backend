package com.portal.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchChecklistAnswerDTO {

	private Integer id;

	private Integer question;

	private Integer checklist;

	public SearchChecklistAnswerDTO(Integer checklist) {

		this.checklist = checklist;
	}

}
