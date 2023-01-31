package com.portal.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class AppointmentDTO {

	private Integer idRegisterTime;
	private String userName;
	private String numberOS;
	private String brand;
	private String model;
	private String stageName;
	private Date dateStart;
	private Date dateFinish;
	private String totalTime;
	private BigDecimal value;
	private Boolean paymentByTeam; 
	private BigDecimal valueRecived;

}
