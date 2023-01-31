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
import com.portal.dao.IChecklistAnswerPhotoDAO;
import com.portal.exceptions.AppException;
import com.portal.mapper.ChecklistAnswerPhotoMapper;
import com.portal.model.ChecklistAnswerPhotoModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ChecklistAnswerPhotoDAO extends BaseDAO implements IChecklistAnswerPhotoDAO {

	@Override
	public Optional<ChecklistAnswerPhotoModel> find(ChecklistAnswerPhotoModel model) throws AppException {
		Optional<ChecklistAnswerPhotoModel> objReturn = Optional.empty();

		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append(" cap.qap_id");
			query.append(",cap.file_name ");
			query.append(",cap.creation_date ");
			query.append("FROM " + schemaName + "checklist_answer_photo AS cap ");
			query.append("WHERE cap.qap_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND qap_id=:id ");
					params.addValue("id", model.getId());
				}
				if (model.getFileName() != null && !model.getFileName().equals("")) {
					query.append("AND file_name=:fileName ");
					params.addValue("fileName", mapLike(model.getFileName()));
				}
				if (model.getAnswer() != null && model.getAnswer().getId() != null && model.getAnswer().getId() > 0) {
					query.append("AND cqa_id=:answer ");
					params.addValue("answer", model.getAnswer().getId());
				}
			}

			log.trace("[QUERY] checklist_group.find {} [PARAMS] : {}", query, params.getValues());

			List<ChecklistAnswerPhotoModel> services = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistAnswerPhotoMapper());
			if (!CollectionUtils.isEmpty(services)) {
				objReturn = Optional.ofNullable(services.get(0));
			}

		} catch (Exception e) {
			log.error("Erro ao buscar foto.", e);
			throw new AppException("Erro ao buscar foto.", e);
		}

		return objReturn;
	}

	@Override
	public Optional<ChecklistAnswerPhotoModel> getById(Integer id) throws AppException {
		Optional<ChecklistAnswerPhotoModel> objReturn = Optional.empty();
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append(" cap.qap_id");
			query.append(",cap.file_name ");
			query.append(",cap.creation_date ");
			query.append("FROM " + schemaName + "checklist_answer_photo AS cap ");
			query.append("WHERE cap.qap_id = :id");
			query.append("LIMIT 1");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);

			log.trace("[QUERY] check_list.getById: {} [PARAMS] : {}", query, params.getValues());

			List<ChecklistAnswerPhotoModel> service = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistAnswerPhotoMapper());
			if (!CollectionUtils.isEmpty(service)) {
				objReturn = Optional.ofNullable(service.get(0));
			}

		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		} catch (Exception e) {
			log.trace("Erro ao consultar foto.", e);
			throw new AppException("Erro ao consultar foto.", e);
		}

		return objReturn;
	}

	@Override
	public List<ChecklistAnswerPhotoModel> list() throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT ");
			query.append(" cap.qap_id");
			query.append(",cap.file_name ");
			query.append(",cap.creation_date ");
			query.append("FROM " + schemaName + "checklist_answer_photo AS cap ");
			query.append("ORDER BY cap.qap_id");

			log.trace("[QUERY] checklist_question.list: {} [PARAMS]: {}", query);
			return this.getJdbcTemplate().query(query.toString(), new ChecklistAnswerPhotoMapper());
		} catch (Exception e) {
			log.error("Erro ao tentar listar foto", e);
			throw new AppException("Erro ao listar foto", e);
		}
	}

	@Override
	public List<ChecklistAnswerPhotoModel> search(ChecklistAnswerPhotoModel model) throws AppException {
		List<ChecklistAnswerPhotoModel> objReturn = new ArrayList<>();
		try {

			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append(" cap.qap_id");
			query.append(",cap.file_name ");
			query.append(",cap.creation_date ");
			query.append("FROM " + schemaName + "checklist_answer_photo AS cap ");
			query.append("WHERE cap.qap_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (model != null) {
				if (model.getId() != null && model.getId() > 0) {
					query.append("AND qap_id=:id ");
					params.addValue("id", model.getId());
				}
				if (model.getFileName() != null && !model.getFileName().equals("")) {
					query.append("AND file_name=:fileName ");
					params.addValue("fileName", mapLike(model.getFileName()));
				}
				if (model.getAnswer() != null && model.getAnswer().getId() != null && model.getAnswer().getId() > 0) {
					query.append("AND cqa_id=:answer ");
					params.addValue("answer", model.getAnswer().getId());
				}
			}

			log.trace("[QUERY] checklist_question.search: {} [PARAMS]: {}", query, params.getValues());

			List<ChecklistAnswerPhotoModel> checklist = this.getJdbcTemplatePortal().query(query.toString(), params,
					new ChecklistAnswerPhotoMapper());
			if (!CollectionUtils.isEmpty(checklist)) {
				objReturn = checklist;
			}

		} catch (Exception e) {
			log.error("Erro ao procurar foto", e);
			throw new AppException("Erro ao procurar foto", e);
		}

		return objReturn;
	}

	@Override
	public Optional<ChecklistAnswerPhotoModel> save(ChecklistAnswerPhotoModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder("");

			query.append("INSERT INTO " + schemaName + "checklist_answer_photo ( file_name, creation_date,cqa_id ) ");
			query.append(" VALUES (:fileName,:creationDate,:questionID) ");

			MapSqlParameterSource params = new MapSqlParameterSource();

			params.addValue("fileName", model.getFileName());
			params.addValue("creationDate", model.getCreationDate());
			params.addValue("questionID", model.getAnswer().getId());

			KeyHolder keyHolder = new GeneratedKeyHolder();
			this.getJdbcTemplatePortal().update(query.toString(), params, keyHolder);
			model.setId(this.getKey(keyHolder));

			return Optional.ofNullable(model);
		} catch (Exception e) {
			log.error("Erro ao tentar salvar Foto do Checklist: {}", model, e);
			throw new AppException("Erro ao tentar salvar Foto do Checklist.", e);
		}
	}

	@Override
	public Optional<ChecklistAnswerPhotoModel> update(ChecklistAnswerPhotoModel model) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("UPDATE checklist_answer_photo ");
			query.append("SET file_name=:fileName ");
			query.append(",creation_date=:creationDate ");
			query.append(",cqa_id=:questionID ");
			query.append("WHERE qas_id = :ID ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ID", model.getId());
			params.addValue("fileName", model.getFileName());
			params.addValue("creationDate", model.getCreationDate());
			params.addValue("questionID", model.getAnswer().getId());

			log.trace("[QUERY] checklist_question.update: {} [PARAMS]: {}", query, params.getValues());

			this.getJdbcTemplatePortal().update(query.toString(), params);
			return Optional.ofNullable(model);

		} catch (Exception e) {
			log.error("Erro ao tentar atualizar foto : {}", model, e);
			throw new AppException("Erro ao tentar atualizar foto.", e);
		}
	}

	@Override
	public void delete(Integer id) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("DELETE ");
			query.append("FROM checklist_answer_photo ");
			query.append("WHERE qap_id = :id ");

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
	public void deleteByAnswer(Integer id) throws AppException {
		try {
			StringBuilder query = new StringBuilder();
			query.append("DELETE ");
			query.append("FROM checklist_answer_photo ");
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

}
