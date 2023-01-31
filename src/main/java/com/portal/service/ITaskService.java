package com.portal.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.context.NoSuchMessageException;

import com.portal.dto.TaskDTO;
import com.portal.dto.TaskWithTimeDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.dto.request.InsertOrEditTaskDTO;
import com.portal.dto.request.SearchTaskDTO;
import com.portal.enums.StatusType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.StageModel;
import com.portal.model.TaskModel;
import com.portal.model.TeamModel;

public interface ITaskService extends IBaseFactoryService<TaskModel, SearchTaskDTO, TaskDTO, InsertOrEditTaskDTO> {

	public Optional<TaskDTO> save(TaskModel model, UserProfileDTO userProfile) throws AppException, BusException;

	public List<TaskDTO> returnTasksByServiceOrderOrChassi(String osOrChassi, UserProfileDTO userProfile)
			throws NoSuchMessageException, IOException, InterruptedException, AppException, BusException;

	public Optional<TaskModel> getModelById(Integer id) throws AppException, BusException;

	public boolean changeStatusTask(Integer taskToChange, StatusType statusToGo, StatusType statusToConsider)
			throws AppException;

	public List<TaskDTO> searchByUser(SearchTaskDTO search, UserProfileDTO user) throws AppException, BusException;

	public boolean saveChecklistFile(Integer taskToChange, String fileName) throws AppException;

	public List<TaskWithTimeDTO> getAllByServiceOrderId(Integer id)
			throws AppException, NoSuchMessageException, BusException;

	public List<TeamModel> getTeamWhiTaskUser(Integer id) throws NoSuchMessageException, AppException;

	public Optional<TaskModel> getLastTaskByUser(Integer userID) throws NoSuchMessageException, AppException;

	public List<TaskModel> getTasksActiveOnTeamFinded(Integer id) throws NoSuchMessageException, AppException;

	public void generateTaskPayment(Integer taskID, StageModel stage) throws AppException;

}
