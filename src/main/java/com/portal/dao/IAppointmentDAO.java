package com.portal.dao;

import java.util.List;

import com.portal.dto.AppointmentDTO;
import com.portal.dto.request.SearchAppointmentDTO;
import com.portal.exceptions.AppException;

public interface IAppointmentDAO {

	public List<AppointmentDTO> search(SearchAppointmentDTO appointment) throws AppException;

}
