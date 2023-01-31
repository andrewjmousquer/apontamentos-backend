package com.portal.dao.impl;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.portal.config.BaseDAO;
import com.portal.dao.IChecklistQuestionDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.ChecklistQuestionMapper;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistQuestionModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ChecklistQuestionDAO extends BaseDAO implements IChecklistQuestionDAO {

	@Override
	public void save(ChecklistGroupModel checklistGroupModel, ChecklistQuestionModel model) throws AppException {

		StringBuilder query = new StringBuilder("");
		query.append("INSERT INTO " + schemaName + "checklist_question (");
		query.append("question, ");
		query.append("creation_date, ");
		query.append("active, ");
		query.append("ckg_id ");
		query.append(") VALUES (");
		query.append(":question,");
		query.append(":creation_date, ");
		query.append(":active, ");
		query.append(":ckg_id ) ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("question", model.getQuestion());
		params.addValue("creation_date", model.getCreationDate());
		params.addValue("active", model.getActive());
		params.addValue("ckg_id", checklistGroupModel.getCkgId());

		log.trace("[QUERY] checklist_question.save: {} [PARAMS]: {}", query, params.getValues());

		try {
			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar salvar: {}", checklistGroupModel, e);
			throw new AppException("Erro ao tentar salvar.", e);
		}

	}

	@Override
	public void delete(Integer checklist) throws AppException {
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM ");
		query.append("checklist_question ");
		query.append("WHERE ckg_id in (select ckg_id from checklist_group where ckl_id = :ChecklistId) ");

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("ChecklistId", checklist);

		log.trace("[QUERY] checklist_group.delete : {} [PARAMS] : {}", query, params.getValues());

		try {

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir: {}", checklist, e);
			throw new AppException("Erro ao tentar excluir.", e);

		}
	}

	@Override
	public void delete(Integer ckgId, Integer qasId) throws AppException {

		try {

			StringBuilder query = new StringBuilder();
			query.append("DELETE FROM ");
			query.append(schemaName + "checklist_question ");
			query.append("WHERE qas_id = :qasid ");
			query.append("AND ckg_id = :ckgid");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("qasid", qasId);
			params.addValue("ckgid", ckgId);

			log.trace("[QUERY] checklist_question.delete : {} [PARAMS] : {}", query, params.getValues());

			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir: {}", qasId, e);
			throw new AppException("Erro ao tentar excluir.", e);

		}
	}

	@Override
	public List<ChecklistQuestionModel> search(ChecklistQuestionModel model) throws AppException {
		List<ChecklistQuestionModel> listReturn = null;

		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append(" cw.qas_id, ");
			query.append("cw.question, ");
			query.append("cw.creation_date, ");
			query.append("cw.active ");

			query.append("FROM " + schemaName + "checklist_question AS cw ");
			query.append("WHERE cw.qas_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND cw.qas_id = :qasId ");
					params.addValue("qasId", model.getId());
				}

				if (model.getQuestion() != null && !model.getQuestion().equals("")) {
					query.append("AND cw.question LIKE :question ");
					params.addValue("question", model.getQuestion() + "%");
				}

				if (model.getCreationDate() != null && !model.getQuestion().equals("")) {
					query.append("AND cw.creation_date LIKE :creation_date ");
					params.addValue("creation_date", model.getQuestion() + "%");
				}

				if (model.getActive() != null && !model.getQuestion().equals("")) {
					query.append("AND cw.active LIKE :active ");
					params.addValue("active", model.getQuestion() + "%");
				}
			}

			log.trace("[QUERY] checklist_question.search: {} [PARAMS]: {}", query, params.getValues());

			List<ChecklistQuestionModel> checklist = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistQuestionMapper());
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
	public List<ChecklistQuestionModel> getChecklistListQuestionByChecklistId(Integer id) throws AppException {
		List<ChecklistQuestionModel> listReturn = null;

		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("cw.qas_id, ");
			query.append("cw.question, ");
			query.append("cw.creation_date, ");
			query.append("cw.active ");

			query.append("FROM " + schemaName + "checklist_question AS cw ");
			query.append("WHERE cw.ckg_id = :qasId ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("qasId", id);

			log.trace("[QUERY] checklist_question.getById: {} [PARAMS] : {}", query, params.getValues());

			List<ChecklistQuestionModel> checklist = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistQuestionMapper());
			if (!CollectionUtils.isEmpty(checklist)) {
				listReturn = checklist;
			}

		} catch (EmptyResultDataAccessException e) {
		} catch (Exception e) {
			log.trace("Erro ao consultar um checklist.", e);
			throw new AppException("Erro ao consultar um checklist.", e);
		}

		return listReturn;

	}
}
