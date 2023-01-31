package com.portal.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.ChecklistAnswerPhotoDTO;
import com.portal.enums.FileType;
import com.portal.model.ChecklistAnswerModel;
import com.portal.model.ChecklistAnswerPhotoModel;
import com.portal.service.IFilesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChecklistAnswerPhotoFactory {

	@Autowired
	ClassifierFactory classifierFactory;

	@Autowired
	ChecklistQuestionFactory checklistQuestionFactory;

	@Autowired
	private IFilesService filesService;

	public ChecklistAnswerPhotoDTO convertFromModel(ChecklistAnswerPhotoModel model) {

		ChecklistAnswerPhotoDTO dtoToReturn = new ChecklistAnswerPhotoDTO();

		dtoToReturn.setId(model.getId());
		try {
			dtoToReturn
					.setUrl(this.filesService.getVirtualPath(FileType.CHECKLIST_PHOTO, model.getFileName()).toString());
		} catch (Exception e) {
			log.error("Erro ao tentar gerar a URL do arquivo de ID: {}", model.getId(), e);
		}

		dtoToReturn.setCreationDate(model.getCreationDate());

		return dtoToReturn;

	}

	public List<ChecklistAnswerPhotoDTO> convertFromListOfModels(List<ChecklistAnswerPhotoModel> list) {

		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<ChecklistAnswerPhotoDTO>();

	}

	public ChecklistAnswerPhotoModel convertFromInsertDto(String fileName, ChecklistAnswerModel model) {

		ChecklistAnswerPhotoModel modelToReturn = new ChecklistAnswerPhotoModel();

		modelToReturn.setFileName(fileName);
		modelToReturn.setCreationDate(new Date());
		modelToReturn.setAnswer(model);

		return modelToReturn;

	}

}
