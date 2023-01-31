package com.portal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.portal.dto.ChecklistQuestionDTO;
import com.portal.dto.request.InsertOrEditChecklistQuestionDTO;
import com.portal.dto.request.SearchChecklistQuestionDTO;
import com.portal.model.ChecklistQuestionModel;

@Component
public class ChecklistQuestionFactory {

	public ChecklistQuestionDTO convertFromModel(ChecklistQuestionModel model) {

		ChecklistQuestionDTO dtoToReturn = new ChecklistQuestionDTO();

		dtoToReturn.setId(model.getId());
		dtoToReturn.setQuestion(model.getQuestion());
		dtoToReturn.setCreationDate(model.getCreationDate());
		dtoToReturn.setActive(model.getActive());
		dtoToReturn.setGroupID(model.getCklId());

		return dtoToReturn;
	}

	public List<ChecklistQuestionDTO> convertFromListOfModels(List<ChecklistQuestionModel> list) {
		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<ChecklistQuestionDTO>();
	}

	/**
	 * @param dto
	 * @return
	 */
	public ChecklistQuestionModel convertFromInsertDto(InsertOrEditChecklistQuestionDTO dto) {

		ChecklistQuestionModel modelToReturn = new ChecklistQuestionModel();

		modelToReturn.setId(dto.getId());
		modelToReturn.setQuestion(dto.getQuestion());
		modelToReturn.setCreationDate(dto.getCreationDate());
//TODO Buscar um melhor caminho para validação para SetActive				
		modelToReturn.setActive(dto.getId() == null || dto.getId() == 0 ? Boolean.TRUE : dto.getActive());

		return modelToReturn;

	}

	public List<ChecklistQuestionModel> convertFromListOfInsertOrEditDTO(List<InsertOrEditChecklistQuestionDTO> list) {

		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromInsertDto(model)).collect(Collectors.toList())
				: new ArrayList<ChecklistQuestionModel>();
	}

	public ChecklistQuestionModel convertFromFilterDto(SearchChecklistQuestionDTO dto) {

		ChecklistQuestionModel modelToReturn = new ChecklistQuestionModel();

		modelToReturn.setId(dto.getId());
		modelToReturn.setQuestion(dto.getQuestion());
		modelToReturn.setCreationDate(dto.getCreationDate());
		modelToReturn.setActive(dto.getActive());

		return modelToReturn;

	}

}
