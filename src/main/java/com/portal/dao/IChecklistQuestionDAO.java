package com.portal.dao;

import java.util.List;

import com.portal.exceptions.AppException;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistQuestionModel;

public interface IChecklistQuestionDAO {

	public void save(ChecklistGroupModel checklistGroupModel, ChecklistQuestionModel model) throws AppException;

	public void delete(Integer checklistQuestion) throws AppException;

	public void delete(Integer ckgId, Integer qasId) throws AppException;

	public List<ChecklistQuestionModel> search(ChecklistQuestionModel model) throws AppException;

	List<ChecklistQuestionModel> getChecklistListQuestionByChecklistId(Integer id) throws AppException;

}
