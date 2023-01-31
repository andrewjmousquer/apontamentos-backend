package com.portal.enums;

import com.portal.model.ClassifierModel;

public enum ChecklistAnswerType {

	OK(new ClassifierModel(42, "OK", "CHECKLIST_ANSWER")),
	NOK(new ClassifierModel(43, "N/OK", "CHECKLIST_ANSWER")),
	NA(new ClassifierModel(44, "N/A", "CHECKLIST_ANSWER"));


	private ClassifierModel type;

	ChecklistAnswerType(ClassifierModel type) {
		this.type = type;
	}

	public ClassifierModel getType() {
		return type;
	}

	public static ChecklistAnswerType getById(Integer id) {
		if (id != null) {
			for (ChecklistAnswerType type : ChecklistAnswerType.values()) {
				if (type.getType().getId().equals(id)) {
					return type;
				}
			}
		}

		throw new IllegalArgumentException("DocumentType - Não foi encontrado o ENUM com o ID " + id);
	}
}
