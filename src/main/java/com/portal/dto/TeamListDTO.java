package com.portal.dto;

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
public class TeamListDTO {

	@EqualsAndHashCode.Include
	private Integer id;

	private String name;

	private String abbreviation;

	private Integer numberOfUsers;

	private Integer numberOfStages;

}