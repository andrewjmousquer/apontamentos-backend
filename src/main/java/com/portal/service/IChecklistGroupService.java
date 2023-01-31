package com.portal.service;

import java.util.List;

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistModel;

public interface IChecklistGroupService {


	public ChecklistModel save(ChecklistModel checklistModel) throws AppException, BusException;

	public ChecklistModel delete(ChecklistModel checklistModel) throws AppException, BusException;

	public ChecklistModel update(ChecklistModel checklistModel) throws AppException, BusException;

	List<ChecklistGroupModel> search(ChecklistGroupModel model) throws AppException, BusException;

	List<ChecklistGroupModel> getByChecklist(Integer id) throws AppException, BusException;

}