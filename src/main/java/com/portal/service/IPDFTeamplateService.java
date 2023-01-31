package com.portal.service;

import org.springframework.stereotype.Service;

import com.portal.dto.ChecklistResponseDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.TaskModel;

@Service
public interface IPDFTeamplateService {

	public String formatCheckListPDF(TaskModel task, ChecklistResponseDTO checklistResponseDTO)
			throws AppException, BusException;

}