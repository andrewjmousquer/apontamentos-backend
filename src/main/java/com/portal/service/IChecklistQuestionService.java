package com.portal.service;

import java.util.List;

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistQuestionModel;

public interface IChecklistQuestionService {

	List<ChecklistQuestionModel> search(ChecklistQuestionModel model) throws AppException, BusException;

	List<ChecklistQuestionModel> getById(Integer id) throws AppException, BusException;

	public ChecklistGroupModel update(ChecklistGroupModel checklistModel) throws AppException, BusException;

	public ChecklistGroupModel save(ChecklistGroupModel checklistModel) throws AppException, BusException;

	public void delete(Integer checklistModelId) throws AppException, BusException;

}