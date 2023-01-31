package com.portal.dao.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.portal.config.BaseDAO;
import com.portal.dao.IStageMovementDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.StageMovementMapper;
import com.portal.model.StageMovementModel;

@Repository
public class StageMovementDAO extends BaseDAO implements IStageMovementDAO {

	private static final Logger logger = LoggerFactory.getLogger(StageMovementDAO.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public Optional<StageMovementModel> find(StageMovementModel model) throws AppException {
		Optional<StageMovementModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append(" 		mst.sps_id, ");
			query.append(" 		mst.jira_id, ");
			query.append(" 		mst.stg_id, ");
			query.append(" 		mst.name, ");
			query.append(" 		mst.icon, ");
			query.append(" 		ct.cla_id as cla_id, ");
			query.append(" 		ct.value as cla_value, ");
			query.append(" 		ct.type as cla_type ");
			query.append("FROM movement_stage as mst ");
			query.append("INNER JOIN  classifier as ct on ct.cla_id = mst.type_id ");
			query.append("WHERE mst.sps_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (!StringUtils.isEmpty(model.getName())) {
				query.append(" AND mst.name LIKE :name ");
				params.addValue("name", this.mapLike(model.getName()));
			}

			if (model.getType() != null) {
				query.append(" AND mst.type_id = :type ");
				params.addValue("type", model.getType().getId());
			}

			if (model.getJiraID() > 0) {
				query.append(" AND mst.jira_id = :jiraID ");
				params.addValue("jiraID", model.getJiraID());
			}

			List<StageMovementModel> list = this.getJdbcTemplatePortal().query(query.toString(), params,
					new StageMovementMapper());
			if (!CollectionUtils.isEmpty(list)) {
				objReturn = Optional.ofNullable(list.get(0));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return objReturn;
	}

	@Override
	public Optional<StageMovementModel> getById(Integer id) throws AppException {
		Optional<StageMovementModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append(" 		mst.sps_id, ");
			query.append(" 		mst.jira_id, ");
			query.append(" 		mst.stg_id, ");
			query.append(" 		mst.name, ");
			query.append(" 		mst.icon, ");
			query.append(" 		ct.cla_id as cla_id, ");
			query.append(" 		ct.value as cla_value, ");
			query.append(" 		ct.type as cla_type ");
			query.append("FROM movement_stage as mst ");
			query.append("INNER JOIN  classifier as ct on ct.cla_id = mst.type_id ");
			query.append("WHERE mst.sps_id = :ID ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", id);

			List<StageMovementModel> list = this.getJdbcTemplatePortal().query(query.toString(), params,
					new StageMovementMapper());
			if (!CollectionUtils.isEmpty(list)) {
				objReturn = Optional.ofNullable(list.get(0));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return objReturn;
	}

	@Override
	public List<StageMovementModel> list() throws AppException {
		List<StageMovementModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append(" 		mst.sps_id, ");
			query.append(" 		mst.jira_id, ");
			query.append(" 		mst.stg_id, ");
			query.append(" 		mst.name, ");
			query.append(" 		mst.icon, ");
			query.append(" 		ct.cla_id as cla_id, ");
			query.append(" 		ct.value as cla_value, ");
			query.append(" 		ct.type as cla_type ");
			query.append("FROM movement_stage as mst ");
			query.append("INNER JOIN  classifier as ct on ct.cla_id = mst.type_id ");
			query.append("WHERE mst.sps_id > 0 ");

			List<StageMovementModel> list = this.getJdbcTemplatePortal().query(query.toString(),
					new StageMovementMapper());
			if (!CollectionUtils.isEmpty(list)) {
				listReturn = list;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return listReturn;
	}

	@Override
	public List<StageMovementModel> search(StageMovementModel model) throws AppException {
		List<StageMovementModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append(" 		mst.sps_id, ");
			query.append(" 		mst.jira_id, ");
			query.append(" 		mst.stg_id, ");
			query.append(" 		mst.name, ");
			query.append(" 		mst.icon, ");
			query.append(" 		ct.cla_id as cla_id, ");
			query.append(" 		ct.value as cla_value, ");
			query.append(" 		ct.type as cla_type ");
			query.append("FROM movement_stage as mst ");
			query.append("INNER JOIN  classifier as ct on ct.cla_id = mst.type_id ");
			query.append("WHERE mst.sps_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (!StringUtils.isEmpty(model.getName())) {
				query.append(" AND mst.name LIKE :name ");
				params.addValue("name", this.mapLike(model.getName()));
			}

			if (model.getType() != null) {
				query.append(" AND mst.type_id = :type ");
				params.addValue("type", model.getType().getId());
			}

			if (model.getJiraID() != null && model.getJiraID() > 0) {
				query.append(" AND mst.jira_id = :jiraID ");
				params.addValue("jiraID", model.getJiraID());
			}

			if (model.getStage() != null && model.getStage().getId() > 0) {
				query.append(" AND mst.stg_id = :stage ");
				params.addValue("stage", model.getStage().getId());
			}

			List<StageMovementModel> users = this.getJdbcTemplatePortal().query(query.toString(), params,
					new StageMovementMapper());
			if (!CollectionUtils.isEmpty(users)) {
				listReturn = users;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return listReturn;
	}

	@Override
	public Optional<StageMovementModel> save(StageMovementModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder("");
			query.append("INSERT INTO movement_stage (  ");
			query.append("	jira_id, ");
			query.append("	stg_id, ");
			query.append("	type_id, ");
			query.append("	name, ");
			query.append("	icon ");
			query.append(") VALUES (");
			query.append("	:jiraID, ");
			query.append("	:stageID, ");
			query.append("	:typeID, ");
			query.append("	:name, ");
			query.append("	:icon ");
			query.append(")");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("jiraID", model.getJiraID());
			params.addValue("stageID", model.getStage().getId());
			params.addValue("typeID", model.getType().getId());
			params.addValue("name", model.getName());
			params.addValue("icon", model.getIcon());

			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.getJdbcTemplatePortal().update(query.toString(), params, keyHolder);
			model.setId(this.getKey(keyHolder));

			return Optional.ofNullable(model);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<StageMovementModel> update(StageMovementModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("UPDATE movement_stage SET ");
			query.append("	jira_id = :jiraID, ");
			query.append("	stg_id = :stageID, ");
			query.append("	type_id = :typeID, ");
			query.append("	name = :name, ");
			query.append("	icon = :icon ");
			query.append("WHERE ");
			query.append("	sps_id = :ID ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", model.getId());
			params.addValue("jiraID", model.getJiraID());
			params.addValue("stageID", model.getStage().getId());
			params.addValue("typeID", model.getType().getId());
			params.addValue("name", model.getName());
			params.addValue("icon", model.getIcon());

			this.getJdbcTemplatePortal().update(query.toString(), params);

			return Optional.ofNullable(model);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void delete(Integer id) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM movement_stage WHERE sps_id = :id");

			MapSqlParameterSource params = new MapSqlParameterSource("id", id);
			this.getJdbcTemplatePortal().update(query.toString(), params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

}
