package com.portal.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDTO {

	private Integer id;
	private String name;
	private String menuPath;
	private String description;
	private String icon;
	private MenuDTO root;
	private ClassifierDTO type;
	private Integer mnuOrder;
	private List<MenuDTO> submenus;

}
