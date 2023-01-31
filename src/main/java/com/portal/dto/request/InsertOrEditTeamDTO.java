package com.portal.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertOrEditTeamDTO {

	private Integer id;
	private String name;
	private String abbreviation;
	private List<Integer> users;
	private List<Integer> stages;

}
