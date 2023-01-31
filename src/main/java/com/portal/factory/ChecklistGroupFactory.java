package com.portal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.portal.dto.ChecklistGroupDTO;
import com.portal.dto.ChecklistQuestionDTO;
import com.portal.dto.request.InsertOrEditChecklistGroupDTO;
import com.portal.dto.request.SearchChecklistGroupDTO;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistQuestionModel;

@Component
public class ChecklistGroupFactory {

	public ChecklistGroupDTO convertFromModel(ChecklistGroupModel model) {

		ChecklistGroupDTO dtoToReturn = new ChecklistGroupDTO();
		dtoToReturn.setName(model.getName());
		dtoToReturn.setId(model.getCkgId());
		dtoToReturn.setQuestions(model.getQuestions() != null && model.getQuestions().size() > 0
				? model.getQuestions().stream()
						.map(dt -> new ChecklistQuestionDTO(dt.getId(), dt.getQuestion(), dt.getCreationDate(),
								dt.getActive(), dt.getCklId()))
						.collect(Collectors.toList())
				: new ArrayList<ChecklistQuestionDTO>());

		return dtoToReturn;

	}

	public List<ChecklistGroupDTO> convertFromListOfModels(List<ChecklistGroupModel> list) {
		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<ChecklistGroupDTO>();
	}

	/**
	 * @param dto
	 * @return
	 */
	public ChecklistGroupModel convertFromInsertDto(InsertOrEditChecklistGroupDTO dto) {

		ChecklistGroupModel modelToReturn = new ChecklistGroupModel();

		modelToReturn.setName(dto.getName());
		modelToReturn.setCkgId(dto.getId());

		modelToReturn.setQuestions(dto.getQuestions() != null && dto.getQuestions().size() > 0
				? dto.getQuestions().stream()
						.map(question -> new ChecklistQuestionModel(question.getId(), question.getQuestion(),
								question.getCreationDate(), question.getActive()))
						.collect(Collectors.toList())
				: new ArrayList<ChecklistQuestionModel>());

		return modelToReturn;

	}

	public ChecklistGroupModel convertFromFilterDto(SearchChecklistGroupDTO dto) {

		ChecklistGroupModel modelToReturn = new ChecklistGroupModel();

		modelToReturn.setName(dto.getName());
		modelToReturn.setCkgId(dto.getCkgId());

		modelToReturn.setQuestions(dto.getQuestions() != null && dto.getQuestions().size() > 0
				? dto.getQuestions().stream().map(id -> new ChecklistQuestionModel()).collect(Collectors.toList())
				: new ArrayList<ChecklistQuestionModel>());

		return modelToReturn;

	}

}
