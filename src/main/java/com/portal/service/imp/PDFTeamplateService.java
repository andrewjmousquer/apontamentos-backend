package com.portal.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.portal.dto.ChecklistResponseDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ParameterModel;
import com.portal.model.TaskModel;
import com.portal.service.IPDFTeamplateService;
import com.portal.service.IParameterService;

@Service
public class PDFTeamplateService implements IPDFTeamplateService {

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private IParameterService parameterService;

	@Override
	public String formatCheckListPDF(TaskModel task, ChecklistResponseDTO checklistResponseDTO)
			throws AppException, BusException {

		Optional<ParameterModel> logo = this.parameterService.find(new ParameterModel("CARBON_LOGO"));

		Context context = new Context();

		context.setVariable("checklistName", checklistResponseDTO.getChecklist().getName());
		context.setVariable("checklistDate", checklistResponseDTO.getAnswers().get(0).getCreationDate());
		context.setVariable("checklistAnsweredName",
				checklistResponseDTO.getAnswers().get(0).getResponsibleForAnswer());
		context.setVariable("logo", logo.get().getValue());
		context.setVariable("entryDate", task.getServiceOrder().getDateStart());
		context.setVariable("osNumber", task.getServiceOrder().getNumber());
		context.setVariable("plate", task.getServiceOrder().getPlate());
		context.setVariable("chassi", task.getServiceOrder().getChassi());
		context.setVariable("model", task.getServiceOrder().getModel());
		context.setVariable("brand", task.getServiceOrder().getBrand());
		context.setVariable("checklistResponseDTO", checklistResponseDTO);

		return templateEngine.process("relatorio-pdf-checklist", context);

	}

}
