package com.portal.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskUserTimeModel {

	private Integer id;
	private Date dateStart;
	private Date dateFinish;
	private TaskUserModel taskUser;

}
