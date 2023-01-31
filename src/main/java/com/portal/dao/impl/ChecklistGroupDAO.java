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
import com.portal.dao.IChecklistGroupDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.ChecklistGroupMapper;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ChecklistGroupDAO extends BaseDAO implements IChecklistGroupDAO {



	@Override
	public Optional<ChecklistGroupModel> save(ChecklistModel checklistModel, ChecklistGroupModel model)
			throws AppException {


		StringBuilder query = new StringBuilder("");
		query.append("INSERT INTO " + schemaName + "checklist_group (");
		query.append("name, ");
		query.append("ckl_id ");
		query.append(") VALUES (");
		query.append(":name, ");
		query.append(":ckl_id)");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ckl_id", checklistModel.getId());
		params.addValue("name", model.getName());

		log.trace("[QUERY] checklist_group.save: {} [PARAMS]: {}", query, params.getValues());

		KeyHolder keyHolder = new GeneratedKeyHolder();


		try {
			this.getJdbcTemplatePortal().update(query.toString(), params, keyHolder);

			model.setCkgId(this.getKey(keyHolder));


		} catch (Exception e) {
			log.error("Erro ao tentar salvar: {}", checklistModel, e);
			throw new AppException("Erro ao tentar salvar.", e);
		}
		return Optional.ofNullable(model);
	}


	@Override
	public void delete(Integer cklId, Integer ckgId) throws AppException {
		
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append(schemaName + "checklist_group ");
		query.append("WHERE ckg_id = :ckgid ");
		query.append("AND ckl_id = :cklid");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ckgid", ckgId);
		params.addValue("cklid", cklId);

		log.trace("[QUERY] checklist_group.delete : {} [PARAMS] : {}", query, params.getValues());
		try {

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir: {}", ckgId, e);
			throw new AppException("Erro ao tentar excluir.", e);

		}
	}


	@Override
	public void delete(Integer checklistModel) throws AppException {

		try {

		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append("checklist_group ");
		query.append("WHERE ckl_id = :checklistId ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("checklistId", checklistModel);

		log.trace("[QUERY] checklist_group.delete : {} [PARAMS] : {}", query, params.getValues());
		this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir: {}", checklistModel, e);
			throw new AppException("Erro ao tentar excluir.", e);

		}
	}

	@Override
	public Optional<ChecklistGroupModel> update(ChecklistModel checklistModel, ChecklistGroupModel model)
			throws AppException {

		try {

		StringBuilder query = new StringBuilder("");
		query.append("UPDATE " + schemaName + "checklist_group SET");
		query.append("name = :name, ");
		query.append(" WHERE ckg_id = ckgid ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ckgid", model.getCkgId());
		params.addValue("name", model.getName());
		params.addValue("cklid", checklistModel.getId());

		log.trace("[QUERY] checklist_group.save: {} [PARAMS]: {}", query, params.getValues());


		this.getJdbcTemplatePortal().update(query.toString(), params);
		return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar salvar: {}", checklistModel, e);
			throw new AppException("Erro ao tentar salvar.", e);
		}

	}

	@Override
	public List<ChecklistGroupModel> search(ChecklistGroupModel model)
			throws AppException {

		List<ChecklistGroupModel> listReturn = null;

		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append(" cw.ckg_id, ");
			query.append(" cw.name ");

			query.append("FROM " + schemaName + "checklist_group AS cw ");
			query.append("WHERE cw.ckg_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getCkgId() != null && model.getCkgId() > 0) {
					query.append("AND cw.ckg_id = :ckgid ");
					params.addValue("ckgid", model.getCkgId());
				}

				if (model.getName() != null && !model.getName().equals("")) {
					query.append("AND cw.name LIKE :name ");
					params.addValue("name", model.getName() + "%");
				}
			}

			log.trace("[QUERY] checklist_group.search: {} [PARAMS]: {}", query, params.getValues());

			List<ChecklistGroupModel> checklist = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistGroupMapper());
			if (!CollectionUtils.isEmpty(checklist)) {
				listReturn = checklist;
			}

		} catch (Exception e) {
			log.error("Erro ao procurar checklist", e);
			throw new AppException("Erro ao procurar checklist.", e);
		}

		return listReturn;
	}

	@Override
	public List<ChecklistGroupModel> getByChecklistId(Integer id) throws AppException {
		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("cw.ckg_id, ");
			query.append("cw.name ");

			query.append("FROM " + schemaName + "checklist_group AS cw ");
			query.append("WHERE cw.ckl_id = :ckgId ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ckgId", id);

			log.trace("[QUERY] checklist_group.getById: {} [PARAMS] : {}", query, params.getValues());

			return this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistGroupMapper());
		} catch (EmptyResultDataAccessException e) {
			return new ArrayList<>();
		} catch (Exception e) {
			log.trace("Erro ao consultar um checklist.", e);
			throw new AppException("Erro ao consultar um checklist.", e);
		}
	}

}
