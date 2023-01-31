package com.portal.dto.request;

import lombok.Getter;

@Getter
public class InsertOrEditStageMovementDTO {

	private Integer id;
	private Integer jiraID;
	private Integer type;
	private String name;
	private String icon;
}
