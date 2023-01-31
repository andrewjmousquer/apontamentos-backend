package com.portal.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertOrEditChecklistDTO {

	private Integer id;

	private String name;

	private String descrition;

	private String priorityOrder;

	private String tag;
	
	private List<InsertOrEditChecklistGroupDTO> groups;

}