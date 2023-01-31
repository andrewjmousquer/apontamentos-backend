package com.portal.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskUserModel {

	private Integer id;
	private TaskModel task;
	private UserModel user;
	private ClassifierModel status;
	private Date dateStart;
	private Date dateFinish;
	private BigDecimal stageValue;
	private BigDecimal recivedValue;
	private List<TaskUserTimeModel> timesRegistreds;

	public TaskUserModel(TaskModel task, UserModel user) {
		this.task = task;
		this.user = user;
	}

	public TaskUserModel(Integer id) {
		this.id = id;
	}

}
