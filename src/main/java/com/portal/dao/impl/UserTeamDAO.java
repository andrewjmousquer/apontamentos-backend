package com.portal.dao.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.portal.config.BaseDAO;
import com.portal.dao.IUserTeamDAO;
import com.portal.exceptions.AppException;
import com.portal.model.TeamModel;
import com.portal.model.UserModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserTeamDAO extends BaseDAO implements IUserTeamDAO {

	@Override
	public void save(TeamModel team, UserModel user) throws AppException {

		StringBuilder query = new StringBuilder("");
		query.append("INSERT INTO " + schemaName + "user_team ");
		query.append("( ");
		query.append("usr_id, ");
		query.append("tam_id ) ");
		query.append("VALUES ( ");
		query.append(":user, ");
		query.append(":team ) ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("user", user.getId());
		params.addValue("team", team.getId());

		log.trace("[QUERY] team_user.save: {} [PARAMS]: {}", query, params.getValues());

		try {
			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar salvar: {}", team, user, e);
			throw new AppException("Erro ao tentar salvar.", e);
		}

	}

	@Override
	public void delete(Integer teamId) throws AppException {

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(schemaName + "user_team ");
		query.append("WHERE tam_id = :tam_id ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("tam_id", teamId);

		log.trace("[QUERY] team_user.delete : {} [PARAMS] : {}", query, params.getValues());
		try {

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir: {}", teamId, e);
			throw new AppException("Erro ao tentar excluir.", e);

		}
	}

	@Override
	public void delete(Integer teamId, Integer userId) throws AppException {

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append("user_team ");
		query.append("WHERE tam_id = :tamid ");
		query.append("AND usr_id = :usrid");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("tamid", teamId);
		params.addValue("usrid", userId);

		log.trace("[QUERY] team_user.delete : {} [PARAMS] : {}", query, params.getValues());

		try {

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir: {}", teamId, e);
			throw new AppException("Erro ao tentar excluir.", e);

		}

	}

}
