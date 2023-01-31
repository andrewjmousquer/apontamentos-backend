package com.portal.dto;

import com.portal.model.TeamModel;
import com.portal.model.UserModel;

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
public class UserTeamDTO {

	private UserModel user;

	private TeamModel team;
}
