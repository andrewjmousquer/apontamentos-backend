package com.portal.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.portal.config.BaseDAO;
import com.portal.dao.IChecklistDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.ChecklistMapper;
import com.portal.model.ChecklistModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ChecklistDAO extends BaseDAO implements IChecklistDAO {

	@Override
	public Optional<ChecklistModel> find(ChecklistModel model) throws AppException {
		return search(model).stream().findFirst();
	

	}

	@Override
	public Optional<ChecklistModel> getById(Integer id) throws AppException {

			
			ChecklistModel search = new ChecklistModel();
			search.setId(id);
			List<ChecklistModel> returnList = search(search);
			return returnList.stream().findFirst();

	}

	@Override
	public List<ChecklistModel> list() throws AppException {
        return search(null);

	}
 
	@Override
	public List<ChecklistModel> search(ChecklistModel model) throws AppException {
		try {

			StringBuilder query = new StringBuilder();

			query.append(" SELECT cw.ckl_id, cw.name, cw.description, cw.priority_order, cw.tag_id,");
			query.append("  COUNT(distinct cg.ckg_id) AS numberOfGroups,");
			query.append("  COUNT(distinct cq.qas_id) AS numberOfQuestions");
			query.append(" FROM check_list AS cw");
			query.append(" LEFT JOIN checklist_group AS cg ON cg.ckl_id = cw.ckl_id");
			query.append(" LEFT JOIN checklist_question cq ON cq.ckg_id = cg.ckg_id");
			query.append(" WHERE cw.ckl_id > 0 ");
			

			MapSqlParameterSource params = new MapSqlParameterSource();	
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND cw.ckl_id = :cklid ");
					params.addValue("cklid", model.getId());
				}

				if (model.getName() != null && !model.getName().equals("")) {
					query.append("AND cw.name LIKE :name ");
					params.addValue("name", mapLike(model.getName()));
				}

				if (model.getDescrition() != null && !model.getDescrition().equals("")) {
					query.append("AND cw.description LIKE :description ");
					params.addValue("description", mapLike(model.getDescrition()));
				}
				if (model.getPriorityOrder() != null && !model.getPriorityOrder().equals("")) {
					query.append("AND cw.priority_order LIKE :priority_order ");
					params.addValue("priority_order", model.getPriorityOrder());
				}

			}
			
			query.append(" GROUP BY ckl_id, name, description, priority_order, tag_id");
			query.append(" ORDER BY cw.ckl_id;");

			log.trace("[QUERY] check_list.search: {} [PARAMS]: {}", query, params.getValues());

			return this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistMapper());

		} catch (Exception e) {
			log.error("Erro ao procurar checklist", e);
			throw new AppException("Erro ao procurar checklist.", e);
		}
	}

	@Override
	public Optional<ChecklistModel> save(ChecklistModel model) throws AppException {
		try {

			StringBuilder query = new StringBuilder();

			query.append("INSERT INTO " + schemaName + "check_list (");
			query.append("name, ");
			query.append("description, ");
			query.append("priority_order, ");
			query.append("tag_id ");
			query.append(") VALUES (");
			query.append(":name, ");
			query.append(":description, ");
			query.append(":priority_order, ");
			query.append(":tag_id ");
			query.append(")");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("name", model.getName());
			params.addValue("description", model.getDescrition());
			params.addValue("priority_order", model.getPriorityOrder());
			params.addValue("tag_id", model.getTag());

			log.trace("[QUERY] check_list.save: {} [PARAMS]: {}", query, params.getValues());

			KeyHolder keyHolder = new GeneratedKeyHolder();

			this.getJdbcTemplatePortal().update(query.toString(), params, keyHolder);

			model.setId(this.getKey(keyHolder));

			return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar salvar um checklist: {}", model, e);
			throw new AppException("Erro ao tentar salvar um checklist.", e);
		}

	}

	@Override
	public Optional<ChecklistModel> update(ChecklistModel model) throws AppException {

		try {

			StringBuilder query = new StringBuilder();
			query.append("UPDATE " + schemaName + "check_list SET ");
			query.append("name = :name, ");
			query.append("description = :description, ");
			query.append("priority_order = :priority_order, ");
			query.append("tag_id = :tag_id ");
			query.append("WHERE ckl_id = :cklid ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("cklid", model.getId());
			params.addValue("name", model.getName());
			params.addValue("description", model.getDescrition());
			params.addValue("priority_order", model.getPriorityOrder());
			params.addValue("tag_id", model.getTag());

			log.trace("[QUERY] check_list.update: {} [PARAMS]: {}", query, params.getValues());

			this.getJdbcTemplatePortal().update(query.toString(), params);
			return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar atualizar um checklist : {}", model, e);
			throw new AppException("Erro ao tentar atualizar um checklist.", e);
		}
	}

	@Override
	public void delete(Integer id) throws AppException {

		try {

			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM " + schemaName + "check_list WHERE ckl_id = :cklid ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("cklid", id);

			log.trace("[QUERY] check_list.delete : {} [PARAMS] : {}", query, params.getValues());
			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir.", e);
			throw new AppException("Erro ao excluir.", e);
		}

	}

}
