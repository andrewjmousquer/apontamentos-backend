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
public class ChecklistDTO {

	@EqualsAndHashCode.Include
	private Integer id;

	private String name;

	private String descrition;

	private String priorityOrder;

	private String tag;

	private List<ChecklistGroupDTO> groups;
	
	private Integer numberOfGroups;

	private Integer numberOfQuestions;


}
