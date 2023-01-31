package com.portal.service;

import com.portal.dto.ServiceOrderDashboardDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ServiceOrderModel;

public interface IServiceOrderService extends IBaseService<ServiceOrderModel> {

	public ServiceOrderDashboardDTO getDashboardById(Integer id) throws AppException, BusException;

}
