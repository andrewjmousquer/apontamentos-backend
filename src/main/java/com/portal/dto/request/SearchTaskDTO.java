package com.portal.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchTaskDTO {

	private Integer stage;
	private String jiraServiceOrderID;
	private String os;
	private String chassi;
	private Integer serviceOrder;
	private boolean inProgress;

	public SearchTaskDTO(Integer serviceOrder) {
		this.serviceOrder = serviceOrder;
	}

	public SearchTaskDTO(Integer serviceOrder, Integer stage) {
		this.serviceOrder = serviceOrder;
		this.stage = stage;
	}

	public SearchTaskDTO(boolean inProgress) {
		this.inProgress = inProgress;
	}

}
