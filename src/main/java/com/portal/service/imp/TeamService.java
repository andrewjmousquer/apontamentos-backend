package com.portal.service.imp;

import java.util.List;
import java.util.Optional;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.dao.ITeamDAO;
import com.portal.dto.UserProfileDTO;
import com.portal.enums.AuditOperationType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.TeamModel;
import com.portal.service.IAuditService;
import com.portal.service.IStageService;
import com.portal.service.IStageTeamService;
import com.portal.service.ITeamService;
import com.portal.service.IUserService;
import com.portal.service.IUserTeamService;
import com.portal.validators.ValidationHelper;
import com.portal.validators.ValidationHelper.OnUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TeamService implements ITeamService {

	@Autowired
	private Validator validator;

	@Autowired
	private ITeamDAO dao;

	@Autowired
	private IAuditService auditService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private IUserTeamService userTeamService;

	@Autowired
	private IStageTeamService stageTeamService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IStageService stageService;

	@Override
	public Optional<TeamModel> getById(Integer id) throws AppException, BusException {
		try {
			if (id == null)
				throw new BusException("ID de busca inválido.");

			Optional<TeamModel> team = this.dao.getById(id);

			if (team.isPresent()) {
				team.get().setUsers(userService.findByTeam(team.get().getId()));
				team.get().setStages(stageService.findByStage(team.get().getId()));
			}

			return team;

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao consultar uma equipe pelo ID: {}", id, e);
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { TeamModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<TeamModel> list() throws AppException, BusException {
		List<TeamModel> teamList = this.dao.list();
		return teamList;
	}

	@Override
	public Optional<TeamModel> find(TeamModel model) throws AppException, BusException {
		try {
			return this.dao.find(model);

		} catch (Exception e) {
			log.error("Erro no processo de buscar as equipes.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.find",
					new Object[] { TeamModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<TeamModel> search(TeamModel model) throws AppException, BusException {
		try {
			return this.dao.search(model);

		} catch (Exception e) {
			log.error("Erro no processo de procurar as equipes.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.search",
					new Object[] { TeamModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<TeamModel> saveOrUpdate(TeamModel model, UserProfileDTO userProfile)
			throws AppException, BusException {
		if (model.getId() != null && model.getId() > 0) {
			return this.update(model, userProfile);
		} else {
			return this.save(model, userProfile);
		}
	}

	@Override
	public Optional<TeamModel> save(TeamModel model, UserProfileDTO userProfile) throws AppException, BusException {
		try {

			Optional<TeamModel> saved = this.dao.save(model);

			if (saved.isPresent()) {
				model.setId(saved.get().getId());
				this.userTeamService.save(saved.get());
				this.stageTeamService.save(saved.get());
			}

			this.audit((saved.isPresent() ? saved.get() : null), AuditOperationType.TEAM_INSERTED, userProfile);

			return this.getById(saved.get().getId());

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro no processo de cadastro da equipe: {}", model, e);
			throw new AppException(this.messageSource.getMessage("error.generic.save",
					new Object[] { TeamModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}

	}

	@Override
	public Optional<TeamModel> update(TeamModel model, UserProfileDTO userProfile) throws AppException, BusException {
		try {
			this.validateEntity(model, OnUpdate.class);

			Optional<TeamModel> updated = this.dao.update(model);

			if (updated.isPresent()) {
				model.setId(updated.get().getId());
				this.userTeamService.save(model);
				this.stageTeamService.save(model);
			}

			this.audit((updated.isPresent() ? updated.get() : null), AuditOperationType.TEAM_UPDATED, userProfile);

			return this.getById(updated.get().getId());

		} catch (BusException e) {
			throw e;
		} catch (Exception e) {
			log.error("Erro no processo de atualização: {}", model, e);
			throw new AppException(this.messageSource.getMessage("error.generic.update",
					new Object[] { TeamModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException {
		try {
			if (id == null)
				throw new BusException("ID de exclusão inválido");

			Optional<TeamModel> entityDB = this.getById(id);
			if (!entityDB.isPresent()) {
				throw new BusException("Equipe a ser excluído não existe.");
			}

			this.userTeamService.delete(entityDB.get());
			this.stageTeamService.delete(entityDB.get());
			this.dao.delete(id);

			this.audit((entityDB.isPresent() ? entityDB.get() : null), AuditOperationType.TEAM_DELETED, userProfile);

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro no processo de exclusão da equipe.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.delete",
					new Object[] { TeamModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}

	}

	private void validateEntity(TeamModel model, Class<?> group) throws AppException, BusException {
		ValidationHelper.generateException(validator.validate(model, group));
	}

	@Override
	public void audit(TeamModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {
		try {
			this.auditService.save(objectMapper.writeValueAsString(model), operationType, userProfile);
		} catch (JsonProcessingException e) {
			throw new AppException(this.messageSource.getMessage("error.audit", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<TeamModel> getAllTeamByUser(Integer id) throws AppException {
		return this.dao.getAllTeamByUser(id);
	}
}
