package com.portal.enums;

import com.portal.model.ClassifierModel;

public enum StageMovementType {

	START(new ClassifierModel(35, "Iniciar", "MOVEMENT_TYPE")),
	PAUSE(new ClassifierModel(36, "Pausar", "MOVEMENT_TYPE")),
	MOVEMENT(new ClassifierModel(37, "Transitar", "MOVEMENT_TYPE")),
	SPECIAL(new ClassifierModel(46, "Serviço Especial", "MOVEMENT_TYPE")),
	RESUME(new ClassifierModel(0, "Continuar", "MOVEMENT_TYPE"));

	private ClassifierModel type;

	StageMovementType(ClassifierModel type) {
		this.type = type;
	}

	public ClassifierModel getType() {
		return type;
	}

	public static StageMovementType getById(Integer id) {
		if (id != null) {
			for (StageMovementType type : StageMovementType.values()) {
				if (type.getType().getId().equals(id)) {
					return type;
				}
			}
		}

		throw new IllegalArgumentException("StatusOS - Não foi encontrado o ENUM com o ID " + id);
	}
}
