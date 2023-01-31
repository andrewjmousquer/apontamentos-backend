package com.portal.dto.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchServiceOrderDTO {

	private Integer id;

	private String numberJira;

	private Date dateStart;

	private Date dateFinish;

	private Integer statusOs;

	private String brand;

	private String model;

	private String plate;

	private String chassi;

	private String number;

}
