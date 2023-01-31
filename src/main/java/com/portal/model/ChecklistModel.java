package com.portal.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ChecklistModel {

	private Integer id;

	private String name;

	private String descrition;

	private String priorityOrder;

	private String tag;

	private List<ChecklistGroupModel> groups;

	private Integer numberOfGroups;

	private Integer numberOfQuestions;

	public ChecklistModel(String name) {
		this.name = name;
	}

	public ChecklistModel(Integer id) {
		this.id = id;
	}

}
