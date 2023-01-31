package com.portal.service;

import java.util.List;
import java.util.Optional;

import com.portal.dto.AppointmentDTO;
import com.portal.dto.TaskUserTimeDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.dto.request.InsertOrEditTaskUserTimeDTO;
import com.portal.dto.request.SearchAppointmentDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;

public interface ITaskUserTimeService {

	public Optional<TaskUserTimeDTO> saveNewTime(InsertOrEditTaskUserTimeDTO insertDTO, UserProfileDTO userProfile)
			throws AppException, BusException;

	public Optional<TaskUserTimeDTO> update(InsertOrEditTaskUserTimeDTO insertDTO, UserProfileDTO userProfile)
			throws AppException, BusException;

	public void finishByTaskUser(Integer taskUser) throws AppException;

	public Optional<TaskUserTimeDTO> saveOrUpdate(InsertOrEditTaskUserTimeDTO model, UserProfileDTO userProfile)
			throws AppException, BusException;

	public List<AppointmentDTO> searchAllRegistredTime(SearchAppointmentDTO searchDTO) throws AppException;
	
	public byte[] exportAllRegistredTime(SearchAppointmentDTO searchDTO) throws AppException;

}
