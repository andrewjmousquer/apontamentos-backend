package com.portal.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.portal.config.BaseDAO;
import com.portal.dao.IChecklistAnswerDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.ChecklistAnswerByQuestionMapper;
import com.portal.mapper.ChecklistAnswerMapper;
import com.portal.model.ChecklistAnswerModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ChecklistAnswerDAO extends BaseDAO implements IChecklistAnswerDAO {

	@Override
	public Optional<ChecklistAnswerModel> find(ChecklistAnswerModel model) throws AppException {
		Optional<ChecklistAnswerModel> objReturn = Optional.empty();

		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ca.cqa_id ");
			query.append(",ca.creation_date ");
			query.append(",ca.qas_id ");
			query.append(",ca.comment ");
			query.append(",ca.tsk_id ");
			query.append(",ca.responsible_for_answer ");
			query.append(",ca.answer_cla ");

			query.append(",cla.cla_id as cla_id ");
			query.append(",cla.value as cla_value ");
			query.append(",cla.type as cla_type ");

			query.append(",cq.qas_id as qst_qas_id ");
			query.append(",cq.question as qst_question ");
			query.append(",cq.creation_date as qst_creation_date ");
			query.append(",cq.active as qst_active ");
			query.append(",cq.ckg_id as qst_ckg_id ");

			query.append("FROM " + schemaName + "checklist_answer AS ca ");
			query.append("INNER JOIN " + schemaName + "checklist_question AS cq ON cq.qas_id = ca.qas_id ");
			query.append("INNER JOIN " + schemaName + "classifier AS cla ON cla.cla_id = ca.answer_cla ");
			query.append("WHERE ca.cqa_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND qas_id=:id ");
					params.addValue("id", model.getId());
				}
				if (model.getQuestion() != null && !model.getQuestion().equals("")) {
					query.append("AND question=:question ");
					params.addValue("question", model.getQuestion() + "%");
				}

			}

			log.trace("[QUERY] checklist_group.find {} [PARAMS] : {}", query, params.getValues());

			List<ChecklistAnswerModel> services = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistAnswerMapper());
			if (!CollectionUtils.isEmpty(services)) {
				objReturn = Optional.ofNullable(services.get(0));
			}

		} catch (Exception e) {
			log.error("Erro ao buscar checklist.", e);
			throw new AppException("Erro ao buscar checklist.", e);
		}

		return objReturn;
	}

	@Override
	public Optional<ChecklistAnswerModel> getById(Integer id) throws AppException {
		Optional<ChecklistAnswerModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ca.cqa_id ");
			query.append(",ca.creation_date ");
			query.append(",ca.qas_id ");
			query.append(",ca.comment ");
			query.append(",ca.tsk_id ");
			query.append(",ca.responsible_for_answer ");
			query.append(",ca.answer_cla ");

			query.append(",cla.cla_id as cla_id ");
			query.append(",cla.value as cla_value ");
			query.append(",cla.type as cla_type ");

			query.append(",cq.qas_id as qst_qas_id ");
			query.append(",cq.question as qst_question ");
			query.append(",cq.creation_date as qst_creation_date ");
			query.append(",cq.active as qst_active ");
			query.append(",cq.ckg_id as qst_ckg_id ");

			query.append("FROM " + schemaName + "checklist_answer AS ca ");
			query.append("INNER JOIN " + schemaName + "checklist_question AS cq ON cq.qas_id = ca.qas_id ");
			query.append("INNER JOIN " + schemaName + "classifier AS cla ON cla.cla_id = ca.answer_cla ");
			query.append("WHERE ca.cqa_id = :id ");
			query.append("LIMIT 1");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] check_list.getById: {} [PARAMS] : {}", query, params.getValues());

			List<ChecklistAnswerModel> service = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistAnswerMapper());
			if (!CollectionUtils.isEmpty(service)) {
				objReturn = Optional.ofNullable(service.get(0));
			}

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (Exception e) {
			log.trace("Erro ao consultar checklist.", e);
			throw new AppException("Erro ao consultar checklist.", e);
		}

		return objReturn;
	}

	@Override
	public List<ChecklistAnswerModel> list() throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ca.cqa_id ");
			query.append(",ca.creation_date ");
			query.append(",ca.qas_id ");
			query.append(",ca.comment ");
			query.append(",ca.tsk_id ");
			query.append(",ca.responsible_for_answer ");
			query.append(",ca.answer_cla ");

			query.append(",cla.cla_id as cla_id ");
			query.append(",cla.value as cla_value ");
			query.append(",cla.type as cla_type ");

			query.append(",cq.qas_id as qst_qas_id ");
			query.append(",cq.question as qst_question ");
			query.append(",cq.creation_date as qst_creation_date ");
			query.append(",cq.active as qst_active ");
			query.append(",cq.ckg_id as qst_ckg_id ");

			query.append("FROM " + schemaName + "checklist_answer AS ca ");
			query.append("INNER JOIN " + schemaName + "checklist_question AS cq ON cq.qas_id = ca.qas_id ");
			query.append("INNER JOIN " + schemaName + "classifier AS cla ON cla.cla_id = ca.answer_cla ");
			query.append("WHERE ca.cqa_id > 0 ");
			query.append("ORDER BY ca.cqa_id");

			log.trace("[QUERY] checklist_question.list: {} [PARAMS]: {}", query);
			return this.getJdbcTemplate().query(query.toString(), new ChecklistAnswerMapper());
		} catch (Exception e) {
			log.error("Erro ao tentar listar checklist", e);
			throw new AppException("Erro ao listar checklist", e);
		}
	}

	@Override
	public List<ChecklistAnswerModel> search(ChecklistAnswerModel model) throws AppException {
		List<ChecklistAnswerModel> objReturn = null;
		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ca.cqa_id ");
			query.append(",ca.creation_date ");
			query.append(",ca.qas_id ");
			query.append(",ca.comment ");
			query.append(",ca.tsk_id ");
			query.append(",ca.responsible_for_answer ");
			query.append(",ca.answer_cla ");

			query.append(",cla.cla_id as cla_id ");
			query.append(",cla.value as cla_value ");
			query.append(",cla.type as cla_type ");

			query.append(",cq.qas_id as qst_qas_id ");
			query.append(",cq.question as qst_question ");
			query.append(",cq.creation_date as qst_creation_date ");
			query.append(",cq.active as qst_active ");
			query.append(",cq.ckg_id as qst_ckg_id ");

			query.append("FROM " + schemaName + "checklist_answer AS ca ");
			query.append("INNER JOIN " + schemaName + "checklist_question AS cq ON cq.qas_id = ca.qas_id ");
			query.append("INNER JOIN " + schemaName + "classifier AS cla ON cla.cla_id = ca.answer_cla ");
			query.append("WHERE ca.cqa_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND qas_id=:id ");
					params.addValue("id", model.getId());
				}
				if (model.getQuestion() != null && !model.getQuestion().equals("")) {
					query.append("AND question=:question ");
					params.addValue("question", model.getQuestion() + "%");
				}

				if (model.getTask() != null && model.getTask().getId() != null) {
					query.append("AND ca.tsk_id=:taskID ");
					params.addValue("taskID", model.getTask().getId());
				}
			}
			query.append("ORDER BY ca.cqa_id ");

			log.trace("[QUERY] checklist_question.search: {} [PARAMS]: {}", query, params.getValues());

			List<ChecklistAnswerModel> checklist = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistAnswerMapper());
			if (!CollectionUtils.isEmpty(checklist)) {
				objReturn = checklist;
			}

		} catch (Exception e) {
			log.error("Erro ao procurar Resposta", e);
			throw new AppException("Erro ao procurar Resposta", e);
		}

		return objReturn;
	}

	@Override
	public Optional<ChecklistAnswerModel> save(ChecklistAnswerModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder("");

			query.append("INSERT INTO " + schemaName
					+ "checklist_answer (`creation_date`, `qas_id`, `comment`, `tsk_id`, `responsible_for_answer`, `answer_cla`) ");
			query.append(" VALUES (:creationDate,:questionID, :comment, :taskID , :responsibleForAnswer, :answerCLA) ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("questionID", model.getQuestion().getId());
			params.addValue("creationDate", model.getCreationDate());
			params.addValue("comment", model.getComment());
			params.addValue("taskID", model.getTask().getId());
			params.addValue("responsibleForAnswer", model.getResponsibleForAnswer());
			params.addValue("answerCLA", model.getAnswer().getId());

			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.getJdbcTemplatePortal().update(query.toString(), params, keyHolder);
			model.setId(this.getKey(keyHolder));

			return Optional.ofNullable(model);
		} catch (Exception e) {
			log.error("Erro ao tentar salvar checklist: {}", model, e);
			throw new AppException("Erro ao tentar salvar checklist.", e);
		}
	}

	@Override
	public Optional<ChecklistAnswerModel> update(ChecklistAnswerModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("UPDATE checklist_answer SET ");
			query.append("comment = :comment");
			query.append(",responsible_for_answer = :responsibleForAnswer");
			query.append(",answer_cla = :answer");
			query.append(" WHERE cqa_id = :id ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			params.addValue("comment", model.getComment());
			params.addValue("responsibleForAnswer", model.getResponsibleForAnswer());
			params.addValue("answer", model.getAnswer().getId());
			params.addValue("id", model.getId());

			log.trace("[QUERY] checklist_question.update: {} [PARAMS]: {}", query, params.getValues());

			this.getJdbcTemplatePortal().update(query.toString(), params);
			return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar atualizar checklist : {}", model, e);
			throw new AppException("Erro ao tentar atualizar checklist.", e);
		}
	}

	@Override
	public void delete(Integer id) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("DELETE ");
			query.append("FROM checklist_answer ");
			query.append("WHERE cqa_id = :id ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] checklist_question.delete : {} [PARAMS] : {}", query, params.getValues());
			this.getJdbcTemplatePortal().update(query.toString(), params);

		} catch (Exception e) {
			log.error("Erro ao tentar excluir.", e);
			throw new AppException("Erro ao excluir.", e);
		}
	}

	@Override
	public List<ChecklistAnswerModel> getByCheckListId(Integer id) throws AppException {
		List<ChecklistAnswerModel> objReturn = null;
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ca.cqa_id ");
			query.append(",ca.creation_date ");
			query.append(",ca.qas_id ");
			query.append(",cq.qas_id as qst_qas_id ");
			query.append(",cq.question as qst_question ");
			query.append(",cq.creation_date as qst_creation_date ");
			query.append(",cq.active as qst_active ");
			query.append(",cq.ckg_id as qst_ckg_id ");
			query.append(",ca.comment  as comment ");
			query.append(",ca.tsk_id as tsk_id ");
			query.append(",ca.responsible_for_answer as responsible_for_answer ");
			query.append(",ca.answer_cla ");
			query.append("FROM " + schemaName + "checklist_answer AS ca ");
			query.append("INNER JOIN " + schemaName + "checklist_question AS cq ON cq.qas_id = ca.qas_id ");
			query.append("INNER JOIN " + schemaName + "checklist_group AS cg ON cg.ckg_id = cq.ckg_id ");
			query.append("INNER JOIN " + schemaName + "check_list AS ck ON ck.ckl_id = cg.ckl_id ");
			query.append("WHERE ck.ckl_id = :id ");
			query.append("ORDER BY ca.cqa_id");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] checklist_question.getByCheckListId: {} [PARAMS]: {}", query, params);

			List<ChecklistAnswerModel> checklistAwn = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistAnswerByQuestionMapper());
			if (!CollectionUtils.isEmpty(checklistAwn)) {
				objReturn = checklistAwn;
			}
		} catch (Exception e) {
			log.error("Erro ao tentar listar checklist", e);
			throw new AppException("Erro ao listar checklist", e);
		}

		return objReturn;
	}

}
