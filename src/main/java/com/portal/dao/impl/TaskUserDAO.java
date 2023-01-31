package com.portal.dao.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.portal.config.BaseDAO;
import com.portal.dao.ITaskUserDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.TaskUserMapper;
import com.portal.model.TaskUserModel;
import com.portal.utils.PortalNumberUtils;

@Repository
public class TaskUserDAO extends BaseDAO implements ITaskUserDAO {

	private static final Logger logger = LoggerFactory.getLogger(TaskUserDAO.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public Optional<TaskUserModel> find(TaskUserModel model) throws AppException {
		Optional<TaskUserModel> objReturn = Optional.empty();
		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tu.tsu_id ");
			query.append(",tu.tsk_id ");
			query.append(",tu.usr_id ");
			query.append(",tu.stage_value ");
			query.append(",tu.recived_value ");

			query.append(",u.username ");
			query.append(",p.per_id as per_id ");
			query.append(",p.job_title as per_job_title ");
			query.append(",p.name ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tu.date_start ");
			query.append(",tu.date_finish ");

			// Task
			query.append(",tsk.tsk_id tsk_tsk_id ");
			query.append(",tsk.name tsk_name ");
			query.append(",tsk.number_jira as tsk_number_jira ");
			query.append(",ctt.cla_id as tsk_cla_id ");
			query.append(",ctt.value as tsk_cla_value ");
			query.append(",ctt.type as tsk_cla_type ");
			query.append(",ctt.label as tsk_cla_label ");
			query.append(",tsk.date_start tsk_date_start ");
			query.append(",tsk.date_finish tsk_date_finish ");
			query.append(",tsk.stg_id tsk_stg_id ");
			query.append(",tsk.special_service_place as tsk_special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start ");
			query.append(",so.date_finish as so_date_finish ");
			query.append(",so.status_cla as so_date_start ");
			query.append(",so.brand as so_brand ");
			query.append(",so.model as so_model ");
			query.append(",so.plate as so_plate ");
			query.append(",so.chassi as so_chassi ");
			query.append(",so.number as so_number ");
			query.append(",ut.cla_id as so_cla_id ");
			query.append(",ut.value as so_cla_value ");
			query.append(",ut.type as so_cla_type ");
			query.append(",ut.label as so_cla_label ");

			query.append(",stg.stg_id as stg_stg_id ");
			query.append(",stg.name as stg_name ");
			query.append(",stg.task as stg_task ");
			query.append(",stg.special as stg_special ");
			query.append(",stg.status_jira_id as stg_status_jira_id ");
			query.append(",stg.ckp_id as stg_ckp_id ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name ");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task_user as tu ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tu.status_cla ");
			query.append("INNER JOIN " + schemaName + "user as u on u.usr_id = tu.usr_id ");
			query.append("INNER JOIN " + schemaName + "person as p on p.per_id = u.per_id ");
			// Task
			query.append("INNER JOIN " + schemaName + "task as tsk on tsk.tsk_id = tu.tsk_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ctt on ctt.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tu.tsu_id > 0");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {

				if (model.getStatus() != null && model.getStatus().getId() > 0) {
					query.append(" AND tu.status_cla = :statusID ");
					params.addValue("statusID", model.getStatus().getId());
				}

				if (model.getTask() != null && model.getTask().getId() > 0) {
					query.append(" AND tu.tsk_id = :taskID ");
					params.addValue("taskID", model.getTask().getId());
				}

				if (model.getUser() != null && model.getUser().getId() > 0) {
					query.append(" AND tu.usr_id = :userID ");
					params.addValue("userID", model.getUser().getId());
				}
			}

			List<TaskUserModel> list = this.getJdbcTemplatePortal().query(query.toString(), params,
					new TaskUserMapper());
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
	public Optional<TaskUserModel> getById(Integer id) throws AppException {
		Optional<TaskUserModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tu.tsu_id ");
			query.append(",tu.tsk_id ");
			query.append(",tu.usr_id ");
			query.append(",tu.stage_value ");
			query.append(",tu.recived_value ");

			query.append(",u.username ");
			query.append(",p.per_id as per_id ");
			query.append(",p.job_title as per_job_title ");
			query.append(",p.name ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tu.date_start ");
			query.append(",tu.date_finish ");

			// Task
			query.append(",tsk.tsk_id tsk_tsk_id ");
			query.append(",tsk.name tsk_name ");
			query.append(",tsk.number_jira as tsk_number_jira ");
			query.append(",ctt.cla_id as tsk_cla_id ");
			query.append(",ctt.value as tsk_cla_value ");
			query.append(",ctt.type as tsk_cla_type ");
			query.append(",ctt.label as tsk_cla_label ");
			query.append(",tsk.date_start tsk_date_start ");
			query.append(",tsk.date_finish tsk_date_finish ");
			query.append(",tsk.stg_id tsk_stg_id ");
			query.append(",tsk.special_service_place as tsk_special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start ");
			query.append(",so.date_finish as so_date_finish ");
			query.append(",so.status_cla as so_date_start ");
			query.append(",so.brand as so_brand ");
			query.append(",so.model as so_model ");
			query.append(",so.plate as so_plate ");
			query.append(",so.chassi as so_chassi ");
			query.append(",so.number as so_number ");
			query.append(",ut.cla_id as so_cla_id ");
			query.append(",ut.value as so_cla_value ");
			query.append(",ut.type as so_cla_type ");
			query.append(",ut.label as so_cla_label ");

			query.append(",stg.stg_id as stg_stg_id ");
			query.append(",stg.name as stg_name ");
			query.append(",stg.task as stg_task ");
			query.append(",stg.special as stg_special ");
			query.append(",stg.status_jira_id as stg_status_jira_id ");
			query.append(",stg.ckp_id as stg_ckp_id ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name ");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task_user as tu ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tu.status_cla ");
			query.append("INNER JOIN " + schemaName + "user as u on u.usr_id = tu.usr_id ");
			query.append("INNER JOIN " + schemaName + "person as p on p.per_id = u.per_id ");
			// Task
			query.append("INNER JOIN " + schemaName + "task as tsk on tsk.tsk_id = tu.tsk_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ctt on ctt.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");

			query.append("WHERE tu.tsu_id = :ID");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", id);

			List<TaskUserModel> list = this.getJdbcTemplatePortal().query(query.toString(), params,
					new TaskUserMapper());
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
	public List<TaskUserModel> list() throws AppException {
		List<TaskUserModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("tu.tsu_id ");
			query.append(",tu.tsk_id ");
			query.append(",tu.usr_id ");
			query.append(",tu.stage_value ");
			query.append(",tu.recived_value ");

			query.append(",u.username ");
			query.append(",p.per_id as per_id ");
			query.append(",p.job_title as per_job_title ");
			query.append(",p.name ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tu.date_start ");
			query.append(",tu.date_finish ");

			// Task
			query.append(",tsk.tsk_id tsk_tsk_id ");
			query.append(",tsk.name tsk_name ");
			query.append(",tsk.number_jira as tsk_number_jira ");
			query.append(",ctt.cla_id as tsk_cla_id ");
			query.append(",ctt.value as tsk_cla_value ");
			query.append(",ctt.type as tsk_cla_type ");
			query.append(",ctt.label as tsk_cla_label ");
			query.append(",tsk.date_start tsk_date_start ");
			query.append(",tsk.date_finish tsk_date_finish ");
			query.append(",tsk.stg_id tsk_stg_id ");
			query.append(",tsk.special_service_place as tsk_special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start ");
			query.append(",so.date_finish as so_date_finish ");
			query.append(",so.status_cla as so_date_start ");
			query.append(",so.brand as so_brand ");
			query.append(",so.model as so_model ");
			query.append(",so.plate as so_plate ");
			query.append(",so.chassi as so_chassi ");
			query.append(",so.number as so_number ");
			query.append(",ut.cla_id as so_cla_id ");
			query.append(",ut.value as so_cla_value ");
			query.append(",ut.type as so_cla_type ");
			query.append(",ut.label as so_cla_label ");

			query.append(",stg.stg_id as stg_stg_id ");
			query.append(",stg.name as stg_name ");
			query.append(",stg.task as stg_task ");
			query.append(",stg.special as stg_special ");
			query.append(",stg.status_jira_id as stg_status_jira_id ");
			query.append(",stg.ckp_id as stg_ckp_id ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name ");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task_user as tu ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tu.status_cla ");
			query.append("INNER JOIN " + schemaName + "user as u on u.usr_id = tu.usr_id ");
			query.append("INNER JOIN " + schemaName + "person as p on p.per_id = u.per_id ");
			// Task
			query.append("INNER JOIN " + schemaName + "task as tsk on tsk.tsk_id = tu.tsk_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ctt on ctt.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tu.tsu_id > 0");

			List<TaskUserModel> list = this.getJdbcTemplatePortal().query(query.toString(), new TaskUserMapper());
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
	public List<TaskUserModel> search(TaskUserModel model) throws AppException {
		List<TaskUserModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tu.tsu_id ");
			query.append(",tu.tsk_id ");
			query.append(",tu.usr_id ");
			query.append(",tu.stage_value ");
			query.append(",tu.recived_value ");

			query.append(",u.username ");
			query.append(",p.per_id as per_id ");
			query.append(",p.job_title as per_job_title ");
			query.append(",p.name ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tu.date_start ");
			query.append(",tu.date_finish ");

			// Task
			query.append(",tsk.tsk_id tsk_tsk_id ");
			query.append(",tsk.name tsk_name ");
			query.append(",tsk.number_jira as tsk_number_jira ");
			query.append(",ctt.cla_id as tsk_cla_id ");
			query.append(",ctt.value as tsk_cla_value ");
			query.append(",ctt.type as tsk_cla_type ");
			query.append(",ctt.label as tsk_cla_label ");
			query.append(",tsk.date_start tsk_date_start ");
			query.append(",tsk.date_finish tsk_date_finish ");
			query.append(",tsk.stg_id tsk_stg_id ");
			query.append(",tsk.special_service_place as tsk_special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start ");
			query.append(",so.date_finish as so_date_finish ");
			query.append(",so.status_cla as so_date_start ");
			query.append(",so.brand as so_brand ");
			query.append(",so.model as so_model ");
			query.append(",so.plate as so_plate ");
			query.append(",so.chassi as so_chassi ");
			query.append(",so.number as so_number ");
			query.append(",ut.cla_id as so_cla_id ");
			query.append(",ut.value as so_cla_value ");
			query.append(",ut.type as so_cla_type ");
			query.append(",ut.label as so_cla_label ");

			query.append(",stg.stg_id as stg_stg_id ");
			query.append(",stg.name as stg_name ");
			query.append(",stg.task as stg_task ");
			query.append(",stg.special as stg_special ");
			query.append(",stg.status_jira_id as stg_status_jira_id ");
			query.append(",stg.ckp_id as stg_ckp_id ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name ");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task_user as tu ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tu.status_cla ");
			query.append("INNER JOIN " + schemaName + "user as u on u.usr_id = tu.usr_id ");
			query.append("INNER JOIN " + schemaName + "person as p on p.per_id = u.per_id ");
			// Task
			query.append("INNER JOIN " + schemaName + "task as tsk on tsk.tsk_id = tu.tsk_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ctt on ctt.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tu.tsu_id > 0");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {

				if (model.getStatus() != null && model.getStatus().getId() > 0) {
					query.append(" AND tu.status_cla = :statusID ");
					params.addValue("statusID", model.getStatus().getId());
				}

				if (model.getTask() != null && model.getTask().getId() > 0) {
					query.append(" AND tu.tsk_id = :taskID ");
					params.addValue("taskID", model.getTask().getId());
				}

				if (model.getUser() != null && model.getUser().getId() > 0) {
					query.append(" AND tu.usr_id = :userID ");
					params.addValue("userID", model.getUser().getId());
				}
			}

			List<TaskUserModel> users = this.getJdbcTemplatePortal().query(query.toString(), params,
					new TaskUserMapper());
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
	public Optional<TaskUserModel> save(TaskUserModel model) throws AppException {

		try {
			StringBuilder query = new StringBuilder("");
			query.append("INSERT INTO task_user (  ");
			query.append("tsk_id ");
			query.append(",usr_id ");
			query.append(",status_cla ");
			query.append(",date_start ");
			query.append(",date_finish ");
			query.append(",stage_value ");
			query.append(") VALUES (");
			query.append(" :taskID ");
			query.append(" ,:userID ");
			query.append(" ,:statusID ");
			query.append(" ,:dateStart ");
			query.append(" ,:dateFinish ");
			query.append(" ,:stageValue ");
			query.append(")");

			MapSqlParameterSource params = new MapSqlParameterSource();

			params.addValue("taskID",
					model.getTask() != null && model.getTask().getId() > 0 ? model.getTask().getId() : 0);
			params.addValue("userID",
					model.getUser() != null && model.getUser().getId() > 0 ? model.getUser().getId() : 0);
			params.addValue("statusID",
					model.getStatus() != null && model.getStatus().getId() > 0 ? model.getStatus().getId() : null);
			params.addValue("stageValue", model.getStageValue());
			params.addValue("dateStart", model.getDateStart());
			params.addValue("dateFinish", model.getDateFinish());

			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.getNamedParameterJdbcTemplate().update(query.toString(), params, keyHolder);
			model.setId(this.getKey(keyHolder));

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return Optional.ofNullable(model);
	}

	@Override
	public Optional<TaskUserModel> update(TaskUserModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("UPDATE task_user SET ");

			if (model.getDateFinish() != null) {
				params.addValue("dateFinish", model.getDateFinish());
				query.append(" date_finish = :dateFinish ");
			}

			if (model.getDateFinish() != null) {
				params.addValue("dateStart", model.getDateStart());
				query.append(query.length() > 21 ? ",date_start = :dateStart" : "date_start = :dateStart");
			}

			if (model.getStatus() != null && model.getStatus().getId() > 0) {
				params.addValue("status", model.getStatus().getId());
				query.append(query.length() > 21 ? ",status_cla = :status " : "status_cla = :status ");
			}

			query.append("WHERE tsu_id = :ID");

			params.addValue("ID", model.getId());

			this.getJdbcTemplatePortal().update(query.toString(), params);

			return Optional.ofNullable(model);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void pauseAllTaskOfUser(Integer userID) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("CALL " + schemaName + "stop_user_task(:userID)");

			params.addValue("userID", userID);

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void finishOneTaskByID(Integer taskUserID) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("CALL " + schemaName + "finish_one_task_by_id(:taskUserID)");

			params.addValue("taskUserID", taskUserID);

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void pauseOneTaskByID(Integer taskUserID) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("CALL " + schemaName + "stop_one_task_by_id(:taskUserID)");

			params.addValue("taskUserID", taskUserID);

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Integer getNumberOfUsersInTask(Integer taskID, Integer userID) throws NoSuchMessageException, AppException {
		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("SELECT " + schemaName + "fnGetNumberUsersOnTask(:taskID , :userID) as countNumber");

			params.addValue("taskID", taskID);
			params.addValue("userID", userID);

			Integer count = this.getJdbcTemplatePortal().queryForObject(query.toString(), params, Integer.class);

			return count;

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Boolean canFinishTheTask(Integer taskID) throws NoSuchMessageException, AppException {
		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("SELECT " + schemaName + "fnValidateIfCanFinishTask(:taskID)");

			params.addValue("taskID", taskID);

			int count = this.getJdbcTemplatePortal().queryForObject(query.toString(), params, int.class);

			return PortalNumberUtils.intToBoolean(count);

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
			query.append("DELETE FROM task_user WHERE tsu_id = :id");

			MapSqlParameterSource params = new MapSqlParameterSource("id", id);
			this.getJdbcTemplatePortal().update(query.toString(), params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<TaskUserModel> searchInProgress(TaskUserModel model) throws AppException {
		List<TaskUserModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tu.tsu_id ");
			query.append(",tu.tsk_id ");
			query.append(",tu.usr_id ");
			query.append(",tu.stage_value ");
			query.append(",tu.recived_value ");

			query.append(",u.username ");
			query.append(",p.per_id as per_id ");
			query.append(",p.job_title as per_job_title ");
			query.append(",p.name ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tu.date_start ");
			query.append(",tu.date_finish ");

			// Task
			query.append(",tsk.tsk_id tsk_tsk_id ");
			query.append(",tsk.name tsk_name ");
			query.append(",tsk.number_jira as tsk_number_jira ");
			query.append(",ctt.cla_id as tsk_cla_id ");
			query.append(",ctt.value as tsk_cla_value ");
			query.append(",ctt.type as tsk_cla_type ");
			query.append(",ctt.label as tsk_cla_label ");
			query.append(",tsk.date_start tsk_date_start ");
			query.append(",tsk.date_finish tsk_date_finish ");
			query.append(",tsk.stg_id tsk_stg_id ");
			query.append(",tsk.special_service_place as tsk_special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start ");
			query.append(",so.date_finish as so_date_finish ");
			query.append(",so.status_cla as so_date_start ");
			query.append(",so.brand as so_brand ");
			query.append(",so.model as so_model ");
			query.append(",so.plate as so_plate ");
			query.append(",so.chassi as so_chassi ");
			query.append(",so.number as so_number ");
			query.append(",ut.cla_id as so_cla_id ");
			query.append(",ut.value as so_cla_value ");
			query.append(",ut.type as so_cla_type ");
			query.append(",ut.label as so_cla_label ");

			query.append(",stg.stg_id as stg_stg_id ");
			query.append(",stg.name as stg_name ");
			query.append(",stg.task as stg_task ");
			query.append(",stg.special as stg_special ");
			query.append(",stg.status_jira_id as stg_status_jira_id ");
			query.append(",stg.ckp_id as stg_ckp_id ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name ");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task_user as tu ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tu.status_cla ");
			query.append("INNER JOIN " + schemaName + "user as u on u.usr_id = tu.usr_id ");
			query.append("INNER JOIN " + schemaName + "person as p on p.per_id = u.per_id ");
			// Task
			query.append("INNER JOIN " + schemaName + "task as tsk on tsk.tsk_id = tu.tsk_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ctt on ctt.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tu.tsu_id > 0 AND tu.status_cla <> 39 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {

				if (model.getStatus() != null && model.getStatus().getId() > 0) {
					query.append(" AND tu.status_cla = :statusID ");
					params.addValue("statusID", model.getStatus().getId());
				}

				if (model.getTask() != null && model.getTask().getId() > 0) {
					query.append(" AND tu.tsk_id = :taskID ");
					params.addValue("taskID", model.getTask().getId());
				}

				if (model.getUser() != null && model.getUser().getId() > 0) {
					query.append(" AND tu.usr_id = :userID ");
					params.addValue("userID", model.getUser().getId());
				}
			}
			query.append("ORDER BY ct.value ASC ");

			List<TaskUserModel> users = this.getJdbcTemplatePortal().query(query.toString(), params,
					new TaskUserMapper());
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

}
