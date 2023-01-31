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
import com.portal.dao.IClassifierDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.ClassifierMapper;
import com.portal.model.ClassifierModel;

@Repository
public class ClassifierDAO extends BaseDAO implements IClassifierDAO {

	private static final Logger logger = LoggerFactory.getLogger(ClassifierDAO.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public Optional<ClassifierModel> find(ClassifierModel model) throws AppException {
		Optional<ClassifierModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT * ");
			query.append("FROM classifier ");
			query.append("WHERE cla_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (model.getId() != null && model.getId() > 0) {
				query.append("AND cla_id = :claId ");
				params.addValue("claId", model.getId());
			}

			if (model.getValue() != null && !model.getValue().equals("")) {
				query.append("AND value LIKE CONCAT('%',:value,'%') ");
				params.addValue("value", model.getValue());
			}

			if (model.getType() != null && !model.getType().equals("")) {
				query.append("AND type = :type ");
				params.addValue("type", model.getType());
			}

			query.append("LIMIT 1");

			List<ClassifierModel> classifierList = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ClassifierMapper());
			if (classifierList != null && !classifierList.isEmpty()) {
				objReturn = Optional.ofNullable(classifierList.get(0));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return objReturn;
	}

	@Override
	public Optional<ClassifierModel> getById(Integer id) throws AppException {
		Optional<ClassifierModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT * ");
			query.append("FROM classifier ");
			query.append("WHERE cla_id = :claId ");
			query.append("LIMIT 1");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("claId", id);

			List<ClassifierModel> classifierList = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ClassifierMapper());
			if (classifierList != null && !classifierList.isEmpty()) {
				objReturn = Optional.ofNullable(classifierList.get(0));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return objReturn;
	}

	@Override
	public List<ClassifierModel> list() throws AppException {
		List<ClassifierModel> listReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT * ");
			query.append("FROM classifier ");
			query.append("ORDER BY type, value");

			List<ClassifierModel> users = this.getJdbcTemplatePortal().query(query.toString(), new ClassifierMapper());
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
	public List<ClassifierModel> search(ClassifierModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT cla.cla_id, cla.value, cla.type, cla.label, cla.description ");
			query.append("FROM classifier cla ");
			query.append("WHERE cla.cla_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (model.getId() != null && model.getId() > 0) {
				query.append("AND cla_id = :claId ");
				params.addValue("claId", model.getId());
			}

			if (model.getValue() != null && !model.getValue().equals("")) {
				query.append("AND value LIKE CONCAT('%',:value,'%') ");
				params.addValue("value", model.getValue());
			}

			if (model.getType() != null && !model.getType().equals("")) {
				query.append("AND type = :type ");
				params.addValue("type", model.getType());
			}

			query.append("ORDER BY type, value");

			return this.getJdbcTemplatePortal().query(query.toString(), params, new ClassifierMapper());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<ClassifierModel> save(ClassifierModel model) throws AppException {

		String query = "INSERT INTO `classifier` (`cla_id`, `value`, `type`, `label`, `description`) "
				+ "VALUES ( :id, :value, :type, :label, :description )";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", model.getId());
		params.addValue("value", model.getValue());
		params.addValue("type", model.getType());
		params.addValue("label", model.getLabel());
		params.addValue("description", model.getDescription());

		this.getJdbcTemplatePortal().update(query, params);

		return Optional.ofNullable(model);
	}

	@Override
	public Optional<ClassifierModel> update(ClassifierModel model) throws AppException {
		try {
			String query = "UPDATE `classifier` SET `value`=:value, `type`=:type, `label`=:label, `description`=:description WHERE cla_id = id ";

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("value", model.getValue());
			params.addValue("type", model.getType());
			params.addValue("label", model.getLabel());
			params.addValue("description", model.getDescription());

			params.addValue("id", model.getId());

			this.getJdbcTemplatePortal().update(query, params);

			return Optional.ofNullable(model);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@Override
	public void delete(Integer id) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM classifier WHERE cla_id = :id");

			MapSqlParameterSource params = new MapSqlParameterSource("id", id);
			this.getJdbcTemplatePortal().update(query.toString(), params);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@Override
	public List<ClassifierModel> searchMultipleIds(List<Integer> idsList) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT * ");
			query.append("FROM " + schemaName + "classifier ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			if (idsList != null && idsList.size() > 0) {
				query.append("WHERE cla_id in ( :ids ) ");
				params.addValue("ids", idsList);
			}

			query.append("ORDER BY type, value");

			return this.getJdbcTemplatePortal().query(query.toString(), params, new ClassifierMapper());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}
	}

}
