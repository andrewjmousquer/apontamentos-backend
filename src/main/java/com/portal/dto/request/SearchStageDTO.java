package com.portal.dto.request;

import lombok.Getter;

@Getter
public class SearchStageDTO {

	private String name;
	private Boolean task;
	private Boolean special;
	private Boolean paymentByTeam;
	private Integer statusJiraID;
	private Integer checkpoint;
	private Integer checklist;

}
