package com.portal.service;

import java.io.IOException;

import org.springframework.context.NoSuchMessageException;

import com.portal.dto.UserProfileDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ServiceOrderModel;
import com.portal.model.StageMovementModel;
import com.portal.model.TaskModel;

public interface IJiraIntegrationService {

	public ServiceOrderModel getServiceOrderByNumberOsOrChassi(ServiceOrderModel serviceOrder, UserProfileDTO requester)
			throws IOException, InterruptedException, NoSuchMessageException, AppException, BusException;

	public void transitJiraStatus(TaskModel model, StageMovementModel movement, UserProfileDTO userRequester)
			throws NoSuchMessageException, AppException;

	public void sendFileJira(String path, String jiraKEY) throws AppException;

	public void transitJiraSpecialStatus(TaskModel model, StageMovementModel movement, UserProfileDTO userRequester,
			String description) throws NoSuchMessageException, AppException;

}
