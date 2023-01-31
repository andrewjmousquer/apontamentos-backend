package com.portal.dto.request;

import com.portal.enums.StatusType;
import com.portal.model.UserModel;

import lombok.Getter;

@Getter
public class SearchTaskUserDTO {

	private Integer task;
	private Integer user;
	private Integer status;

	public SearchTaskUserDTO(Integer task, Integer user) {
		this.task = task;
		this.user = user;
	}

	public SearchTaskUserDTO(Integer user, StatusType status) {
		this.user = user;
		this.status = status.getType().getId();
	}

	public SearchTaskUserDTO(Integer task) {
		this.task = task;
	}

	public SearchTaskUserDTO(UserModel user) {
		this.user = user.getId();
	}
}
