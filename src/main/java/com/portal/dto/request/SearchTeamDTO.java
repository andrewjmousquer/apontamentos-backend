package com.portal.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchTeamDTO {

	private Integer id;
	private String name;
	private String abbreviation;
	private List<Integer> users;
	private List<Integer> stages;
	private Integer numberOfStages;
	private Integer numberOfUsers;
}
