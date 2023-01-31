package com.portal.dao;

import com.portal.exceptions.AppException;
import com.portal.model.StageModel;
import com.portal.model.TeamModel;

public interface IStageTeamDAO {

	public void save(TeamModel team, StageModel stage) throws AppException;

	public void delete(Integer stgId) throws AppException;

	public void delete(Integer stgId, Integer tamId) throws AppException;
}
