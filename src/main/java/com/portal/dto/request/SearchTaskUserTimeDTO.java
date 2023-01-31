package com.portal.dto.request;

import lombok.Getter;

@Getter
public class SearchTaskUserTimeDTO {

	private Integer task;
	private Integer user;
	private Integer team;

	public SearchTaskUserTimeDTO(Integer task, Integer user) {
		super();
		this.task = task;
		this.user = user;
	}

}
