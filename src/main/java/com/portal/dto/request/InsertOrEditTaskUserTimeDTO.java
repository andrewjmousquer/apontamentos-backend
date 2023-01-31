package com.portal.dto.request;

import java.util.Date;

import lombok.Getter;

@Getter
public class InsertOrEditTaskUserTimeDTO {

	private Integer id;
	private Date dateStart;
	private Date dateFinish;
	private Integer taskUser;

	public InsertOrEditTaskUserTimeDTO(Date dateStart, Integer taskUser) {
		this.dateStart = dateStart;
		this.taskUser = taskUser;
	}

}
