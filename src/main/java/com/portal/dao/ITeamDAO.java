package com.portal.dao;

import java.util.List;

import com.portal.exceptions.AppException;
import com.portal.model.TeamModel;

public interface ITeamDAO extends IBaseDAO<TeamModel> {

	List<TeamModel> getAllTeamByUser(Integer id) throws AppException;

}
