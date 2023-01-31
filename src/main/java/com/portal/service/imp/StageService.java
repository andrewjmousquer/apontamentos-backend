package com.portal.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.portal.dao.IStageDAO;
import com.portal.dto.UserProfileDTO;
import com.portal.enums.AuditOperationType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.StageModel;
import com.portal.model.StageMovementModel;
import com.portal.service.IAuditService;
import com.portal.service.IStageMovementService;
import com.portal.service.IStageService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StageService implements IStageService {

	@Autowired
	private IStageDAO dao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IAuditService auditService;

	@Autowired
	private IStageMovementService movementService;

	@Override
	public Optional<StageModel> getById(Integer id) throws AppException, BusException {

		Optional<StageModel> finded = this.dao.getById(id);

		if (finded.isEmpty())
			throw new BusException(
					this.messageSource.getMessage("error.generic.getbyid", null, LocaleContextHolder.getLocale()));

		finded.get().setMoviments(movementService.search(new StageMovementModel(finded.get())));

		return finded;
	}

	@Override
	public Optional<StageModel> find(StageModel model) throws AppException {
		return this.dao.find(model);
	}

	@Override
	public List<StageModel> search(StageModel model) throws AppException {
		return this.dao.search(model);
	}

	@Override
	public List<StageModel> list() throws AppException, BusException {
		return this.dao.list();
	}

	@Override
	public Optional<StageModel> saveOrUpdate(StageModel model, UserProfileDTO userProfile)
			throws AppException, BusException {
		if (model != null && (model.getId() == null || model.getId().equals(0))) {
			return this.save(model, userProfile);
		} else {
			return this.update(model, userProfile);
		}
	}

	@Override
	public Optional<StageModel> save(StageModel model, UserProfileDTO userProfile) throws AppException, BusException {
		this.validateDuplicatemodel(model);
		Optional<StageModel> saved = this.dao.save(model);

		if (model.getMoviments() != null && model.getMoviments().size() > 0) {
			for (StageMovementModel item : model.getMoviments()) {
				item.setStage(saved.get());
				this.movementService.saveOrUpdate(item, userProfile);
			}
		}

		this.audit(saved.get(), AuditOperationType.STAGE_INSERTED, userProfile);
		return getById(saved.get().getId());
	}

	@Override
	public Optional<StageModel> update(StageModel model, UserProfileDTO userProfile) throws AppException, BusException {

		Optional<StageModel> saved = this.dao.update(model);

		if (model.getMoviments() != null && model.getMoviments().size() > 0) {
			for (StageMovementModel item : model.getMoviments()) {
				item.setStage(saved.get());
				this.movementService.saveOrUpdate(item, userProfile);
			}
		}

		this.audit(model, AuditOperationType.STAGE_UPDATED, userProfile);

		return saved;
	}

	@Override
	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException {
		Optional<StageModel> StageModel = this.getById(id);
		if (StageModel.isPresent()) {
			this.audit(StageModel.get(), AuditOperationType.STAGE_DELETED, userProfile);
			this.dao.delete(id);
		}
	}

	@Override
	public void audit(StageModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {
		String details = String.format("id:%s;name:%s;special:%s;task:%s;jiraID:%s;checkpoint:%s", model.getId(),
				model.getName(), model.getSpecial(), model.getTask(), model.getStatusJiraID(),
				model.getCheckpoint() != null ? model.getCheckpoint().getId() : 0);
		this.auditService.save(details, operationType, userProfile);
	}

	private void validateDuplicatemodel(StageModel model) throws AppException, BusException {
		if (model != null) {
			Optional<StageModel> foundParam = this.dao.find(model);
			if (foundParam.isPresent()) {
				throw new BusException(this.messageSource.getMessage("error.generic.duplicate",
						new Object[] { "Etapa" }, LocaleContextHolder.getLocale()));
			}
		}
	}

	@Override
	public void deleteMovement(Integer id, UserProfileDTO userProfile) throws AppException, BusException {
		this.movementService.delete(id, userProfile);

	}

	@Override
	public List<StageModel> findByStage(Integer id) throws AppException, BusException {
		return this.dao.findByStage(id);
	}

}
