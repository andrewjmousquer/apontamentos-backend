package com.portal.service;

import java.util.Optional;

import com.portal.dto.ChecklistResponseDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ChecklistModel;

public interface IChecklistService extends IBaseService<ChecklistModel> {

	public Optional<ChecklistResponseDTO> getAnswerByQuestion(Integer id) throws AppException, BusException;

	public String generateChecklistPDF(Integer taskID) throws AppException, BusException;

	public Optional<ChecklistResponseDTO> getAnswerByTask(Integer taskID) throws AppException, BusException;

}
