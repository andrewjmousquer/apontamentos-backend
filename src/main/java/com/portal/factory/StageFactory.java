package com.portal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.StageDTO;
import com.portal.dto.request.InsertOrEditStageDTO;
import com.portal.dto.request.SearchStageDTO;
import com.portal.model.ChecklistModel;
import com.portal.model.CheckpointModel;
import com.portal.model.StageModel;

@Component
public class StageFactory {

	@Autowired
	CheckpointFactory checkpointFactory;
	
	@Autowired
	ChecklistFactory checklistFactory;

	@Autowired
	StageMovementFactory stageMovementFactory;

	public StageDTO convertFromModel(StageModel model) {

		StageDTO dtoToReturn = new StageDTO();
		dtoToReturn.setId(model.getId());
		dtoToReturn.setName(model.getName());
		dtoToReturn.setSpecial(model.getSpecial());
		dtoToReturn.setTask(model.getTask());
		dtoToReturn.setPaymentByTeam(model.getPaymentByTeam());
		dtoToReturn.setStatusJiraID(model.getStatusJiraID());
		dtoToReturn.setValue(model.getValue());
		dtoToReturn.setCheckpoint(
				model.getCheckpoint() != null ? checkpointFactory.convertFromModel(model.getCheckpoint()) : null);
		dtoToReturn.setChecklist(
				model.getChecklist() != null ? checklistFactory.convertFromModel(model.getChecklist()) : null);

		dtoToReturn.setMoviments(stageMovementFactory.convertFromListOfModels(model.getMoviments()));

		return dtoToReturn;

	}

	public List<StageDTO> convertFromListOfModels(List<StageModel> list) {

		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<StageDTO>();

	}

	/**
	 * @param dto
	 * @return
	 */
	public StageModel convertFromInsertDto(InsertOrEditStageDTO dto) {

		StageModel modelToReturn = new StageModel();

		modelToReturn.setId(dto.getId() != null ? dto.getId() : 0);
		modelToReturn.setName(dto.getName());
		modelToReturn.setStatusJiraID(dto.getStatusJiraID());
		modelToReturn.setValue(dto.getValue());

		if (dto.getCheckpoint() != null && dto.getCheckpoint() > 0)
			modelToReturn.setCheckpoint(new CheckpointModel(dto.getCheckpoint()));
		modelToReturn.setSpecial(dto.isSpecial());
		modelToReturn.setTask(dto.isTask());
		modelToReturn.setPaymentByTeam(dto.isPaymentByTeam());
		modelToReturn.setMoviments(stageMovementFactory.convertFromListOfInsertOrEditDTO(dto.getMoviments()));
		
		if (dto.getChecklist() != null && dto.getChecklist() > 0)
			modelToReturn.setChecklist(new ChecklistModel(dto.getChecklist()));
		
		
		
		return modelToReturn;

	}

	public StageModel convertFromFilterDto(SearchStageDTO dto) {

		StageModel modelToReturn = new StageModel();
		modelToReturn.setName(dto.getName());
		modelToReturn.setTask(dto.getTask());
		modelToReturn.setSpecial(dto.getSpecial());
		modelToReturn.setPaymentByTeam(dto.getPaymentByTeam());
		modelToReturn.setStatusJiraID(dto.getStatusJiraID());
		modelToReturn.setCheckpoint(
				dto.getCheckpoint() != null && dto.getCheckpoint() > 0 ? new CheckpointModel(dto.getCheckpoint())
						: null);
		modelToReturn.setChecklist(
				dto.getChecklist() != null && dto.getChecklist() > 0 ? new ChecklistModel(dto.getChecklist()) 
						: null);

		return modelToReturn;

	}

}
