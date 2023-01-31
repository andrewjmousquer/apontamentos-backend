package com.portal.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchChecklistGroupDTO {


	private Integer ckgId;

	private String name;

	private List<SearchChecklistQuestionDTO> questions;


}
