package com.portal.service;

import java.util.List;

import com.portal.dto.UserProfileDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.StageModel;

public interface IStageService extends IBaseService<StageModel> {

	public void deleteMovement(Integer id, UserProfileDTO userProfile) throws AppException, BusException;

	List<StageModel> findByStage(Integer id) throws AppException, BusException;

}