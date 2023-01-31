package com.portal.service;

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.TeamModel;

public interface IUserTeamService {

	public TeamModel save(TeamModel team) throws AppException, BusException;

	public TeamModel delete(TeamModel team) throws AppException, BusException;

}
