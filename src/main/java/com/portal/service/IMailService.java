package com.portal.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;

@Service
public interface IMailService {

	boolean sendMail(String destinatario, String assunto, String corpoMensagem) throws AppException, BusException;

	boolean sendMailMultipleAddres(String[] destinatarios, String assunto, String corpoMensagem) throws AppException, BusException;

	boolean sendMailMultipleAddresWithFile(String[] recipients, String subject, String message, Path fileToAttach)
			throws IOException, AppException, BusException;

}
