package com.portal.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistAnswerDTO {

	@EqualsAndHashCode.Include
	private Integer id;

	private Date creationDate;

	private ChecklistQuestionDTO question;

	private String comment;

	private Integer task;

	private String responsibleForAnswer;

	private ClassifierDTO answer;

	private List<ChecklistAnswerPhotoDTO> photos;

	public ChecklistAnswerDTO(Integer id) {
		super();
		this.id = id;
	}

}
