package com.portal.service;

import java.util.List;

import com.portal.dto.ChecklistAnswerDTO;
import com.portal.dto.request.InsertOrEditChecklistAnswerDTO;
import com.portal.dto.request.SearchChecklistAnswerDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ChecklistAnswerModel;

public interface IChecklistAnswerService extends
		IBaseFactoryService<ChecklistAnswerModel, SearchChecklistAnswerDTO, ChecklistAnswerDTO, InsertOrEditChecklistAnswerDTO> {

	List<ChecklistAnswerModel> getByCheckListId(Integer id) throws AppException;

	List<ChecklistAnswerDTO> searchWithPhotos(ChecklistAnswerModel search) throws AppException, BusException;

}
