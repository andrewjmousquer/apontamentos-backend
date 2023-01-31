package com.portal.model;

import java.util.Date;
import java.util.List;

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
public class ChecklistAnswerModel {

	private Integer id;

	private Date creationDate;

	private ChecklistQuestionModel question;

	private String comment;

	private TaskModel task;

	private String responsibleForAnswer;

	private ClassifierModel answer;

	private List<ChecklistAnswerPhotoModel> photos;

	public ChecklistAnswerModel(Integer id) {
		this.id = id;
	}

	public ChecklistAnswerModel(TaskModel task) {
		this.task = task;
	}

}
