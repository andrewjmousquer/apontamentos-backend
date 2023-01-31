package com.portal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.StageMovementDTO;
import com.portal.dto.request.InsertOrEditStageMovementDTO;
import com.portal.model.ClassifierModel;
import com.portal.model.StageMovementModel;

@Component
public class StageMovementFactory {

	@Autowired
	CheckpointFactory checkpointFactory;
	
	public StageMovementDTO convertFromModel(StageMovementModel model) {

		StageMovementDTO dtoToReturn = new StageMovementDTO();
		dtoToReturn.setId(model.getId());
		dtoToReturn.setName(model.getName());
		dtoToReturn.setIcon(model.getIcon());
		dtoToReturn.setJiraID(model.getJiraID());
		dtoToReturn.setType(model.getType());

		return dtoToReturn;

	}

	public List<StageMovementDTO> convertFromListOfModels(List<StageMovementModel> list) {

		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<StageMovementDTO>();

	}

	public List<StageMovementModel> convertFromListOfInsertOrEditDTO(List<InsertOrEditStageMovementDTO> list) {

		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromInsertDto(model)).collect(Collectors.toList())
				: new ArrayList<StageMovementModel>();

	}

	/**
	 * @param dto
	 * @return
	 */
	public StageMovementModel convertFromInsertDto(InsertOrEditStageMovementDTO dto) {

		StageMovementModel modelToReturn = new StageMovementModel();

		modelToReturn.setId(dto.getId());
		modelToReturn.setName(dto.getName());
		modelToReturn.setIcon(dto.getIcon());
		modelToReturn.setJiraID(dto.getJiraID());
		modelToReturn.setType(new ClassifierModel(dto.getType()));

		return modelToReturn;

	}

}
