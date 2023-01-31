package com.portal.service.imp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.mail.internet.InternetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ParameterModel;
import com.portal.service.IMailService;
import com.portal.service.IParameterService;
import com.portal.utils.PortalStaticVariables;

@Service
public class MailService implements IMailService {

	private JavaMailSender mailSender;

	@Autowired
	private IParameterService parameterService;

	@Autowired
	public MailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	private Optional<ParameterModel> getMailUsername() throws AppException, BusException {
		return this.parameterService.find(new ParameterModel(PortalStaticVariables.SPRING_MAIL_USERNAME_PARAMETER));
	}

	@Override
	public boolean sendMail(String recipient, String subject, String message) throws AppException, BusException {

		Optional<ParameterModel> mailusername = getMailUsername();

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(mailusername.get().getValue());
			messageHelper.setTo(recipient);
			messageHelper.setSubject(subject);
			messageHelper.setText(message, true);
		};
		try {
			mailSender.send(messagePreparator);
			return true;
		} catch (MailException e) {
			// runtime exception; just for loggin purposes
			Logger logger = LogManager.getLogger(BusException.class.getName());
			logger.error("Erro ao enviar email: {}. Causa:", e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean sendMailMultipleAddres(String[] recipients, String subject, String message)
			throws AppException, BusException {

		Optional<ParameterModel> mailusername = getMailUsername();

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			InternetAddress[] recipientsList = new InternetAddress[recipients.length];
			if (recipients.length > 0) {
				for (int i = 0; i < recipients.length; i++) {
					recipientsList[i] = new InternetAddress(recipients[i]);
				}
			} else {
				recipientsList = InternetAddress.parse("osmar.fagundes@sbmtechnology.com.br");
			}
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(mailusername.get().getValue());
			messageHelper.setTo(recipientsList);
			messageHelper.setSubject(subject);
			messageHelper.setText(message, true);
		};
		try {
			mailSender.send(messagePreparator);
			return true;
		} catch (MailException e) {
			// runtime exception; just for loggin purposes
			Logger logger = LogManager.getLogger(BusException.class.getName());
			logger.error("Erro ao enviar email: {}. Causa:", e.getMessage(), e);
			return false;
		}
	}

	@Override
	public boolean sendMailMultipleAddresWithFile(String[] recipients, String subject, String message,
			Path fileToAttach) throws IOException, AppException, BusException {

		byte[] content = Files.readAllBytes(fileToAttach);

		Optional<ParameterModel> mailusername = getMailUsername();

		MimeMessagePreparator messagePreparator = mimeMessage -> {
			InternetAddress[] recipientsList = new InternetAddress[recipients.length];
			if (recipients.length > 0) {
				for (int i = 0; i < recipients.length; i++) {
					recipientsList[i] = new InternetAddress(recipients[i]);
				}
			} else {
				recipientsList = InternetAddress.parse("osmar.fagundes@sbmtechnology.com.br");
			}
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setFrom(mailusername.get().getValue());
			messageHelper.setTo(recipientsList);
			messageHelper.setSubject(subject);
			messageHelper.setText(message, true);

			if (content != null) {
				messageHelper.addAttachment("checklist.pdf", new ByteArrayResource(content));
			}

		};
		try {
			mailSender.send(messagePreparator);
			return true;
		} catch (MailException e) {

			Logger logger = LogManager.getLogger(BusException.class.getName());

			logger.error("Erro ao enviar email: {}. Causa:", e.getMessage(), e);
			return false;
		}
	}

}