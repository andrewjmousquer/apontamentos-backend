package com.portal.dao.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.portal.config.BaseDAO;
import com.portal.dao.ITaskUserTimeDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.TaskUserTimeMapper;
import com.portal.model.TaskUserTimeModel;

@Repository
public class TaskUserTimeDAO extends BaseDAO implements ITaskUserTimeDAO {

	private static final Logger logger = LoggerFactory.getLogger(TaskUserTimeDAO.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public Optional<TaskUserTimeModel> getById(Integer id) throws AppException {
		Optional<TaskUserTimeModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tut.tut_id ");
			query.append(",tut.tsu_id ");
			query.append(",tut.date_start ");
			query.append(",tut.date_finish ");
			query.append("FROM " + schemaName + "task_user_time_register as tut ");
			query.append("WHERE tut.tut_id = :ID");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", id);

			List<TaskUserTimeModel> list = this.getJdbcTemplatePortal().query(query.toString(), params,
					new TaskUserTimeMapper());
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
	public Optional<TaskUserTimeModel> getUnfinishTimeByTask(Integer id) throws AppException {
		Optional<TaskUserTimeModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tut.tut_id ");
			query.append(",tut.tsu_id ");
			query.append(",tut.date_start ");
			query.append(",tut.date_finish ");
			query.append("FROM " + schemaName + "task_user_time_register as tut ");
			query.append("WHERE tut.tsu_id = :taskUserID AND tut.date_finish is null");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("taskUserID", id);

			List<TaskUserTimeModel> list = this.getJdbcTemplatePortal().query(query.toString(), params,
					new TaskUserTimeMapper());
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
	public List<TaskUserTimeModel> searchByTaskUser(Integer taskUser) throws AppException {
		List<TaskUserTimeModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tut.tut_id ");
			query.append(",tut.tsu_id ");
			query.append(",tut.date_start ");
			query.append(",tut.date_finish ");
			query.append("FROM " + schemaName + "task_user_time_register as tut ");
			query.append("WHERE tut.tut_id > 0 AND tut.tsu_id = :taskUserID");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("taskUserID", taskUser);

			List<TaskUserTimeModel> users = this.getJdbcTemplatePortal().query(query.toString(), params,
					new TaskUserTimeMapper());
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
	public Optional<TaskUserTimeModel> save(TaskUserTimeModel model) throws AppException {

		try {
			StringBuilder query = new StringBuilder("");
			query.append("INSERT INTO task_user_time_register (  ");
			query.append("date_start ");
			query.append(",date_finish ");
			query.append(",tsu_id ");
			query.append(") VALUES (");
			query.append(" :dateStart ");
			query.append(" ,:dateFinish ");
			query.append(" ,:taskUserID ");
			query.append(")");

			MapSqlParameterSource params = new MapSqlParameterSource();

			params.addValue("taskUserID", model.getTaskUser() != null ? model.getTaskUser().getId() : 0);
			params.addValue("dateStart", model.getDateStart());
			params.addValue("dateFinish", model.getDateFinish());

			this.getNamedParameterJdbcTemplate().update(query.toString(), params);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return Optional.ofNullable(model);
	}

	@Override
	public Optional<TaskUserTimeModel> update(TaskUserTimeModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			MapSqlParameterSource params = new MapSqlParameterSource();

			query.append("UPDATE task_user_time_register SET ");

			if (model.getDateFinish() != null) {
				params.addValue("dateFinish", model.getDateFinish());
				query.append(" date_finish = :dateFinish ");
			}

			if (model.getDateStart() != null) {
				params.addValue("dateStart", model.getDateStart());
				query.append(query.length() > 35 ? ",date_start = :dateStart " : "date_start = :dateStart ");
			}

			query.append("WHERE tut_id = :ID");

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
	public void delete(Integer id) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM task_user_time_register WHERE tut_id = :id");

			MapSqlParameterSource params = new MapSqlParameterSource("id", id);
			this.getJdbcTemplatePortal().update(query.toString(), params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void deleteByTaskUser(Integer idTaskUser) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM task_user_time_register WHERE tsu_id = :idTaskUser");

			MapSqlParameterSource params = new MapSqlParameterSource("idTaskUser", idTaskUser);
			this.getJdbcTemplatePortal().update(query.toString(), params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

}
