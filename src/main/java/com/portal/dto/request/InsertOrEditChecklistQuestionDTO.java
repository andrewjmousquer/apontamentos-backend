package com.portal.dto.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertOrEditChecklistQuestionDTO {

	private Integer id;

	private String question;

	private Date creationDate;

	private Boolean active;

}
