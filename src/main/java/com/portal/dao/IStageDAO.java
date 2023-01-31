package com.portal.dao;

import java.util.List;

import com.portal.exceptions.AppException;
import com.portal.model.StageModel;

public interface IStageDAO extends IBaseDAO<StageModel> {

	public List<StageModel> findByStage(Integer id) throws AppException;

}
