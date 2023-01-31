package com.portal.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ParameterModel;
import com.portal.service.IMailTeamplateService;
import com.portal.service.IParameterService;

@Service
public class MailTeamplateService implements IMailTeamplateService {

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private IParameterService parameterService;

	@Override
	public String formatMailNewUser(String nomeUsuario) throws AppException, BusException {

		Optional<ParameterModel> acessListParameter = this.parameterService.find(new ParameterModel("MAIL_LOGO"));

		Context context = new Context();

		context.setVariable("nomeUsuario", nomeUsuario);
		context.setVariable("logo", acessListParameter.get().getValue());

		return templateEngine.process("relatorio-pdf-checklist", context);
	}

	@Override
	public String formatMailForgotPassword(String nomeUsuario, String forgotKey) throws AppException, BusException {

		Optional<ParameterModel> mailLogoParameter = this.parameterService.find(new ParameterModel("MAIL_LOGO"));
		Optional<ParameterModel> systemURLParameter = this.parameterService.find(new ParameterModel("SYSTEM_URL"));

		Context context = new Context();
		context.setVariable("nomeUsuario", nomeUsuario);
		context.setVariable("link", systemURLParameter.get().getValue() + "reset-password/" + forgotKey);
		context.setVariable("logo", mailLogoParameter.get().getValue());
		return templateEngine.process("email-esqueci-minha-senha", context);
	}

	@Override
	public String formatMailChecklistFinished(String numberOS, String checklistName, String stageName)
			throws AppException, BusException {

		Optional<ParameterModel> mailLogoParameter = this.parameterService.find(new ParameterModel("MAIL_LOGO"));

		Context context = new Context();
		context.setVariable("checklistName", checklistName);
		context.setVariable("stageName", stageName);
		context.setVariable("numberOS", numberOS);
		context.setVariable("logo", mailLogoParameter.get().getValue());
		return templateEngine.process("email-relatorio-checklist", context);
	}

}
