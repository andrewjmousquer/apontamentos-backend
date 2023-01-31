package com.portal.dto.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;

@Getter
public class InsertOrEditStageDTO {

	private Integer id;
	private String name;
	private boolean task;
	private boolean special;
	private boolean paymentByTeam;
	private Integer statusJiraID;
	private Integer checkpoint;
	private Integer checklist;
	private BigDecimal value;
	private List<InsertOrEditStageMovementDTO> moviments;

}
