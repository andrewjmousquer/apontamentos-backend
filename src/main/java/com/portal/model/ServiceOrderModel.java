package com.portal.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOrderModel {

	private Integer id;

	private String numberJira;

	private Date dateStart;

	private Date dateFinish;

	private ClassifierModel statusOs;

	private String brand;

	private String model;

	private String plate;

	private String chassi;

	private String number;

	public ServiceOrderModel(String chassi, String number) {
		this.chassi = chassi;
		this.number = number;
	}

	public ServiceOrderModel(Integer id) {
		this.id = id;
	}

}
