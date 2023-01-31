package com.portal.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskWithTimeDTO {

	private Integer id;
	private String name;
	private Date dateStart;
	private Date dateFinish;
	private ClassifierDTO status;
	private StageDTO stage;
	private ServiceOrderDTO serviceOrder;
	private String totalTime;
	private String checklistFile;

}
