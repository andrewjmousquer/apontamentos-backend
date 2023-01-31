package com.portal.factory;

import org.springframework.stereotype.Component;

import com.portal.dto.CheckpointDTO;
import com.portal.model.CheckpointModel;

@Component
public class CheckpointFactory {

	public CheckpointDTO convertFromModel(CheckpointModel model) {

		CheckpointDTO dtoToReturn = new CheckpointDTO();

		dtoToReturn.setId(model.getId());
		dtoToReturn.setName(model.getName());
		dtoToReturn.setDescription(model.getDescription());

		return dtoToReturn;

	}

}
