package com.portal.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceOrderDashboardDTO {

	private ServiceOrderDTO serviceOrder;
	private List<TaskWithTimeDTO> tasks;

}
