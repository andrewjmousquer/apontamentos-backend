package com.portal.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.portal.config.BaseDAO;
import com.portal.dao.ITeamDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.TeamListMapper;
import com.portal.mapper.TeamMapper;
import com.portal.model.TeamModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TeamDAO extends BaseDAO implements ITeamDAO {

	@Override
	public Optional<TeamModel> save(TeamModel model) throws AppException {
		try {

			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO ");
			query.append("team ");
			query.append("(name, abbreviation) ");
			query.append("VALUES ");
			query.append("(:name, :abbreviation) ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("name", model.getName());
			params.addValue("abbreviation", model.getAbbreviation());

			log.trace("[QUERY] team.save: {} [PARAMS]: {}", query, params.getValues());

			KeyHolder keyHolder = new GeneratedKeyHolder();

			this.getNamedParameterJdbcTemplate().update(query.toString(), params, keyHolder);

			model.setId(this.getKey(keyHolder));

			return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar salvar a equipe: {}", model, e);
			throw new AppException("Erro ao tentar salvar equipe.", e);
		}
	}

	@Override
	public Optional<TeamModel> update(TeamModel model) throws AppException {

		try {
			StringBuilder query = new StringBuilder();
			query.append("UPDATE team ");
			query.append("SET name=:name, abbreviation=:abbreviation ");
			query.append("WHERE tam_id = :id ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", model.getId());
			params.addValue("name", model.getName());
			params.addValue("abbreviation", model.getAbbreviation());

			log.trace("[QUERY] team.update: {} [PARAMS]: {}", query, params.getValues());

			this.getJdbcTemplatePortal().update(query.toString(), params);
			return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar atualizar a equipe : {}", model, e);
			throw new AppException("Erro ao tentar atualizar a equipe.", e);
		}
	}

	@Override
	public void delete(Integer id) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("DELETE ");
			query.append("FROM team ");
			query.append("WHERE tam_id = :id ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] team.delete : {} [PARAMS] : {}", query, params.getValues());
			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir.", e);
			throw new AppException("Erro ao excluir.", e);
		}
	}

	@Override
	public Optional<TeamModel> getById(Integer id) throws AppException {
		Optional<TeamModel> objReturn = Optional.empty();

		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("	 tam.tam_id ");
			query.append("	,tam.name ");
			query.append("	,tam.abbreviation ");
			query.append("FROM team as tam ");
			query.append("WHERE tam.tam_id = :id ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] team.getById: {} [PARAMS] : {}", query, params.getValues());

			List<TeamModel> teams = this.getJdbcTemplatePortal().query(query.toString(), params, new TeamMapper());
			if (!CollectionUtils.isEmpty(teams)) {
				objReturn = Optional.ofNullable(teams.get(0));
			}

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (Exception e) {
			log.trace("Erro ao consultar uma equipe.", e);
			throw new AppException("Erro ao consultar uma equipe.", e);
		}

		return objReturn;

	}

	@Override
	public List<TeamModel> list() throws AppException {
		try {

			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("	tam_id ");
			query.append("	,name ");
			query.append("	,abbreviation ");
			query.append("	,(SELECT COUNT(1) FROM user_team AS tu ");
			query.append("WHERE tu.tam_id = team.tam_id) AS numberOfUsers ");
			query.append("	,(SELECT COUNT(1) FROM stage_team AS st ");
			query.append("WHERE st.tam_id = team.tam_id)  AS numberOfstages ");
			query.append("FROM team ");
			query.append("ORDER BY tam_id ");

			log.trace("[QUERY] team.list: {} [PARAMS]: {}", query);
			return this.getJdbcTemplate().query(query.toString(), new TeamListMapper());

		} catch (Exception e) {
			log.error("Erro ao tentar listar equipes", e);
			throw new AppException("Erro ao listar equipes. ", e);
		}
	}

	@Override
	public List<TeamModel> search(TeamModel model) throws AppException {
		List<TeamModel> objReturn = null;

		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tam_id, ");
			query.append("name, ");
			query.append("abbreviation ");
			query.append("	,(SELECT COUNT(1) FROM user_team AS tu ");
			query.append("WHERE tu.tam_id = team.tam_id) AS numberOfUsers ");
			query.append("	,(SELECT COUNT(1) FROM stage_team AS st ");
			query.append("WHERE st.tam_id = team.tam_id)  AS numberOfstages ");
			query.append("FROM " + schemaName + "team ");
			query.append("WHERE tam_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND tam_id=:id ");
					params.addValue("id", model.getId());
				}
				if (model.getName() != null && !model.getName().equals("")) {
					query.append("AND name LIKE:name ");
					params.addValue("name", model.getName() + "%");
				}
				if (model.getAbbreviation() != null && !model.getAbbreviation().equals("")) {
					query.append("AND abbreviation=:abbreviation");
					params.addValue("abbreviation", model.getAbbreviation() + "%");
				}
			}

			log.trace("[QUERY] tam.search: {} [PARAMS]: {}", query, params.getValues());

			List<TeamModel> teams = this.getJdbcTemplatePortal().query(query.toString(), params, new TeamListMapper());
			if (!CollectionUtils.isEmpty(teams)) {
				objReturn = teams;
			}

		} catch (Exception e) {
			log.error("Erro ao procurar equipe", e);
			throw new AppException("Erro ao procurar equipe.", e);
		}

		return objReturn;
	}

	@Override
	public Optional<TeamModel> find(TeamModel model) throws AppException {
		Optional<TeamModel> objReturn = Optional.empty();

		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tam_id, ");
			query.append("name, ");
			query.append("abbreviation ");
			query.append("FROM " + schemaName + "team tam ");
			query.append("WHERE tam.tam_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND tam_id=:id ");
					params.addValue("id", model.getId());
				}
				if (model.getName() != null && !model.getName().equals("")) {
					query.append("AND name LIKE:name ");
					params.addValue("name", model.getName() + "%");
				}
				if (model.getAbbreviation() != null && !model.getAbbreviation().equals("")) {
					query.append("AND abbreviation=:abbreviation");
					params.addValue("abbreviation", model.getAbbreviation() + "%");
				}
			}

			log.trace("[QUERY] team.find {} [PARAMS] : {}", query, params.getValues());

			List<TeamModel> teams = this.getJdbcTemplatePortal().query(query.toString(), params, new TeamMapper());
			if (!CollectionUtils.isEmpty(teams)) {
				objReturn = Optional.ofNullable(teams.get(0));
			}

		} catch (Exception e) {
			log.error("Erro ao buscar equipe.", e);
			throw new AppException("Erro ao buscar equipe.", e);
		}

		return objReturn;

	}

	@Override
	public List<TeamModel> getAllTeamByUser(Integer id) throws AppException {
		List<TeamModel> objReturn = new ArrayList<>();

		try {

			StringBuilder query = new StringBuilder();
			query.append("SELECT * ");
			query.append("FROM team as t ");
			query.append("INNER JOIN user_team as ut ");
			query.append("ON ut.tam_id = t.tam_id ");
			query.append("WHERE ut.usr_id=:id");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] getAllTeamByUser.search: {} [PARAMS]: {}", query, params.getValues());

			List<TeamModel> teamsByUser = this.getJdbcTemplatePortal().query(query.toString(), params,
					new TeamMapper());
			if (!CollectionUtils.isEmpty(teamsByUser)) {
				objReturn = teamsByUser;
			}

		} catch (Exception e) {
			log.error("Erro ao procurar equipe do usuario informado.", e);
			throw new AppException("Erro ao procurar equipe do usuario informado.", e);
		}

		return objReturn;
	}

}
