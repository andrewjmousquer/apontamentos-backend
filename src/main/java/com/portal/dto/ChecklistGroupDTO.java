package com.portal.dto;

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
public class ChecklistGroupDTO {

	@EqualsAndHashCode.Include
	private Integer id;

	private String name;

	private List<ChecklistQuestionDTO> questions;

}
