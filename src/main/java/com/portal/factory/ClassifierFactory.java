package com.portal.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.portal.dto.ClassifierDTO;
import com.portal.model.ClassifierModel;

@Component
public class ClassifierFactory {

	public ClassifierDTO convertFromModel(ClassifierModel classifierModel) {

		ClassifierDTO dto = new ClassifierDTO();
		dto.setId(classifierModel.getId());
		dto.setType(classifierModel.getType());
		dto.setValue(classifierModel.getValue());
		dto.setLabel(classifierModel.getLabel());

		return dto;
	}

	public List<ClassifierDTO> convertFromClassifierModelList(List<ClassifierModel> modelList) {

		ArrayList<ClassifierDTO> result = new ArrayList<ClassifierDTO>();
		for (ClassifierModel model : modelList) {

			ClassifierDTO dto = convertFromModel(model);

			result.add(dto);
		}

		return result;
	}
}
