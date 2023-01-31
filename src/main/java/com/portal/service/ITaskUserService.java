package com.portal.service;

import java.util.List;
import java.util.Optional;

import com.portal.dto.TaskUserDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.dto.request.InsertOrEditSpecialServiceDTO;
import com.portal.dto.request.InsertOrEditTaskUserDTO;
import com.portal.dto.request.SearchTaskUserDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.TaskUserModel;

public interface ITaskUserService
		extends IBaseFactoryService<TaskUserModel, SearchTaskUserDTO, TaskUserDTO, InsertOrEditTaskUserDTO> {

	Optional<TaskUserDTO> finishTask(Integer taskID, Integer userID, Integer movementID, UserProfileDTO userProfile)
			throws AppException, BusException;

	public Optional<TaskUserDTO> startTask(Integer taskID, Integer userID, UserProfileDTO userProfile)
			throws AppException, BusException;

	public Optional<TaskUserDTO> pauseTask(Integer taskID, Integer userID, UserProfileDTO userProfile)
			throws AppException, BusException;

	Optional<TaskUserDTO> resumeTask(Integer taskID, Integer userID, UserProfileDTO userProfile)
			throws AppException, BusException;

	public boolean specialService(InsertOrEditSpecialServiceDTO insertDTO, UserProfileDTO userProfile)
			throws AppException, BusException;

	public List<TaskUserDTO> searchInProgress(SearchTaskUserDTO search) throws AppException, BusException;

}
