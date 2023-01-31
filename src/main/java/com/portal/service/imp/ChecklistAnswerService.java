package com.portal.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.portal.dao.IChecklistAnswerDAO;
import com.portal.dto.ChecklistAnswerDTO;
import com.portal.dto.ChecklistAnswerPhotoDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.dto.request.InsertOrEditChecklistAnswerDTO;
import com.portal.dto.request.SearchChecklistAnswerDTO;
import com.portal.enums.AuditOperationType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.ChecklistAnswerFactory;
import com.portal.model.ChecklistAnswerModel;
import com.portal.model.ChecklistAnswerPhotoModel;
import com.portal.service.IAuditService;
import com.portal.service.IChecklistAnswerPhotoService;
import com.portal.service.IChecklistAnswerService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChecklistAnswerService implements IChecklistAnswerService {

	@Autowired
	private IChecklistAnswerDAO dao;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IAuditService auditService;

	@Autowired
	private ChecklistAnswerFactory factory;

	@Autowired
	private IChecklistAnswerPhotoService photoService;

	@Override
	public Optional<ChecklistAnswerDTO> find(SearchChecklistAnswerDTO search) throws AppException, BusException {

		Optional<ChecklistAnswerModel> finded = this.dao.find(factory.convertFromFilterDto(search));

		return Optional.of(this.factory.convertFromModel(finded.get()));
	}

	@Override
	public Optional<ChecklistAnswerDTO> getById(Integer id) throws AppException, BusException {

		Optional<ChecklistAnswerModel> finded = this.dao.getById(id);

		if (finded.isPresent()) {

			List<ChecklistAnswerPhotoDTO> photos = photoService.listPhotosByAnswer(id);

			ChecklistAnswerDTO dtoToReturn = this.factory.convertFromModel(finded.get());

			dtoToReturn.setPhotos(photos);

			return Optional.of(dtoToReturn);
		}

		throw new AppException("Nenhuma resposta encontrada com o identificador informado!");

	}

	@Override
	public List<ChecklistAnswerDTO> list() throws AppException, BusException {

		List<ChecklistAnswerModel> finded = this.dao.list();

		return factory.convertFromListOfModels(finded);
	}

	@Override
	public List<ChecklistAnswerDTO> search(SearchChecklistAnswerDTO search) throws AppException, BusException {

		List<ChecklistAnswerModel> finded = this.dao.search(factory.convertFromFilterDto(search));

		return factory.convertFromListOfModels(finded);
	}

	@Override
	public List<ChecklistAnswerDTO> searchWithPhotos(ChecklistAnswerModel search) throws AppException, BusException {

		List<ChecklistAnswerModel> finded = this.dao.search(search);

		if (finded != null) {
			for (ChecklistAnswerModel checklistAnswerModel : finded) {
				List<ChecklistAnswerPhotoModel> photos = this.photoService
						.search(new ChecklistAnswerPhotoModel(checklistAnswerModel.getId()));
				checklistAnswerModel.setPhotos(photos);
			}

		}

		return factory.convertFromListOfModels(finded);
	}

	@Override
	public Optional<ChecklistAnswerDTO> saveOrUpdate(InsertOrEditChecklistAnswerDTO insertDTO,
			UserProfileDTO userProfile) throws AppException, BusException {

		// TODO Validar se é o unico que esta presente na task para responder

		if (insertDTO != null && (insertDTO.getId() == null || insertDTO.getId().equals(0))) {
			return this.save(insertDTO, userProfile);
		} else {
			return this.update(insertDTO, userProfile);
		}

	}

	@Override
	public Optional<ChecklistAnswerDTO> save(InsertOrEditChecklistAnswerDTO insertOrEditDTO, UserProfileDTO userProfile)
			throws AppException, BusException {

		Optional<ChecklistAnswerModel> saved = this.dao
				.save(factory.convertFromInsertDto(insertOrEditDTO, userProfile));

		this.audit(saved.get(), AuditOperationType.CHECK_LIST_ANSWER_INSERTED, userProfile);

		if (saved.isPresent()) {
			if (insertOrEditDTO.getFiles() != null && insertOrEditDTO.getFiles().size() > 0) {
				this.photoService.savePhotos(saved.get(), insertOrEditDTO.getFiles());
			}
		}

		return getById(saved.get().getId());
	}

	@Override
	public Optional<ChecklistAnswerDTO> update(InsertOrEditChecklistAnswerDTO insertOrEditDTO,
			UserProfileDTO userProfile) throws AppException, BusException {

		Optional<ChecklistAnswerModel> savedToUpdate = this.dao.getById(insertOrEditDTO.getId());

		if (savedToUpdate.isEmpty())
			throw new AppException("Nenhuma resposta encontrada com o identificador informado!");

		ChecklistAnswerModel toSave = factory.convertFromInsertDto(insertOrEditDTO, userProfile);

		Optional<ChecklistAnswerModel> saved = this.dao.update(toSave);

		this.audit(saved.get(), AuditOperationType.CHECK_LIST_ANSWER_UPDATED, userProfile);

		if (saved.isPresent()) {
			if (insertOrEditDTO.getFiles() != null && insertOrEditDTO.getFiles().size() > 0) {
				this.photoService.savePhotos(saved.get(), insertOrEditDTO.getFiles());
			}
		}

		return getById(saved.get().getId());
	}

	@Override
	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException {

		Optional<ChecklistAnswerModel> finded = this.dao.getById(id);

		if (finded.isPresent()) {
			this.audit(finded.get(), AuditOperationType.CHECK_LIST_ANSWER_DELETED, userProfile);
			this.dao.delete(id);
		}

	}

	@Override
	public List<ChecklistAnswerModel> getByCheckListId(Integer id) throws AppException {
		return this.dao.getByCheckListId(id);
	}

	@Override
	public void audit(ChecklistAnswerModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {

		String details = String.format("id:%s;answer:%s;comment:%s;creationDate:%s;responsible:%s;task:%s;question:%s",
				model.getId(), model.getAnswer() != null ? model.getAnswer().getId() : 0, model.getComment(),
				model.getCreationDate(), model.getResponsibleForAnswer(),
				model.getTask() != null ? model.getTask().getId() : 0,
				model.getQuestion() != null ? model.getQuestion().getId() : 0);
		this.auditService.save(details, operationType, userProfile);

	}

}
