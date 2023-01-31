package com.portal.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
import com.portal.dao.ITaskDAO;
import com.portal.dto.UserProfileDTO;
import com.portal.enums.StatusType;
import com.portal.exceptions.AppException;
import com.portal.mapper.TaskMapper;
import com.portal.mapper.TaskWithTimeMapper;
import com.portal.mapper.TeamMapper;
import com.portal.model.TaskModel;
import com.portal.model.TeamModel;
import com.portal.utils.PortalNumberUtils;

@Repository
public class TaskDAO extends BaseDAO implements ITaskDAO {

	private static final Logger logger = LoggerFactory.getLogger(TaskDAO.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public Optional<TaskModel> find(TaskModel model) throws AppException {
		Optional<TaskModel> objReturn = Optional.empty();
		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tsk.tsk_id ");
			query.append(",tsk.name ");
			query.append(",tsk.number_jira ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tsk.date_start ");
			query.append(",tsk.date_finish ");
			query.append(",tsk.stg_id ");
			query.append(",tsk.special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start  ");
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
			query.append(",stg.payment_by_team as stg_payment_by_team ");
			query.append(",stg.value as stg_value ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task as tsk ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tsk.tsk_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (StringUtils.isNotEmpty(model.getName())) {
				query.append(" AND tsk.name LIKE :name ");
				params.addValue("name", this.mapLike(model.getName()));
			}

			if (StringUtils.isNotEmpty(model.getNumberJira())) {
				query.append(" AND tsk.number_jira = :jiraID ");
				params.addValue("jiraID", model.getNumberJira());
			}

			if (model.getServiceOrder() != null && model.getServiceOrder().getId() > 0) {
				query.append(" AND tsk.svo_id = :serviceOrderID ");
				params.addValue("serviceOrderID", model.getServiceOrder().getId());
			}

			if (model.getStage() != null && model.getStage().getId() > 0) {
				query.append(" AND tsk.stg_id = :stageID ");
				params.addValue("stageID", model.getStage().getId());
			}

			List<TaskModel> list = this.getJdbcTemplatePortal().query(query.toString(), params, new TaskMapper());
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
	public Optional<TaskModel> getById(Integer id) throws AppException {
		Optional<TaskModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("tsk.tsk_id ");
			query.append(",tsk.name ");
			query.append(",tsk.number_jira ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tsk.date_start ");
			query.append(",tsk.date_finish ");
			query.append(",tsk.stg_id ");
			query.append(",tsk.special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start  ");
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
			query.append(",stg.payment_by_team as stg_payment_by_team ");
			query.append(",stg.value as stg_value ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task as tsk ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tsk.tsk_id = :ID ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", id);

			List<TaskModel> list = this.getJdbcTemplatePortal().query(query.toString(), params, new TaskMapper());
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
	public List<TaskModel> list() throws AppException {
		List<TaskModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("tsk.tsk_id ");
			query.append(",tsk.name ");
			query.append(",tsk.number_jira ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tsk.date_start ");
			query.append(",tsk.date_finish ");
			query.append(",tsk.stg_id ");
			query.append(",tsk.special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start  ");
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
			query.append(",stg.payment_by_team as stg_payment_by_team ");
			query.append(",stg.value as stg_value ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task as tsk ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tsk.tsk_id > 0 ");

			List<TaskModel> list = this.getJdbcTemplatePortal().query(query.toString(), new TaskMapper());
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
	public List<TaskModel> search(TaskModel model) throws AppException {
		List<TaskModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("tsk.tsk_id ");
			query.append(",tsk.name ");
			query.append(",tsk.number_jira ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tsk.date_start ");
			query.append(",tsk.date_finish ");
			query.append(",tsk.stg_id ");
			query.append(",tsk.special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start  ");
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
			query.append(",stg.payment_by_team as stg_payment_by_team ");
			query.append(",stg.value as stg_value ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task as tsk ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tsk.tsk_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (StringUtils.isNotEmpty(model.getName())) {
				query.append(" AND tsk.name LIKE :name ");
				params.addValue("name", this.mapLike(model.getName()));
			}

			if (StringUtils.isNotEmpty(model.getNumberJira())) {
				query.append(" AND tsk.number_jira = :jiraID ");
				params.addValue("jiraID", model.getNumberJira());

			}

			if (model.getServiceOrder() != null && model.getServiceOrder().getId() > 0) {
				query.append(" AND tsk.svo_id = :serviceOrderID ");
				params.addValue("serviceOrderID", model.getServiceOrder().getId());
			}

			if (model.getStage() != null && model.getStage().getId() != null && model.getStage().getId() > 0) {
				query.append(" AND tsk.stg_id = :stageID ");
				params.addValue("stageID", model.getStage().getId());
			}

			List<TaskModel> users = this.getJdbcTemplatePortal().query(query.toString(), params, new TaskMapper());
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
	public List<TaskModel> searchByUser(TaskModel model, UserProfileDTO user, boolean inProgress) throws AppException {
		List<TaskModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("tsk.tsk_id ");
			query.append(",tsk.name ");
			query.append(",tsk.number_jira ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tsk.date_start ");
			query.append(",tsk.date_finish ");
			query.append(",tsk.stg_id ");
			query.append(",tsk.special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start  ");
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
			query.append(",stg.payment_by_team as stg_payment_by_team ");
			query.append(",stg.value as stg_value ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task as tsk ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("INNER JOIN " + schemaName + "stage_team as stu on stu.stg_id = stg.stg_id ");
			query.append("RIGHT JOIN " + schemaName + "team as tam on tam.tam_id = stu.tam_id ");
			query.append("RIGHT JOIN " + schemaName + "user_team as utt on utt.tam_id = tam.tam_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tsk.tsk_id > 0 and utt.usr_id = :idUser ");

			if (inProgress)
				query.append(
						"AND exists (select 1 from task_user as subtask where tsk.tsk_id = subtask.tsk_id and subtask.usr_id = :idUser and subtask.status_cla <> 39) ");
			else
				query.append(
						"AND not exists (select 1 from task_user as subtask where tsk.tsk_id = subtask.tsk_id and subtask.usr_id = :idUser) ");

			query.append("AND tsk.status_cla <> 39 ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			params.addValue("idUser", user.getUser().getId());

			if (StringUtils.isNotEmpty(model.getName())) {
				query.append(" AND tsk.name LIKE :name ");
				params.addValue("name", this.mapLike(model.getName()));
			}

			if (StringUtils.isNotEmpty(model.getNumberJira())) {
				query.append(" AND tsk.number_jira = :jiraID ");
				params.addValue("jiraID", model.getNumberJira());

			}

			if (model.getServiceOrder() != null && model.getServiceOrder().getId() != null
					&& model.getServiceOrder().getId() > 0) {
				query.append(" AND tsk.svo_id = :serviceOrderID ");
				params.addValue("serviceOrderID", model.getServiceOrder().getId());
			}

			if (model.getStage() != null && model.getStage().getId() != null && model.getStage().getId() > 0) {
				query.append(" AND tsk.stg_id = :stageID ");
				params.addValue("stageID", model.getStage().getId());
			}

			query.append("group by tsk.tsk_id");

			List<TaskModel> users = this.getJdbcTemplatePortal().query(query.toString(), params, new TaskMapper());
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
	public Optional<TaskModel> save(TaskModel model) throws AppException {

		try {
			StringBuilder query = new StringBuilder("");
			query.append("INSERT INTO task (  ");
			query.append("name ");
			query.append(",number_jira ");
			query.append(",status_cla ");
			query.append(",date_start ");
			query.append(",date_finish ");
			query.append(",stg_id ");
			query.append(",svo_id ");
			query.append(",special_service_place ");
			query.append(") VALUES (");
			query.append("	:name ");
			query.append("	,:numberJira ");
			query.append("	,:status ");
			query.append("	,:dateStart ");
			query.append("	,:dateFinish ");
			query.append("	,:stage ");
			query.append("	,:serviceOrder ");
			query.append("	,:specialPlace ");
			query.append(")");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("name", model.getName());
			params.addValue("numberJira", model.getNumberJira());
			params.addValue("status",
					model.getStatus() != null && model.getStatus().getId() > 0 ? model.getStatus().getId() : null);
			params.addValue("dateStart", model.getDateStart());
			params.addValue("dateFinish", model.getDateFinish());
			params.addValue("specialPlace", model.getPlace());
			params.addValue("stage",
					model.getStage() != null && model.getStage().getId() > 0 ? model.getStage().getId() : null);
			params.addValue("serviceOrder",
					model.getServiceOrder() != null && model.getServiceOrder().getId() > 0
							? model.getServiceOrder().getId()
							: null);

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
	public Optional<TaskModel> update(TaskModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("UPDATE task SET ");
			query.append(" name = :name ");
			query.append(",number_jira = :numberJira ");
			query.append(",status_cla = :status ");
			query.append(",date_start = :dateStart ");
			query.append(",date_finish = :dateFinish ");
			query.append(",special_service_place = :specialPlace ");
			query.append(",stg_id = :stage ");
			query.append(",svo_id = :serviceOrder ");
			query.append("WHERE ");
			query.append("	tsk_id = :ID ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", model.getId());
			params.addValue("name", model.getName());
			params.addValue("numberJira", model.getNumberJira());
			params.addValue("status",
					model.getStatus() != null && model.getStatus().getId() > 0 ? model.getStatus().getId() : null);
			params.addValue("dateStart", model.getDateStart());
			params.addValue("dateFinish", model.getDateFinish());
			params.addValue("specialPlace", model.getPlace());
			params.addValue("stage",
					model.getStage() != null && model.getStage().getId() > 0 ? model.getStage().getId() : null);
			params.addValue("serviceOrder",
					model.getServiceOrder() != null && model.getServiceOrder().getId() > 0
							? model.getServiceOrder().getId()
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
	public void updateTaskStatus(Integer taskToChange, StatusType statusToGo, StatusType statusToConsider)
			throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("UPDATE task SET ");
			query.append("status_cla = :status ");
			query.append("WHERE ");
			query.append("tsk_id = :ID ");

			if (statusToConsider != null) {
				query.append(
						"and not exists (select 1 from task_user as tu where tu.tsk_id = task.tsk_id and tu.status_cla = :statusToValidate");
				params.addValue("statusToValidate", statusToConsider.getType().getId());
			}

			params.addValue("ID", taskToChange);
			params.addValue("status", statusToGo.getType().getId());

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(this.messageSource.getMessage("error.generic.update",
					new Object[] { "Status tarefa" }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void saveChecklistFile(Integer taskToChange, String checklistFileName) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("UPDATE task SET ");
			query.append("checklist_file = :checklistFileName ");
			query.append("WHERE ");
			query.append("tsk_id = :ID ");

			params.addValue("ID", taskToChange);
			params.addValue("checklistFileName", checklistFileName);

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(this.messageSource.getMessage("error.generic.update",
					new Object[] { "Arquivo da tarefa" }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void delete(Integer id) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM task WHERE tsk_id = :id");

			MapSqlParameterSource params = new MapSqlParameterSource("id", id);
			this.getJdbcTemplatePortal().update(query.toString(), params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<List<TaskModel>> getByServiceOrderId(Integer id) throws AppException {
		Optional<List<TaskModel>> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("tsk.tsk_id ");
			query.append(",tsk.name ");
			query.append(",tsk.number_jira ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tsk.date_start ");
			query.append(",tsk.date_finish ");
			query.append(",tsk.stg_id ");
			query.append(",tsk.checklist_file ");
			query.append(",tsk.special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start  ");
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
			query.append(",stg.payment_by_team as stg_payment_by_team ");
			query.append(",stg.value as stg_value ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name");
			query.append(",ckp.description as stg_ckp_description ");
			query.append(
					",SEC_TO_TIME((CASE WHEN tsk.status_cla = 41 THEN 0 ELSE fnGetTotalTimeInSecondsOfTask(tsk.tsk_id) END)) as total_time ");

			query.append("FROM " + schemaName + "task as tsk ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tsk.svo_id = :ID ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", id);

			List<TaskModel> list = this.getJdbcTemplatePortal().query(query.toString(), params,
					new TaskWithTimeMapper());
			if (!CollectionUtils.isEmpty(list)) {
				objReturn = Optional.of(list);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return objReturn;
	}

	@Override
	public List<TeamModel> getTeamWhiTaskUser(Integer id) throws NoSuchMessageException, AppException {
		List<TeamModel> objReturn = new ArrayList<>();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT t.* ");
			query.append("FROM team as t ");
			query.append("INNER JOIN user_team as ut ");
			query.append("ON ut.tam_id = t.tam_id ");
			query.append("INNER JOIN user as u ON u.usr_id = ut.usr_id ");
			query.append(
					"WHERE EXISTS ( SELECT 1 FROM task_user AS tu where tu.usr_id = ut.usr_id and tu.tsk_id =:tskId )");
			query.append("GROUP BY t.tam_id");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("tskId", id);

			List<TeamModel> teamTask = this.getJdbcTemplatePortal().query(query.toString(), params, new TeamMapper());
			if (!CollectionUtils.isEmpty(teamTask)) {
				objReturn = teamTask;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return objReturn;
	}

	@Override
	public Optional<TaskModel> getLastTaskByUser(Integer userID) throws NoSuchMessageException, AppException {
		Optional<TaskModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("tsk.tsk_id ");
			query.append(",tsk.name ");
			query.append(",tsk.number_jira ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tsk.date_start ");
			query.append(",tsk.date_finish ");
			query.append(",tsk.stg_id ");
			query.append(",tsk.special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start  ");
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
			query.append(",stg.payment_by_team as stg_payment_by_team ");
			query.append(",stg.value as stg_value ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task as tsk ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("INNER JOIN " + schemaName + "task_user as tu  ON tu.tsk_id = tsk.tsk_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE tu.usr_id = :ID ");
			query.append("ORDER BY tu.tsu_id DESC");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", userID);

			List<TaskModel> list = this.getJdbcTemplatePortal().query(query.toString(), params, new TaskMapper());

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
	public List<TaskModel> getTasksActiveOnTeamFinded(Integer id) throws NoSuchMessageException, AppException {
		List<TaskModel> objReturn = new ArrayList<>();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append("tsk.tsk_id ");
			query.append(",tsk.name ");
			query.append(",tsk.number_jira ");
			query.append(",ct.cla_id as cla_id ");
			query.append(",ct.value as cla_value ");
			query.append(",ct.type as cla_type ");
			query.append(",ct.label as cla_label ");
			query.append(",tsk.date_start ");
			query.append(",tsk.date_finish ");
			query.append(",tsk.stg_id ");
			query.append(",tsk.special_service_place ");

			query.append(",tsk.svo_id as so_svo_id ");
			query.append(",so.number_jira as so_number_jira ");
			query.append(",so.date_start as so_date_start  ");
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
			query.append(",stg.payment_by_team as stg_payment_by_team ");
			query.append(",stg.value as stg_value ");
			query.append(",ckl.ckl_id as stg_ckl_id ");
			query.append(",ckl.name as stg_cw_name ");
			query.append(",ckp.name as stg_ckp_name");
			query.append(",ckp.description as stg_ckp_description ");

			query.append("FROM " + schemaName + "task as tsk ");
			query.append("INNER JOIN " + schemaName + "classifier as ct on ct.cla_id = tsk.status_cla ");
			query.append("INNER JOIN " + schemaName + "service_order as so on so.svo_id = tsk.svo_id ");
			query.append("INNER JOIN " + schemaName + "stage as stg on stg.stg_id = tsk.stg_id ");
			query.append("INNER JOIN " + schemaName + "task_user as tu  ON tu.tsk_id = tsk.tsk_id ");
			query.append("INNER JOIN " + schemaName + "user as u ON u.usr_id = tu.usr_id ");
			query.append("INNER JOIN " + schemaName + "user_team as usrt ON usrt.usr_id = u.usr_id ");
			query.append("LEFT JOIN " + schemaName + "checkpoint as ckp ON ckp.ckp_id = stg.ckp_id ");
			query.append("LEFT JOIN " + schemaName + "check_list as ckl ON ckl.ckl_id = stg.ckl_id ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = so.status_cla ");
			query.append("WHERE usrt.tam_id=:id AND (tu.status_cla = 38  OR tu.status_cla = 40)");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			List<TaskModel> list = this.getJdbcTemplatePortal().query(query.toString(), params, new TaskMapper());

			if (!CollectionUtils.isEmpty(list)) {
				objReturn = list;
			}

		} catch (Exception e) {
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return objReturn;
	}

	@Override
	public void generateTaskPayment(Integer taskToPay, BigDecimal valueToPay, Boolean sharedByTeam)
			throws AppException {

		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("CALL " + schemaName + "generate_task_payment(:taskToPay ,:valueToPay , :sharedByTeam)");

			params.addValue("taskToPay", taskToPay);
			params.addValue("valueToPay", valueToPay);
			params.addValue("sharedByTeam", PortalNumberUtils.booleanToInt(sharedByTeam));

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

	}
}
