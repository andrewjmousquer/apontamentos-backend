package com.portal.factory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.TaskDTO;
import com.portal.dto.TaskWithTimeDTO;
import com.portal.dto.request.InsertOrEditTaskDTO;
import com.portal.dto.request.SearchTaskDTO;
import com.portal.enums.FileType;
import com.portal.enums.StatusType;
import com.portal.exceptions.AppException;
import com.portal.integration.jira.IntegrationJIRA;
import com.portal.model.ServiceOrderModel;
import com.portal.model.StageModel;
import com.portal.model.TaskModel;
import com.portal.service.IFilesService;

@Component
public class TaskFactory {

	@Autowired
	ServiceOrderFactory serviceOrderFactory;

	@Autowired
	StageFactory stageFactory;

	@Autowired
	ClassifierFactory classifierFactory;

	@Autowired
	IFilesService fileService;

	public TaskDTO convertFromModel(TaskModel model) {

		TaskDTO dtoToReturn = new TaskDTO();
		dtoToReturn.setId(model.getId());
		dtoToReturn.setName(model.getName());
		dtoToReturn.setDateStart(model.getDateStart());
		dtoToReturn.setDateFinish(model.getDateFinish());
		dtoToReturn.setSpecialServicePlace(model.getPlace());

		if (model.getServiceOrder() != null)
			dtoToReturn.setServiceOrder(serviceOrderFactory.convertFromModel(model.getServiceOrder()));
		if (model.getStage() != null)
			dtoToReturn.setStage(stageFactory.convertFromModel(model.getStage()));
		if (model.getStatus() != null)
			dtoToReturn.setStatus(classifierFactory.convertFromModel(model.getStatus()));

		return dtoToReturn;

	}

	public TaskWithTimeDTO convertFromModelWithTime(TaskModel model) throws URISyntaxException {

		TaskWithTimeDTO dtoToReturn = new TaskWithTimeDTO();
		dtoToReturn.setId(model.getId());
		dtoToReturn.setName(model.getName());
		dtoToReturn.setDateStart(model.getDateStart());
		dtoToReturn.setDateFinish(model.getDateFinish());
		dtoToReturn.setTotalTime(model.getTotalTime());
		if (StringUtils.isNotEmpty(model.getCheckListFile()))
			dtoToReturn.setChecklistFile(
					fileService.getVirtualPath(FileType.CHECKLIST_PDF, model.getCheckListFile()).toString());

		if (model.getServiceOrder() != null)
			dtoToReturn.setServiceOrder(serviceOrderFactory.convertFromModel(model.getServiceOrder()));
		if (model.getStage() != null)
			dtoToReturn.setStage(stageFactory.convertFromModel(model.getStage()));
		if (model.getStatus() != null)
			dtoToReturn.setStatus(classifierFactory.convertFromModel(model.getStatus()));

		return dtoToReturn;

	}

	public List<TaskDTO> convertFromListOfModels(List<TaskModel> list) {

		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<TaskDTO>();

	}

	public List<TaskWithTimeDTO> convertFromListOfModelsWithTime(List<TaskModel> list) throws AppException {
		List<TaskWithTimeDTO> toReturn = new ArrayList<>();
		try {
			if (list != null && list.size() > 0) {
				for (TaskModel model : list) {
					toReturn.add(convertFromModelWithTime(model));

				}
			}
		} catch (URISyntaxException e) {

			e.printStackTrace();
			throw new AppException("Erro ao tentar converter o Model para DTO");
		}

		return toReturn;

	}

	public TaskModel convertFromInsertDto(InsertOrEditTaskDTO dto) {

		TaskModel modelToReturn = new TaskModel();

		modelToReturn.setId(dto.getId() != null ? dto.getId() : 0);
		modelToReturn.setName(dto.getName());
		modelToReturn.setServiceOrder(new ServiceOrderModel(dto.getServiceOrder()));
		modelToReturn.setStage(new StageModel(dto.getStage()));
		modelToReturn.setStatus(StatusType.getById(dto.getStatus()).getType());

		if (dto.getId().equals(0))
			modelToReturn.setDateStart(new Date());

		return modelToReturn;

	}

	public TaskModel convertFromFilterDto(SearchTaskDTO dto) {

		TaskModel modelToReturn = new TaskModel();

		modelToReturn.setServiceOrder(new ServiceOrderModel(dto.getServiceOrder()));
		modelToReturn.setStage(new StageModel(dto.getStage()));

		if (StringUtils.isNotEmpty(dto.getOs()) && StringUtils.isNotEmpty(dto.getChassi()))
			modelToReturn.setServiceOrder(new ServiceOrderModel(dto.getOs(), dto.getChassi()));

		modelToReturn.setNumberJira(dto.getJiraServiceOrderID());

		return modelToReturn;

	}

	public TaskModel convertFromJiraIntegration(IntegrationJIRA jira, StageModel stage,
			ServiceOrderModel serviceOrder) {

		TaskModel modelToReturn = new TaskModel();

		modelToReturn.setId(0);
		modelToReturn.setNumberJira(jira.getKey());
		modelToReturn.setName(stage.getName());
		modelToReturn.setServiceOrder(serviceOrder);
		modelToReturn.setStage(stage);
		modelToReturn.setStatus(StatusType.AGUARDANDO_INICIO.getType());
		modelToReturn.setDateStart(new Date());

		if (jira.getFields().getLocaisServicoEspeciais().size() > 0)
			modelToReturn.setPlace(jira.getFields().getLocaisServicoEspeciais().toString());

		return modelToReturn;

	}

}
