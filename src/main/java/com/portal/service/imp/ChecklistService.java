package com.portal.service.imp;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
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
import com.itextpdf.html2pdf.HtmlConverter;
import com.portal.dao.IChecklistAnswerDAO;
import com.portal.dao.IChecklistDAO;
import com.portal.dto.ChecklistResponseDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.enums.AuditOperationType;
import com.portal.enums.FileType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.ChecklistAnswerFactory;
import com.portal.factory.ChecklistFactory;
import com.portal.model.ChecklistAnswerModel;
import com.portal.model.ChecklistModel;
import com.portal.model.ParameterModel;
import com.portal.model.TaskModel;
import com.portal.service.IAuditService;
import com.portal.service.IChecklistAnswerService;
import com.portal.service.IChecklistGroupService;
import com.portal.service.IChecklistService;
import com.portal.service.IFilesService;
import com.portal.service.IJiraIntegrationService;
import com.portal.service.IMailService;
import com.portal.service.IMailTeamplateService;
import com.portal.service.IPDFTeamplateService;
import com.portal.service.IParameterService;
import com.portal.service.ITaskService;
import com.portal.validators.ValidationHelper;
import com.portal.validators.ValidationHelper.OnUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChecklistService implements IChecklistService {

	@Autowired
	private Validator validator;

	@Autowired
	private IChecklistDAO iChecklistDAO;

	@Autowired
	private IAuditService auditService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private IChecklistGroupService iChecklistGroupService;

	@Autowired
	private IChecklistAnswerDAO iCheckListAnswer;

	@Autowired
	private IChecklistAnswerService answerService;

	@Autowired
	private ChecklistFactory checkListFactory;

	@Autowired
	private ChecklistAnswerFactory checkListAnswerFactory;

	@Autowired
	private ITaskService taskService;

	@Autowired
	private IFilesService filesService;

	@Autowired
	private IPDFTeamplateService teamplatePDFService;

	@Autowired
	private IMailTeamplateService mailTeamplateService;

	@Autowired
	private IParameterService parameterService;

	@Autowired
	private IMailService mailService;

	@Autowired
	private IJiraIntegrationService integrationService;

	@Override
	public Optional<ChecklistModel> find(ChecklistModel model) throws AppException, BusException {
		try {
			return this.iChecklistDAO.find(model);

		} catch (Exception e) {
			log.error("Erro no processo de buscar checklist.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.find",
					new Object[] { ChecklistModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<ChecklistModel> getById(Integer id) throws AppException, BusException {
		try {
			if (id == null)
				throw new BusException("ID de busca inválido.");

			Optional<ChecklistModel> checklist = this.iChecklistDAO.getById(id);

			if (checklist.isPresent()) {
				checklist.get().setGroups(iChecklistGroupService.getByChecklist(checklist.get().getId()));
			}

			return checklist;

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao consultar uma checklist pelo ID: {}", id, e);
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { ChecklistModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<ChecklistModel> list() throws AppException, BusException {
		List<ChecklistModel> checklistModel = this.iChecklistDAO.list();
		return checklistModel;
	}

	@Override
	public List<ChecklistModel> search(ChecklistModel model) throws AppException, BusException {
		try {
			return this.iChecklistDAO.search(model);

		} catch (Exception e) {
			log.error("Erro no processo de procurar checklist.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.search",
					new Object[] { ChecklistModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<ChecklistModel> saveOrUpdate(ChecklistModel model, UserProfileDTO userProfile)
			throws AppException, BusException {

		if (model.getId() != null && model.getId() > 0) {
			return this.update(model, userProfile);
		} else {
			return this.save(model, userProfile);
		}
	}

	@Override
	public Optional<ChecklistModel> save(ChecklistModel model, UserProfileDTO userProfile)
			throws AppException, BusException {

		try {

			Optional<ChecklistModel> saved = this.iChecklistDAO.save(model);

			if (saved.isPresent()) {
				model.setId(saved.get().getId());
				this.iChecklistGroupService.save(model);
			}

			this.audit((saved.isPresent() ? saved.get() : null), AuditOperationType.CHECK_LIST_INSERTED, userProfile);

			return this.getById(saved.get().getId());

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro no processo de cadastro de Checklist: {}", model, e);
			throw new AppException(this.messageSource.getMessage("error.generic.save",
					new Object[] { ChecklistModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public Optional<ChecklistModel> update(ChecklistModel model, UserProfileDTO userProfile)
			throws AppException, BusException {

		try {
			this.validateEntity(model, OnUpdate.class);

			Optional<ChecklistModel> updated = this.iChecklistDAO.update(model);

			if (updated.isPresent()) {
				model.setId(updated.get().getId());
				this.iChecklistGroupService.save(model);

			}

			this.audit((updated.isPresent() ? updated.get() : null), AuditOperationType.CHECK_LIST_UPDATED,
					userProfile);

			return this.getById(updated.get().getId());

		} catch (BusException e) {
			throw e;
		} catch (Exception e) {
			log.error("Erro no processo de atualização: {}", model, e);
			throw new AppException(this.messageSource.getMessage("error.generic.update",
					new Object[] { ChecklistModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	private void validateEntity(ChecklistModel model, Class<?> group) throws AppException, BusException {
		ValidationHelper.generateException(validator.validate(model, group));

	}

	@Override
	public Optional<ChecklistResponseDTO> getAnswerByQuestion(Integer id) throws AppException, BusException {
		try {
			if (id == null)
				throw new BusException("ID de busca inválido.");

			Optional<ChecklistModel> finded = this.iChecklistDAO.getById(id);

			Optional<ChecklistResponseDTO> response = Optional.of(new ChecklistResponseDTO());

			if (finded.isPresent()) {
				finded.get().setGroups(iChecklistGroupService.getByChecklist(finded.get().getId()));
				response.get().setChecklist(checkListFactory.convertFromModel(finded.get()));
				response.get().setAnswers(checkListAnswerFactory
						.convertFromListOfModels(iCheckListAnswer.getByCheckListId(finded.get().getId())));
			}

			return response;

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao consultar uma checklist pelo ID: {}", id, e);
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { ChecklistModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}

	}

	@Override
	public Optional<ChecklistResponseDTO> getAnswerByTask(Integer taskID) throws AppException, BusException {
		try {

			Optional<TaskModel> task = this.taskService.getModelById(taskID);

			if (task.isEmpty() || task.get().getStage() == null)
				throw new BusException("ID de busca inválido.");

			Optional<ChecklistModel> finded = this.iChecklistDAO
					.find(new ChecklistModel(task.get().getStage().getChecklist().getId()));

			Optional<ChecklistResponseDTO> response = Optional.of(new ChecklistResponseDTO());

			if (finded.isPresent()) {
				finded.get().setGroups(iChecklistGroupService.getByChecklist(finded.get().getId()));
				response.get().setChecklist(checkListFactory.convertFromModel(finded.get()));
				response.get().setAnswers(answerService.searchWithPhotos(new ChecklistAnswerModel(task.get())));

			}

			return response;

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro ao consultar uma checklist pelo ID: {}", taskID, e);
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { ChecklistModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}

	}

	@Override
	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException {

		try {
			if (id == null)
				throw new BusException("ID de exclusão inválido");

			Optional<ChecklistModel> entityDB = this.getById(id);
			if (!entityDB.isPresent()) {
				throw new BusException("Checklist a ser excluído não existe.");
			}

			this.iChecklistGroupService.delete(entityDB.get());
			this.iChecklistDAO.delete(id);

			this.audit((entityDB.isPresent() ? entityDB.get() : null), AuditOperationType.CHECK_LIST_DELETED,
					userProfile);

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			log.error("Erro no processo de exclusão de checklist.", e);
			throw new AppException(this.messageSource.getMessage("error.generic.delete",
					new Object[] { ChecklistModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public String generateChecklistPDF(Integer taskID) throws AppException, BusException {

		Optional<TaskModel> taskFInded = taskService.getModelById(taskID);

		if (taskFInded.isEmpty())
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { "Dados para salvar PDF" }, LocaleContextHolder.getLocale()));

		Optional<ChecklistResponseDTO> toSave = getAnswerByTask(taskFInded.get().getId());

		if (toSave.isEmpty())
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { "Existem respostas que ainda não foram finalizadas" },
					LocaleContextHolder.getLocale()));

		try {
			String fileName = taskFInded.get().getId().toString() + ".pdf";
			Path path = filesService.getPhysicalPath(FileType.CHECKLIST_PDF, fileName);

			OutputStream fileOutputStream = new FileOutputStream(path.toString());

			HtmlConverter.convertToPdf(teamplatePDFService.formatCheckListPDF(taskFInded.get(), toSave.get()),
					fileOutputStream);

			Optional<ParameterModel> emailsListToSend = this.parameterService
					.find(new ParameterModel("CHECKLIST_EMAIL_BROADCAST"));

			String formatedMailChecklistFinished = mailTeamplateService.formatMailChecklistFinished(
					taskFInded.get().getServiceOrder().getNumber(), toSave.get().getChecklist().getName(),
					taskFInded.get().getStage().getName());

			mailService.sendMailMultipleAddresWithFile(emailsListToSend.get().getValue().split(","),
					"Finalização Checklist", formatedMailChecklistFinished,
					filesService.getPhysicalPath(FileType.CHECKLIST_PDF, fileName));

			integrationService.sendFileJira(path.toString(), taskFInded.get().getServiceOrder().getNumberJira());

			return fileName;

		} catch (Exception e) {
			throw new AppException("Erro ao tentar gerar o PDF da tarefa tente, novamente!");
		}

	}

	@Override
	public void audit(ChecklistModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {
		try {

			this.auditService.save(objectMapper.writeValueAsString(model), operationType, userProfile);
		} catch (JsonProcessingException e) {
			throw new AppException(this.messageSource.getMessage("error.audit", null, LocaleContextHolder.getLocale()));
		}

	}

}
