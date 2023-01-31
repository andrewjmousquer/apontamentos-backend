package com.portal.dao;

import com.portal.exceptions.AppException;
import com.portal.model.TeamModel;
import com.portal.model.UserModel;

public interface IUserTeamDAO {

	public void save(TeamModel team, UserModel user) throws AppException;

	public void delete(Integer team) throws AppException;

	public void delete(Integer tamId, Integer userId) throws AppException;
}
