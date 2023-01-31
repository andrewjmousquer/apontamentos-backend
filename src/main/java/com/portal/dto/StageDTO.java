package com.portal.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StageDTO {

	private Integer id;
	private String name;
	private Boolean task;
	private Boolean special;
	private Boolean paymentByTeam;
	private Integer statusJiraID;
	private CheckpointDTO checkpoint;
	private ChecklistDTO checklist;
	private BigDecimal value;
	private List<StageMovementDTO> moviments;

	public StageDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

}
