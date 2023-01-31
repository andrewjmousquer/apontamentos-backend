package com.portal.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.ChecklistAnswerDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.dto.request.InsertOrEditChecklistAnswerDTO;
import com.portal.dto.request.SearchChecklistAnswerDTO;
import com.portal.enums.ChecklistAnswerType;
import com.portal.model.ChecklistAnswerModel;
import com.portal.model.ChecklistQuestionModel;
import com.portal.model.TaskModel;

@Component
public class ChecklistAnswerFactory {

	@Autowired
	ClassifierFactory classifierFactory;

	@Autowired
	ChecklistQuestionFactory checklistQuestionFactory;

	@Autowired
	ChecklistAnswerPhotoFactory answerPhotoFactory;

	public ChecklistAnswerDTO convertFromModel(ChecklistAnswerModel model) {

		ChecklistAnswerDTO dtoToReturn = new ChecklistAnswerDTO();

		dtoToReturn.setId(model.getId());
		dtoToReturn.setComment(model.getComment());
		dtoToReturn.setCreationDate(model.getCreationDate());
		dtoToReturn.setQuestion(checklistQuestionFactory.convertFromModel(model.getQuestion()));
		dtoToReturn.setResponsibleForAnswer(model.getResponsibleForAnswer());

		if (model.getTask() != null && model.getTask().getId() != null)
			dtoToReturn.setTask(model.getTask().getId());
		if (model.getAnswer() != null)
			dtoToReturn.setAnswer(classifierFactory.convertFromModel(model.getAnswer()));
		if (model.getPhotos() != null)
			dtoToReturn.setPhotos(answerPhotoFactory.convertFromListOfModels(model.getPhotos()));

		return dtoToReturn;

	}

	public List<ChecklistAnswerDTO> convertFromListOfModels(List<ChecklistAnswerModel> list) {

		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<ChecklistAnswerDTO>();

	}

	public ChecklistAnswerModel convertFromInsertDto(InsertOrEditChecklistAnswerDTO dto, UserProfileDTO userLoggedIn) {

		ChecklistAnswerModel modelToReturn = new ChecklistAnswerModel();

		modelToReturn.setId(dto.getId() != null ? dto.getId() : 0);
		modelToReturn.setComment(dto.getComment());
		modelToReturn.setResponsibleForAnswer(userLoggedIn.getUser().getUsername());
		modelToReturn.setQuestion(new ChecklistQuestionModel(dto.getQuestion()));
		modelToReturn.setTask(new TaskModel(dto.getTask()));
		modelToReturn.setAnswer(ChecklistAnswerType.getById(dto.getAnswer()).getType());

		if (dto.getId().equals(0))
			modelToReturn.setCreationDate(new Date());

		return modelToReturn;

	}

	public ChecklistAnswerModel convertFromFilterDto(SearchChecklistAnswerDTO dto) {

		ChecklistAnswerModel modelToReturn = new ChecklistAnswerModel();

		modelToReturn.setQuestion(new ChecklistQuestionModel(dto.getQuestion()));
		modelToReturn.setId(dto.getId() != null ? dto.getId() : 0);

		return modelToReturn;

	}

}
