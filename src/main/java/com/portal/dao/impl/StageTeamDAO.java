package com.portal.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.portal.config.BaseDAO;
import com.portal.dao.IStageTeamDAO;
import com.portal.exceptions.AppException;
import com.portal.model.StageModel;
import com.portal.model.TeamModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class StageTeamDAO extends BaseDAO implements IStageTeamDAO {

	@Override
	public void save(TeamModel team, StageModel stage) throws AppException {

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO " + schemaName + "stage_team (");
		query.append("	tam_id");
		query.append("	,stg_id");
		query.append(") VALUES (");
		query.append("	:tam_id");
		query.append("	,:stg_id");
		query.append(")");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("tam_id", team.getId());
		params.addValue("stg_id", stage.getId());
		try {
			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar salvar stage_team: {}", team, stage, e);
			throw new AppException("Erro ao tentar salvar stage_team.", e);
		}

	}

	@Override
	public void delete(Integer tamId) throws AppException {

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(schemaName + "stage_team ");
		query.append("WHERE tam_id = :tam_id ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("tam_id", tamId);

		try {
			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir stage_team: {}", tamId, e);
			throw new AppException("Erro ao tentar excluir stage_team.", e);
		}

	}

	@Override
	public void delete(Integer stgId, Integer tamId) throws AppException {

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM stage_team ");
		query.append("WHERE stg_id = :stg_id ");
		query.append("AND tam_id = :tam_id ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("stg_id", stgId);
		params.addValue("tam_id", tamId);
		try {
			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir stage_team: {}", stgId, e);
			throw new AppException("Erro ao tentar excluir stage_team.", e);
		}

	}

}
