package com.portal.dto;

import com.portal.model.ClassifierModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StageMovementDTO {

	private Integer id;
	private Integer jiraID;
	private ClassifierModel type;
	private String name;
	private String icon;

}
