package com.portal.dto;

import com.portal.model.StageModel;
import com.portal.model.TeamModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StageTeamDTO {

	private StageModel stage;

	private TeamModel team;
}
