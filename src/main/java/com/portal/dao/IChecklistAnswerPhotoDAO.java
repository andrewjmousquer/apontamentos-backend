package com.portal.dao;

import com.portal.exceptions.AppException;
import com.portal.model.ChecklistAnswerPhotoModel;

public interface IChecklistAnswerPhotoDAO extends IBaseDAO<ChecklistAnswerPhotoModel> {

	public void deleteByAnswer(Integer id) throws AppException;

}
