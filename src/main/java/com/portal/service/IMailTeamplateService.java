package com.portal.service;

import org.springframework.stereotype.Service;

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;

@Service
public interface IMailTeamplateService {

	public String formatMailNewUser(String nomeUsuario) throws AppException, BusException;

	public String formatMailForgotPassword(String nomeUsuario, String forgotKey) throws AppException, BusException;

	public String formatMailChecklistFinished(String numberOS, String checklistName, String stageName)
			throws AppException, BusException;

}