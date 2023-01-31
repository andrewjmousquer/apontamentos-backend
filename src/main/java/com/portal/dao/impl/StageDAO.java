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
import com.portal.dao.IStageDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.StageMapper;
import com.portal.model.StageModel;
import com.portal.utils.PortalNumberUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class StageDAO extends BaseDAO implements IStageDAO {

	private static final Logger logger = LoggerFactory.getLogger(StageDAO.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public Optional<StageModel> find(StageModel model) throws AppException {
		Optional<StageModel> objReturn = Optional.empty();
		try {

			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("	 stg.stg_id, ");
			query.append("	 stg.name as stg_name, ");
			query.append("	 stg.task, ");
			query.append("	 stg.special, ");
			query.append("	 stg.payment_by_team, ");
			query.append("	 stg.status_jira_id, ");
			query.append("	 stg.ckp_id, ");
			query.append("	 stg.value, ");
			query.append("	 ckp.name as ckp_name, ");
			query.append("	 ckp.description, ");
			query.append("	 cw.name as cw_name ");
			query.append("FROM " + schemaName + "stage as stg ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as cw ON cw.ckl_id = stg.ckl_id ");
			query.append("WHERE stg.stg_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (!StringUtils.isEmpty(model.getName())) {
				query.append(" AND stg.name LIKE :name ");
				params.addValue("name", this.mapLike(model.getName()));
			}

			if (model.getSpecial() != null && model.getSpecial()) {
				query.append(" AND stg.special = :special ");
				params.addValue("special", PortalNumberUtils.booleanToInt(model.getSpecial()));
			}

			if (model.getTask() != null && model.getTask()) {
				query.append(" AND stg.task = :task ");
				params.addValue("task", PortalNumberUtils.booleanToInt(model.getTask()));
			}

			if (model.getPaymentByTeam() != null && model.getPaymentByTeam()) {
				query.append(" AND stg.payment_by_team = :payment_by_team ");
				params.addValue("payment_by_team", PortalNumberUtils.booleanToInt(model.getPaymentByTeam()));
			}

			if (model.getStatusJiraID() != null && model.getStatusJiraID() > 0) {
				query.append(" AND stg.status_jira_id = :jiraID ");
				params.addValue("jiraID", model.getStatusJiraID());
			}

			if (model.getCheckpoint() != null && model.getCheckpoint().getId() > 0) {
				query.append(" AND stg.ckp_id = :checkpointID ");
				params.addValue("checkpointID", model.getCheckpoint().getId());
			}

			if (model.getChecklist() != null && model.getChecklist().getId() > 0) {
				query.append(" AND stg.ckl_id = :checklistID ");
				params.addValue("checklistID", model.getChecklist().getId());
			}

			List<StageModel> list = this.getJdbcTemplatePortal().query(query.toString(), params, new StageMapper());
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
	public Optional<StageModel> getById(Integer id) throws AppException {
		Optional<StageModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("	 stg.stg_id, ");
			query.append("	 stg.name as stg_name, ");
			query.append("	 stg.task, ");
			query.append("	 stg.special, ");
			query.append("	 stg.payment_by_team, ");
			query.append("	 stg.status_jira_id, ");
			query.append("	 stg.ckp_id, ");
			query.append("	 stg.ckl_id, ");
			query.append("	 stg.value, ");
			query.append("	 ckp.ckp_id, ");
			query.append("	 ckp.name as ckp_name, ");
			query.append("	 ckp.description, ");
			query.append("	 cw.ckl_id, ");
			query.append("	 cw.name as cw_name ");
			query.append("FROM " + schemaName + "stage as stg ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as cw ON cw.ckl_id = stg.ckl_id ");
			query.append("WHERE stg.stg_id = :ID ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", id);

			List<StageModel> list = this.getJdbcTemplatePortal().query(query.toString(), params, new StageMapper());
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
	public List<StageModel> list() throws AppException {
		List<StageModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("	 stg.stg_id, ");
			query.append("	 stg.name as stg_name, ");
			query.append("	 stg.task, ");
			query.append("	 stg.special, ");
			query.append("	 stg.payment_by_team, ");
			query.append("	 stg.status_jira_id, ");
			query.append("	 stg.ckp_id, ");
			query.append("	 stg.value, ");
			query.append("	 ckp.ckp_id, ");
			query.append("	 ckp.name as ckp_name, ");
			query.append("	 ckp.description, ");
			query.append("	 cw.ckl_id, ");
			query.append("	 cw.name as cw_name ");
			query.append("FROM " + schemaName + "stage as stg ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as cw ON cw.ckl_id = stg.ckl_id ");
			query.append("WHERE stg.stg_id > 0 ");

			List<StageModel> list = this.getJdbcTemplatePortal().query(query.toString(), new StageMapper());
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
	public List<StageModel> search(StageModel model) throws AppException {
		List<StageModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("	 stg.stg_id, ");
			query.append("	 stg.name as stg_name, ");
			query.append("	 stg.task, ");
			query.append("	 stg.special, ");
			query.append("	 stg.payment_by_team, ");
			query.append("	 stg.status_jira_id, ");
			query.append("	 stg.ckp_id, ");
			query.append("	 stg.value, ");
			query.append("	 ckp.ckp_id, ");
			query.append("	 ckp.name as ckp_name, ");
			query.append("	 ckp.description, ");
			query.append("	 cw.ckl_id, ");
			query.append("	 cw.name as cw_name ");
			query.append("FROM " + schemaName + "stage as stg ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as cw ON cw.ckl_id = stg.ckl_id ");
			query.append("WHERE stg.stg_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (!StringUtils.isEmpty(model.getName())) {
				query.append(" AND stg.name LIKE :name ");
				params.addValue("name", this.mapLike(model.getName()));
			}

			if (model.getSpecial() != null && model.getSpecial()) {
				query.append(" AND stg.special = :special ");
				params.addValue("special", PortalNumberUtils.booleanToInt(model.getSpecial()));
			}

			if (model.getTask() != null && model.getTask()) {
				query.append(" AND stg.task = :task ");
				params.addValue("task", PortalNumberUtils.booleanToInt(model.getTask()));
			}

			if (model.getPaymentByTeam() != null && model.getPaymentByTeam()) {
				query.append(" AND stg.payment_by_team = :payment_by_team ");
				params.addValue("payment_by_team", PortalNumberUtils.booleanToInt(model.getPaymentByTeam()));
			}

			if (model.getStatusJiraID() != null && model.getStatusJiraID() > 0) {
				query.append(" AND stg.status_jira_id = :jiraID ");
				params.addValue("jiraID", model.getStatusJiraID());
			}

			if (model.getCheckpoint() != null && model.getCheckpoint().getId() > 0) {
				query.append(" AND stg.ckp_id = :checkpointID ");
				params.addValue("checkpointID", model.getCheckpoint().getId());
			}

			if (model.getChecklist() != null && model.getChecklist().getId() > 0) {
				query.append(" AND stg.ckl_id = :checklistID ");
				params.addValue("checklistID", model.getChecklist().getId());
			}

			List<StageModel> users = this.getJdbcTemplatePortal().query(query.toString(), params, new StageMapper());
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
	public Optional<StageModel> save(StageModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder("");
			query.append("INSERT INTO stage (  ");
			query.append("	name, ");
			query.append("	task, ");
			query.append("	special, ");
			query.append("	payment_by_team, ");
			query.append("	status_jira_id, ");
			query.append("	ckp_id, ");
			query.append("	value, ");
			query.append("	ckl_id ");
			query.append(") VALUES (");
			query.append("	:name, ");
			query.append("	:task, ");
			query.append("	:special, ");
			query.append("	:payment_by_team, ");
			query.append("	:jiraID, ");
			query.append("	:checkID, ");
			query.append("	:value, ");
			query.append("	:checklistID ");
			query.append(")");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("name", model.getName());
			params.addValue("task", PortalNumberUtils.booleanToInt(model.getTask()));
			params.addValue("special", PortalNumberUtils.booleanToInt(model.getSpecial()));
			params.addValue("payment_by_team", PortalNumberUtils.booleanToInt(model.getPaymentByTeam()));
			params.addValue("jiraID", model.getStatusJiraID());
			params.addValue("value", model.getValue());
			params.addValue("checkID",
					model.getCheckpoint() != null && model.getCheckpoint().getId() > 0 ? model.getCheckpoint().getId()
							: null);
			params.addValue("checklistID",
					model.getChecklist() != null && model.getChecklist().getId() > 0 ? model.getChecklist().getId()
							: null);

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
	public Optional<StageModel> update(StageModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("UPDATE stage SET ");
			query.append("	name = :name, ");
			query.append("	task = :task, ");
			query.append("	special = :special, ");
			query.append("	payment_by_team = :payment_by_team, ");
			query.append("	status_jira_id = :jiraID, ");
			query.append("	ckp_id = :checkID, ");
			query.append("	value = :value, ");
			query.append("	ckl_id = :checklistID ");
			query.append("WHERE ");
			query.append("	stg_id = :ID ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", model.getId());
			params.addValue("name", model.getName());
			params.addValue("task", PortalNumberUtils.booleanToInt(model.getTask()));
			params.addValue("special", PortalNumberUtils.booleanToInt(model.getSpecial()));
			params.addValue("payment_by_team", PortalNumberUtils.booleanToInt(model.getPaymentByTeam()));
			params.addValue("jiraID", model.getStatusJiraID());
			params.addValue("value", model.getValue());
			params.addValue("checkID",
					model.getCheckpoint() != null && model.getCheckpoint().getId() > 0 ? model.getCheckpoint().getId()
							: null);
			params.addValue("checklistID",
					model.getChecklist() != null && model.getChecklist().getId() > 0 ? model.getChecklist().getId()
							: null);

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
			query.append("DELETE FROM stage WHERE stg_id = :id");

			MapSqlParameterSource params = new MapSqlParameterSource("id", id);
			this.getJdbcTemplatePortal().update(query.toString(), params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<StageModel> findByStage(Integer id) throws AppException {
		List<StageModel> objReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("s.stg_id ");
			query.append(", s.name as stg_name");
			query.append(", s.task ");
			query.append(", s.special ");
			query.append(", s.payment_by_team ");
			query.append(", s.value ");
			query.append(", s.status_jira_id ");
			query.append(", s.ckl_id ");
			query.append(", ckp.ckp_id ");
			query.append(", ck.name as cw_name ");
			query.append("FROM " + schemaName + "stage as s ");
			query.append("LEFT JOIN checkpoint as ckp ON ckp.ckp_id = s.ckp_id ");
			query.append("LEFT JOIN check_list as ck ON ck.ckl_id = s.ckl_id ");
			query.append("LEFT JOIN stage_team as st ON s.stg_id = st.stg_id ");
			query.append("WHERE st.tam_id = :id");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] user.findByStage: {} [PARAMS] : {}", query, params.getValues());

			List<StageModel> teams = this.getJdbcTemplatePortal().query(query.toString(), params, new StageMapper());
			if (!CollectionUtils.isEmpty(teams)) {
				objReturn = teams;
			}

			return objReturn;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic.getbyid", null, LocaleContextHolder.getLocale()));
		}
	}

}
