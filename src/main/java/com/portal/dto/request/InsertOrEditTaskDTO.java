package com.portal.dto.request;

import lombok.Getter;

@Getter
public class InsertOrEditTaskDTO {

	private Integer id;
	private String numberJira;
	private String name;
	private Integer status;
	private Integer stage;
	private Integer serviceOrder;

}
