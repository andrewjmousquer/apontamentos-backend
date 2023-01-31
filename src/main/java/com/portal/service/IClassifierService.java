package com.portal.service;

import java.util.List;

import com.portal.exceptions.AppException;
import com.portal.model.ClassifierModel;

public interface IClassifierService extends IBaseService<ClassifierModel> {

	public List<ClassifierModel> searchInIds(List<Integer> idsList) throws AppException;

}
