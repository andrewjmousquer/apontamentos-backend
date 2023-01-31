package com.portal.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

	@EqualsAndHashCode.Include
	private Integer id;

	private String name;

	private String abbreviation;

	private List<UserDTO> users;

	private List<StageDTO> stages;

}
