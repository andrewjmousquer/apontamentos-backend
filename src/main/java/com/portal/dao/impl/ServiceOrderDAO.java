package com.portal.dao.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.portal.config.BaseDAO;
import com.portal.dao.IServiceOrderDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.ServiceOrderMapper;
import com.portal.model.ServiceOrderModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ServiceOrderDAO extends BaseDAO implements IServiceOrderDAO {

	@Override
	public Optional<ServiceOrderModel> find(ServiceOrderModel model) throws AppException {
		Optional<ServiceOrderModel> objReturn = Optional.empty();

		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append(" cw.svo_id,");
			query.append(" cw.number_jira ");
			query.append(",cw.date_start ");
			query.append(",cw.date_finish ");
			query.append(",cw.status_cla ");
			query.append(",cw.brand ");
			query.append(",cw.model ");
			query.append(",cw.plate ");
			query.append(",cw.chassi ");
			query.append(",cw.number ");
			query.append(",ut.cla_id ");
			query.append(",ut.value as cla_value ");
			query.append(",ut.type as cla_type ");
			query.append(",ut.label as cla_label ");
			query.append("FROM " + schemaName + "service_order AS cw ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = cw.status_cla ");
			query.append("WHERE cw.svo_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND svo_id = :id ");
					params.addValue("id", model.getId());
				}
				if (StringUtils.isNotEmpty(model.getNumberJira())) {
					query.append("AND number_jira = :number_jira ");
					params.addValue("number_jira", model.getNumberJira());
				}
				if (model.getDateStart() != null) {
					query.append("AND date_start = :date_start ");
					params.addValue("date_start", model.getDateStart());
				}
				if (model.getDateFinish() != null) {
					query.append("AND date_finish = :date_finish ");
					params.addValue("date_finish", model.getDateFinish());
				}
				if (model.getStatusOs() != null && model.getId() > 0) {
					query.append("AND status_cla = :status_cla ");
					params.addValue("status_cla", model.getStatusOs().getId());
				}
				if (StringUtils.isNotEmpty(model.getBrand())) {
					query.append("AND brand LIKE :brand ");
					params.addValue("brand", mapLike(model.getBrand()));
				}
				if (StringUtils.isNotEmpty(model.getModel())) {
					query.append("AND model LIKE :model ");
					params.addValue("model", mapLike(model.getModel()));
				}
				if (StringUtils.isNotEmpty(model.getPlate())) {
					query.append("AND plate LIKE :plate ");
					params.addValue("plate", model.getPlate());
				}

				if (StringUtils.isNotEmpty(model.getChassi()) && StringUtils.isNotEmpty(model.getNumber())) {
					query.append("AND ( chassi LIKE :chassi OR number LIKE :number )");
					params.addValue("chassi", mapLike(model.getChassi()));
					params.addValue("number", model.getNumber());
				}

			}

			log.trace("[QUERY] service_order.find {} [PARAMS] : {}", query, params.getValues());

			List<ServiceOrderModel> services = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ServiceOrderMapper());
			if (!CollectionUtils.isEmpty(services)) {
				objReturn = Optional.ofNullable(services.get(0));
			}

		} catch (Exception e) {
			log.error("Erro ao buscar order de serviço.", e);
			throw new AppException("Erro ao buscar order de serviço.", e);
		}

		return objReturn;

	}

	@Override
	public Optional<ServiceOrderModel> getById(Integer id) throws AppException {
		Optional<ServiceOrderModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append(" cw.svo_id,");
			query.append(" cw.number_jira ");
			query.append(",cw.date_start ");
			query.append(",cw.date_finish ");
			query.append(",cw.status_cla ");
			query.append(",cw.brand ");
			query.append(",cw.model ");
			query.append(",cw.plate ");
			query.append(",cw.chassi ");
			query.append(",cw.number ");
			query.append(",ut.cla_id ");
			query.append(",ut.value as cla_value ");
			query.append(",ut.type as cla_type ");
			query.append(",ut.label as cla_label ");
			query.append("FROM " + schemaName + "service_order AS cw ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = cw.status_cla ");
			query.append("WHERE cw.svo_id = :id");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] service_order.getById: {} [PARAMS] : {}", query, params.getValues());

			List<ServiceOrderModel> service = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ServiceOrderMapper());

			if (!CollectionUtils.isEmpty(service)) {
				objReturn = Optional.ofNullable(service.get(0));
			}

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (Exception e) {
			log.trace("Erro ao consultar uma ordem de serviço.", e);
			throw new AppException("Erro ao consultar uma ordem de serviço.", e);
		}

		return objReturn;

	}

	@Override
	public List<ServiceOrderModel> list() throws AppException {

		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append(" cw.svo_id,");
			query.append(" cw.number_jira ");
			query.append(",cw.date_start ");
			query.append(",cw.date_finish ");
			query.append(",cw.status_cla ");
			query.append(",cw.brand ");
			query.append(",cw.model ");
			query.append(",cw.plate ");
			query.append(",cw.chassi ");
			query.append(",cw.number ");
			query.append(",ut.cla_id ");
			query.append(",ut.value as cla_value ");
			query.append(",ut.type as cla_type ");
			query.append(",ut.label as cla_label ");
			query.append("FROM " + schemaName + "service_order AS cw ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = cw.status_cla ");
			query.append("WHERE cw.svo_id > 0 ");
			query.append("ORDER BY cw.svo_id");

			log.trace("[QUERY] service_order.list: {} [PARAMS]: {}", query);

			return this.getJdbcTemplate().query(query.toString(), new ServiceOrderMapper());
		} catch (Exception e) {
			log.error("Erro ao tentar listar ordem de serviços", e);
			throw new AppException("Erro ao listar as ordens de serviços", e);
		}

	}

	@Override
	public List<ServiceOrderModel> search(ServiceOrderModel model) throws AppException {
		List<ServiceOrderModel> objReturn = null;
		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append(" cw.svo_id,");
			query.append(" cw.number_jira ");
			query.append(",cw.date_start ");
			query.append(",cw.date_finish ");
			query.append(",cw.status_cla ");
			query.append(",cw.brand ");
			query.append(",cw.model ");
			query.append(",cw.plate ");
			query.append(",cw.chassi ");
			query.append(",cw.number ");
			query.append(",ut.cla_id ");
			query.append(",ut.value as cla_value ");
			query.append(",ut.type as cla_type ");
			query.append(",ut.label as cla_label ");
			query.append("FROM " + schemaName + "service_order AS cw ");
			query.append("INNER JOIN " + schemaName + "classifier as ut on ut.cla_id = cw.status_cla ");
			query.append("WHERE cw.svo_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND svo_id = :id ");
					params.addValue("id", model.getId());
				}
				if (StringUtils.isNotEmpty(model.getNumberJira())) {

					query.append("AND number_jira = :number_jira ");
					params.addValue("number_jira", model.getNumberJira());
				}
				if (model.getDateStart() != null) {
					query.append("AND date_start = :date_start ");
					params.addValue("date_start", model.getDateStart());
				}
				if (model.getDateFinish() != null) {
					query.append("AND date_finish = :date_finish ");
					params.addValue("date_finish", model.getDateFinish());
				}
				if (model.getStatusOs() != null && model.getId() > 0) {
					query.append("AND status_cla = :status_cla ");
					params.addValue("status_cla", model.getStatusOs().getId());
				}
				if (StringUtils.isNotEmpty(model.getBrand())) {
					query.append("AND brand LIKE :brand ");
					params.addValue("brand", mapLike(model.getBrand()));
				}
				if (StringUtils.isNotEmpty(model.getModel())) {
					query.append("AND model LIKE :model ");
					params.addValue("model", mapLike(model.getModel()));
				}
				if (StringUtils.isNotEmpty(model.getPlate())) {
					query.append("AND plate LIKE :plate ");
					params.addValue("plate", model.getPlate());
				}
				if (StringUtils.isNotEmpty(model.getChassi())) {
					query.append("AND chassi LIKE :chassi ");
					params.addValue("chassi", mapLike(model.getChassi()));
				}

				if (StringUtils.isNotEmpty(model.getNumber())) {
					query.append("AND number LIKE :number");
					params.addValue("number", model.getNumber());
				}
			}

			log.trace("[QUERY] service_order.search: {} [PARAMS]: {}", query, params.getValues());

			List<ServiceOrderModel> service = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ServiceOrderMapper());
			if (!CollectionUtils.isEmpty(service)) {
				objReturn = service;
			}

		} catch (Exception e) {
			log.error("Erro ao procurar ordem de serviço", e);
			throw new AppException("Erro ao procurar ordem de serviço.", e);
		}

		return objReturn;
	}

	@Override
	public Optional<ServiceOrderModel> save(ServiceOrderModel model) throws AppException {
		try {

			StringBuilder query = new StringBuilder("");

			query.append("INSERT INTO " + schemaName + "service_order ");
			query.append("(number_jira, date_start, date_finish, status_cla, brand, model, plate, chassi, number) ");
			query.append("VALUES ");
			query.append(
					"(:number_jira, :date_start, :date_finish, :status_cla, :brand, :model, :plate, :chassi, :number )");
			query.append("");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("number_jira", model.getNumberJira());
			params.addValue("date_start", model.getDateStart());
			params.addValue("date_finish", model.getDateFinish());
			params.addValue("status_cla", model.getStatusOs() != null ? model.getStatusOs().getId() : 0);

			params.addValue("brand", model.getBrand());
			params.addValue("model", model.getModel());
			params.addValue("plate", model.getPlate());
			params.addValue("chassi", model.getChassi());
			params.addValue("number", model.getNumber());

			KeyHolder keyHolder = new GeneratedKeyHolder();

			this.getNamedParameterJdbcTemplate().update(query.toString(), params, keyHolder);

			model.setId(this.getKey(keyHolder));

			return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar salvar ordem de serviço: {}", model, e);
			throw new AppException("Erro ao tentar salvar ordem de serviço.", e);
		}
	}

	@Override
	public Optional<ServiceOrderModel> update(ServiceOrderModel model) throws AppException {

		try {
			StringBuilder query = new StringBuilder();

			query.append("UPDATE " + schemaName + "service_order SET ").append("number_jira = :number_jira, ")
					.append("date_start = :date_start, ").append("date_finish = :date_finish, ")
					.append("status_cla = :status_cla, ").append("brand = :brand, ").append("model = :model, ")
					.append("plate = :plate, ").append("chassi = :chassi, ").append("usr_id = :usr_id, ")
					.append("number = :number").append(" WHERE ").append("svo_id = :svo_id ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("number_jira", model.getNumberJira());
			params.addValue("date_start", model.getDateStart());
			params.addValue("date_finish", model.getDateFinish());
			params.addValue("status_cla", model.getStatusOs());
			params.addValue("brand", model.getBrand());
			params.addValue("model", model.getModel());
			params.addValue("plate", model.getPlate());
			params.addValue("chassi", model.getChassi());
			params.addValue("chassi", model.getId());
			params.addValue("number", model.getNumber());

			this.getJdbcTemplatePortal().update(query.toString(), params);
			return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar atualizar ordem de serviço : {}", model, e);
			throw new AppException("Erro ao tentar atualizar ordem de serviço.", e);
		}
	}

	@Override
	public void delete(Integer id) throws AppException {

		try {

			StringBuilder query = new StringBuilder();
			query.append("DELETE ");
			query.append("FROM service_order ");
			query.append("WHERE svo_id = :svoid ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("svoid", id);

			log.trace("[QUERY] service_order.delete : {} [PARAMS] : {}", query, params.getValues());
			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir.", e);
			throw new AppException("Erro ao excluir.", e);
		}

	}

}
