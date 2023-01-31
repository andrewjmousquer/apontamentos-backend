package com.portal.dao;

import java.util.List;

import com.portal.exceptions.AppException;
import com.portal.model.ChecklistAnswerModel;

public interface IChecklistAnswerDAO extends IBaseDAO<ChecklistAnswerModel> {

	List<ChecklistAnswerModel> getByCheckListId(Integer id) throws AppException;

}
