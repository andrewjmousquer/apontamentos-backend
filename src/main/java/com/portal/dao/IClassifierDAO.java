package com.portal.dao;

import java.util.List;

import com.portal.exceptions.AppException;
import com.portal.model.ClassifierModel;

public interface IClassifierDAO extends IBaseDAO<ClassifierModel> {

	public List<ClassifierModel> searchMultipleIds(List<Integer> idsList) throws AppException;

}
