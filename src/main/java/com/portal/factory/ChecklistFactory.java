package com.portal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.ChecklistDTO;
import com.portal.dto.ChecklistGroupDTO;
import com.portal.dto.request.InsertOrEditChecklistDTO;
import com.portal.dto.request.SearchChecklistDTO;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistModel;

@Component
public class ChecklistFactory {

	@Autowired
	private ChecklistQuestionFactory questionFactory;

	public ChecklistDTO convertFromModel(ChecklistModel model) {

		ChecklistDTO dtoToReturn = new ChecklistDTO();

		dtoToReturn.setId(model.getId());
		dtoToReturn.setName(model.getName());
		dtoToReturn.setDescrition(model.getDescrition());
		dtoToReturn.setPriorityOrder(model.getPriorityOrder());
		dtoToReturn.setTag(model.getTag());
		dtoToReturn.setNumberOfGroups(model.getNumberOfGroups());
		dtoToReturn.setNumberOfQuestions(model.getNumberOfQuestions());

		dtoToReturn.setGroups(model.getGroups() != null && model.getGroups().size() > 0 ? model.getGroups().stream()
				.map(dt -> new ChecklistGroupDTO(dt.getCkgId(), dt.getName(),
						questionFactory.convertFromListOfModels(dt.getQuestions())))
				.collect(Collectors.toList()) : new ArrayList<ChecklistGroupDTO>());

		return dtoToReturn;
	}

	public List<ChecklistDTO> convertFromListOfModels(List<ChecklistModel> list) {
		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<ChecklistDTO>();
	}

	/**
	 * @param dto
	 * @return
	 */
	public ChecklistModel convertFromInsertDto(InsertOrEditChecklistDTO dto) {

		ChecklistModel modelToReturn = new ChecklistModel();

		modelToReturn.setId(dto.getId() != null ? dto.getId() : 0);
		modelToReturn.setName(dto.getName());
		modelToReturn.setDescrition(dto.getDescrition());
		modelToReturn.setPriorityOrder(dto.getPriorityOrder());
		modelToReturn.setTag(dto.getTag());

		modelToReturn.setGroups(dto.getGroups() != null && dto.getGroups().size() > 0 ? dto.getGroups().stream()
				.map(id -> new ChecklistGroupModel(id.getId(), id.getName(),

						questionFactory.convertFromListOfInsertOrEditDTO(id.getQuestions())))
				.collect(Collectors.toList()) : new ArrayList<ChecklistGroupModel>());

		return modelToReturn;

	}

	/**
	 * @param dto
	 * @return
	 */
	public ChecklistModel convertFromFilterDto(SearchChecklistDTO dto) {

		ChecklistModel modelToReturn = new ChecklistModel();

		modelToReturn.setId(dto.getId());
		modelToReturn.setName(dto.getName());
		modelToReturn.setDescrition(dto.getDescrition());
		modelToReturn.setPriorityOrder(dto.getPriorityOrder());

		return modelToReturn;

	}

}
