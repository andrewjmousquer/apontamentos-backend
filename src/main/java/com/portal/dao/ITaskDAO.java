package com.portal.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.context.NoSuchMessageException;

import com.portal.dto.UserProfileDTO;
import com.portal.enums.StatusType;
import com.portal.exceptions.AppException;
import com.portal.model.TaskModel;
import com.portal.model.TeamModel;

public interface ITaskDAO extends IBaseDAO<TaskModel> {

	public void updateTaskStatus(Integer taskToChange, StatusType statusToGo, StatusType statusToConsider)
			throws AppException;

	public List<TaskModel> searchByUser(TaskModel model, UserProfileDTO user, boolean b) throws AppException;

	public void saveChecklistFile(Integer taskToChange, String checklistFileName) throws AppException;

	public Optional<List<TaskModel>> getByServiceOrderId(Integer id) throws AppException;

	public List<TeamModel> getTeamWhiTaskUser(Integer id) throws NoSuchMessageException, AppException;

	public Optional<TaskModel> getLastTaskByUser(Integer userID) throws NoSuchMessageException, AppException;

	public List<TaskModel> getTasksActiveOnTeamFinded(Integer id) throws NoSuchMessageException, AppException;

	public void generateTaskPayment(Integer taskToPay, BigDecimal valueToPay, Boolean sharedByTeam) throws AppException;

}
