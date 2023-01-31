package com.portal.factory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.TaskUserDTO;
import com.portal.dto.request.InsertOrEditTaskUserDTO;
import com.portal.dto.request.SearchTaskUserDTO;
import com.portal.enums.StatusType;
import com.portal.model.TaskModel;
import com.portal.model.TaskUserModel;
import com.portal.model.UserModel;
import com.portal.utils.PortalTimeUtils;

@Component
public class TaskUserFactory {

	@Autowired
	ClassifierFactory classifierFactory;

	@Autowired
	TaskFactory taskFactory;

	public TaskUserDTO convertFromModel(TaskUserModel model) {

		TaskUserDTO dtoToReturn = new TaskUserDTO();

		dtoToReturn.setId(model.getId());
		dtoToReturn.setDateStart(model.getDateStart());
		dtoToReturn.setDateFinish(model.getDateFinish());
		dtoToReturn.setStageValue(model.getStageValue());
		dtoToReturn.setRecivedValue(model.getRecivedValue());
		dtoToReturn.setName(model.getUser().getPerson().getName());
		if (model.getTask() != null)
			dtoToReturn.setTask(taskFactory.convertFromModel(model.getTask()));
		dtoToReturn.setUser(model.getUser().getId());
		if (model.getStatus() != null)
			dtoToReturn.setStatus(classifierFactory.convertFromModel(model.getStatus()));

		return dtoToReturn;

	}

	public List<TaskUserDTO> convertFromListOfModels(List<TaskUserModel> list) {

		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<TaskUserDTO>();

	}

	public TaskUserModel convertFromInsertDto(InsertOrEditTaskUserDTO dto) {

		TaskUserModel modelToReturn = new TaskUserModel();

		modelToReturn.setId(dto.getId());
		modelToReturn.setTask(new TaskModel(dto.getTask()));
		modelToReturn.setUser(new UserModel(dto.getUser()));
		modelToReturn.setDateStart(PortalTimeUtils.localDateTimeToDate(LocalDateTime.now()));

		if (dto.getStatus() > 0)
			modelToReturn.setStatus(StatusType.getById(dto.getStatus()).getType());

		return modelToReturn;

	}

	public TaskUserModel convertFromFilterDto(SearchTaskUserDTO dto) {

		TaskUserModel modelToReturn = new TaskUserModel();

		if (dto.getTask() != null && dto.getTask() > 0)
			modelToReturn.setTask(new TaskModel(dto.getTask()));
		if (dto.getUser() != null && dto.getUser() > 0)
			modelToReturn.setUser(new UserModel(dto.getUser()));
		if (dto.getStatus() != null && dto.getStatus() > 0)
			modelToReturn.setStatus(StatusType.getById(dto.getStatus()).getType());

		return modelToReturn;

	}

}
