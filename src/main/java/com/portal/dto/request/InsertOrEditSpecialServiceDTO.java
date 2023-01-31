package com.portal.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class InsertOrEditSpecialServiceDTO {

	private Integer taskID;
	private Integer movementID;
	private List<Integer> classifiersId;

}
