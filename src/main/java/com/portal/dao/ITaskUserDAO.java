package com.portal.dao;

import java.util.List;

import org.springframework.context.NoSuchMessageException;

import com.portal.exceptions.AppException;
import com.portal.model.TaskUserModel;

public interface ITaskUserDAO extends IBaseDAO<TaskUserModel> {

	public void pauseAllTaskOfUser(Integer userID) throws AppException;

	public void pauseOneTaskByID(Integer taskUserID) throws AppException;

	public Integer getNumberOfUsersInTask(Integer taskID, Integer userID) throws NoSuchMessageException, AppException;

	public void finishOneTaskByID(Integer taskUserID) throws AppException;

	public Boolean canFinishTheTask(Integer taskID) throws NoSuchMessageException, AppException;

	public List<TaskUserModel> searchInProgress(TaskUserModel model) throws AppException;

}
