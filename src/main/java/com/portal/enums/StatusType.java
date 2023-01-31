package com.portal.enums;

import com.portal.model.ClassifierModel;

public enum StatusType {

	AGUARDANDO_INICIO(new ClassifierModel(41, "Aguardando inicio", "PROCESS_STATUS")),
	EM_ANDAMENTO(new ClassifierModel(38, "Em andamento", "PROCESS_STATUS")),
	FINALIZADO(new ClassifierModel(39, "Finalizado", "PROCESS_STATUS")),
	PAUSADO(new ClassifierModel(40, "Pausado", "PROCESS_STATUS"));

	private ClassifierModel type;

	StatusType(ClassifierModel type) {
		this.type = type;
	}

	public ClassifierModel getType() {
		return type;
	}

	public static StatusType getById(Integer id) {
		if (id != null) {
			for (StatusType type : StatusType.values()) {
				if (type.getType().getId().equals(id)) {
					return type;
				}
			}
		}

		throw new IllegalArgumentException("StatusOS - Não foi encontrado o ENUM com o ID " + id);
	}
}
