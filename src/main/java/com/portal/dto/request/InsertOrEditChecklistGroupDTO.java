
package com.portal.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertOrEditChecklistGroupDTO {

	private Integer id;

	private String name;

	private List<InsertOrEditChecklistQuestionDTO> questions;

}
