package com.portal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InsertOrEditTaskUserDTO {

	private Integer id;
	private Integer task;
	private Integer user;
	private Integer status;

}
