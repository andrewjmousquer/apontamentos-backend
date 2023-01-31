package com.portal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.portal.dto.TaskUserTimeDTO;
import com.portal.dto.request.InsertOrEditTaskUserTimeDTO;
import com.portal.model.TaskUserModel;
import com.portal.model.TaskUserTimeModel;

@Component
public class TaskUserTimeFactory {

	public TaskUserTimeDTO convertFromModel(TaskUserTimeModel model) {

		TaskUserTimeDTO dtoToReturn = new TaskUserTimeDTO();

		dtoToReturn.setId(model.getId());
		dtoToReturn.setDateStart(model.getDateStart());
		dtoToReturn.setDateFinish(model.getDateFinish());

		return dtoToReturn;

	}

	public List<TaskUserTimeDTO> convertFromListOfModels(List<TaskUserTimeModel> list) {

		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<TaskUserTimeDTO>();

	}

	public TaskUserTimeModel convertFromInsertDto(InsertOrEditTaskUserTimeDTO dto) {

		TaskUserTimeModel modelToReturn = new TaskUserTimeModel();

		modelToReturn.setId(dto.getId());
		modelToReturn.setDateFinish(dto.getDateFinish());
		modelToReturn.setDateStart(dto.getDateStart());

		if (dto.getTaskUser() != null && dto.getTaskUser() > 0)
			modelToReturn.setTaskUser(new TaskUserModel(dto.getTaskUser()));

		return modelToReturn;

	}

}
