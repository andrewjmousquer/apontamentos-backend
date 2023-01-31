package com.portal.dto;

import java.util.Date;

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
public class ServiceOrderDTO {

	@EqualsAndHashCode.Include
	private Integer id;

	private String numberJira;

	private Date dateStart;

	private Date dateFinish;

	private ClassifierDTO statusOs;

	private String brand;

	private String model;

	private String plate;

	private String chassi;

	private String number;

}
