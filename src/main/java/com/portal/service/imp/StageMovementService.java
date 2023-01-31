package com.portal.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.portal.dao.IStageMovementDAO;
import com.portal.dto.UserProfileDTO;
import com.portal.enums.AuditOperationType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.StageMovementModel;
import com.portal.service.IAuditService;
import com.portal.service.IStageMovementService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StageMovementService implements IStageMovementService {

	@Autowired
	private IStageMovementDAO dao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IAuditService auditService;

	@Override
	public Optional<StageMovementModel> getById(Integer id) throws AppException, BusException {

		Optional<StageMovementModel> finded = this.dao.getById(id);

		if (finded.isEmpty())
			throw new BusException(
					this.messageSource.getMessage("error.generic.getbyid", null, LocaleContextHolder.getLocale()));

		return finded;
	}

	@Override
	public Optional<StageMovementModel> find(StageMovementModel model) throws AppException {
		return this.dao.find(model);
	}

	@Override
	public List<StageMovementModel> search(StageMovementModel model) throws AppException {
		return this.dao.search(model);
	}

	@Override
	public List<StageMovementModel> list() throws AppException, BusException {
		return this.dao.list();
	}

	@Override
	public Optional<StageMovementModel> saveOrUpdate(StageMovementModel parameter, UserProfileDTO userProfile)
			throws AppException, BusException {
		if (parameter != null && (parameter.getId() == null || parameter.getId().equals(0))) {
			return this.save(parameter, userProfile);
		} else {
			return this.update(parameter, userProfile);
		}
	}

	@Override
	public Optional<StageMovementModel> save(StageMovementModel parameter, UserProfileDTO userProfile)
			throws AppException, BusException {

		Optional<StageMovementModel> StageMovementModel = this.dao.save(parameter);
		this.audit(StageMovementModel.get(), AuditOperationType.STAGE_MOVEMENT_INSERTED, userProfile);
		return StageMovementModel;
	}

	@Override
	public Optional<StageMovementModel> update(StageMovementModel parameter, UserProfileDTO userProfile)
			throws AppException, BusException {
		this.audit(parameter, AuditOperationType.STAGE_MOVEMENT_UPDATED, userProfile);
		return this.dao.update(parameter);
	}

	@Override
	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException {
		Optional<StageMovementModel> StageMovementModel = this.getById(id);
		if (StageMovementModel.isPresent()) {
			this.audit(StageMovementModel.get(), AuditOperationType.STAGE_MOVEMENT_DELETED, userProfile);
			this.dao.delete(id);
		}
	}

	@Override
	public void audit(StageMovementModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {
		String details = String.format("id:%s;name:%s;icon:%s;type:%s;stage:%s;jiraID:%s", model.getId(),
				model.getName(), model.getIcon(), model.getType().getId(),
				model.getStage() != null ? model.getStage().getId() : null, model.getJiraID());
		this.auditService.save(details, operationType, userProfile);
	}

	private void validateDuplicateParameter(StageMovementModel parameter) throws AppException, BusException {
		if (parameter != null) {
			Optional<StageMovementModel> foundParam = this.dao.find(parameter);
			if (foundParam.isPresent()) {
				throw new BusException(this.messageSource.getMessage("error.parameter.duplicate", null,
						LocaleContextHolder.getLocale()));
			}
		}
	}

}
