package com.portal.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityModel {

	private Integer id;
	private String name;
	private Integer codIbge;
	private StateModel state;


}
