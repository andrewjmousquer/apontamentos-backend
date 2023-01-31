package com.portal.dto;

import java.util.Date;

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

public class ChecklistQuestionDTO {
	@EqualsAndHashCode.Include
	private Integer id;

	private String question;

	private Date creationDate;

	private Boolean active;

	private Integer groupID;

}
