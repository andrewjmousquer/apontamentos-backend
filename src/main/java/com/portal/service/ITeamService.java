package com.portal.service;

import java.util.List;

import com.portal.exceptions.AppException;
import com.portal.model.TeamModel;

public interface ITeamService extends IBaseService<TeamModel> {

	List<TeamModel> getAllTeamByUser(Integer id) throws AppException;

}
