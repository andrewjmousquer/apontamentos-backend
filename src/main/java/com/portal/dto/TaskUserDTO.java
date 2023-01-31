package com.portal.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskUserDTO {

	private Integer id;
	private TaskDTO task;
	private Integer user;
	private String name;
	private ClassifierDTO status;
	private Date dateStart;
	private Date dateFinish;
	private BigDecimal stageValue;
	private BigDecimal recivedValue;

}
