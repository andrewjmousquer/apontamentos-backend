package com.portal.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUserTimeDTO {

	private Integer id;
	private Date dateStart;
	private Date dateFinish;

}
