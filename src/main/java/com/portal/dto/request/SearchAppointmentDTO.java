package com.portal.dto.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchAppointmentDTO {

	private String name;

	private String numberOS;

	private String brand;

	private String model;

	private Date dateStart;

	private Date dateFinish;

	private Integer stage;

}
