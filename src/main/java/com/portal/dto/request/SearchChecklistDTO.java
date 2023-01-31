package com.portal.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchChecklistDTO {

	private Integer id;

	private String name;

	private String descrition;

	private String priorityOrder;
	
	private Integer numberOfGroups;

	private Integer numberOfQuestions;

}
