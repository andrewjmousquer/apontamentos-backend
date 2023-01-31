package com.portal.service.imp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.dao.ITaskDAO;
import com.portal.dto.TaskDTO;
import com.portal.dto.TaskWithTimeDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.dto.request.InsertOrEditTaskDTO;
import com.portal.dto.request.SearchTaskDTO;
import com.portal.enums.AuditOperationType;
import com.portal.enums.StatusType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.TaskFactory;
import com.portal.model.ServiceOrderModel;
import com.portal.model.StageModel;
import com.portal.model.TaskModel;
import com.portal.model.TeamModel;
import com.portal.service.IAuditService;
import com.portal.service.IJiraIntegrationService;
import com.portal.service.IServiceOrderService;
import com.portal.service.ITaskService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TaskService implements ITaskService {

	@Autowired
	private ITaskDAO dao;

	@Autowired
	private TaskFactory factory;

	@Autowired
	private IAuditService auditService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IServiceOrderService serviceOrderService;

	@Autowired
	private IJiraIntegrationService jiraIntegrationService;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public Optional<TaskDTO> find(SearchTaskDTO search) throws AppException, BusException {

		Optional<TaskModel> finded = this.dao.find(factory.convertFromFilterDto(search));

		return finded.isPresent() ? Optional.of(this.factory.convertFromModel(finded.get())) : Optional.empty();
	}

	@Override
	public Optional<TaskDTO> getById(Integer id) throws AppException, BusException {

		Optional<TaskModel> finded = this.dao.getById(id);

		if (finded.isEmpty())
			throw new BusException(
					this.messageSource.getMessage("error.generic.getbyid", null, LocaleContextHolder.getLocale()));

		return Optional.of(this.factory.convertFromModel(finded.get()));
	}

	@Override
	public Optional<TaskModel> getModelById(Integer id) throws AppException, BusException {

		Optional<TaskModel> finded = this.dao.getById(id);

		if (finded.isEmpty())
			throw new BusException(
					this.messageSource.getMessage("error.generic.getbyid", null, LocaleContextHolder.getLocale()));

		return finded;
	}

	@Override
	public List<TaskDTO> list() throws AppException, BusException {

		List<TaskModel> finded = this.dao.list();

		return factory.convertFromListOfModels(finded);
	}

	@Override
	public List<TaskDTO> search(SearchTaskDTO search) throws AppException, BusException {

		List<TaskModel> finded = this.dao.search(factory.convertFromFilterDto(search));

		return factory.convertFromListOfModels(finded);
	}

	@Override
	public List<TaskDTO> searchByUser(SearchTaskDTO search, UserProfileDTO user) throws AppException, BusException {

		List<TaskModel> finded = this.dao.searchByUser(factory.convertFromFilterDto(search), user,
				search.isInProgress());

		return factory.convertFromListOfModels(finded);
	}

	@Override
	public Optional<TaskDTO> saveOrUpdate(InsertOrEditTaskDTO model, UserProfileDTO userProfile)
			throws AppException, BusException {

		if (model != null && (model.getId() == null || model.getId().equals(0))) {
			return this.save(model, userProfile);
		} else {
			return this.update(model, userProfile);
		}

	}

	@Override
	public Optional<TaskDTO> save(InsertOrEditTaskDTO model, UserProfileDTO userProfile)
			throws AppException, BusException {

		Optional<TaskModel> saved = this.dao.save(factory.convertFromInsertDto(model));

		this.audit(saved.get(), AuditOperationType.TASK_INSERTED, userProfile);

		return getById(saved.get().getId());
	}

	@Override
	public Optional<TaskDTO> update(InsertOrEditTaskDTO model, UserProfileDTO userProfile)
			throws AppException, BusException {

		Optional<TaskModel> saved = this.dao.save(factory.convertFromInsertDto(model));

		this.audit(saved.get(), AuditOperationType.TASK_UPDATED, userProfile);

		return getById(saved.get().getId());
	}

	@Override
	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException {

		Optional<TaskModel> finded = this.dao.getById(id);

		if (finded.isPresent()) {
			this.audit(finded.get(), AuditOperationType.TASK_DELETED, userProfile);
			this.dao.delete(id);
		}

	}

	@Override
	public void audit(TaskModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {

		String details = String.format(
				"id:%s;name:%s;dateStart:%s;dateFinish:%s;numberJira:%s;stage:%s;serviceOrder:%s;status:%s",
				model.getId(), model.getName(), model.getDateStart(), model.getDateFinish(), model.getNumberJira(),
				model.getStage() != null ? model.getStage().getId() : 0,
				model.getServiceOrder() != null ? model.getServiceOrder().getId() : 0,
				model.getStatus() != null ? model.getStatus().getId() : 0);
		this.auditService.save(details, operationType, userProfile);

	}

	@Override
	public Optional<TaskDTO> save(TaskModel model, UserProfileDTO userProfile) throws AppException, BusException {

		Optional<TaskModel> saved = this.dao.save(model);

		this.audit(saved.get(), AuditOperationType.TASK_INSERTED, userProfile);

		return getById(saved.get().getId());
	}

	@Override
	public List<TaskDTO> returnTasksByServiceOrderOrChassi(String osOrChassi, UserProfileDTO userProfile)
			throws NoSuchMessageException, IOException, InterruptedException, AppException, BusException {

		Optional<ServiceOrderModel> service = this.serviceOrderService
				.find(new ServiceOrderModel(osOrChassi, osOrChassi));

		if (service.isEmpty()) {
			service = Optional.of(jiraIntegrationService
					.getServiceOrderByNumberOsOrChassi(new ServiceOrderModel(osOrChassi, osOrChassi), userProfile));

		} else {

			List<TaskDTO> tasksFinded = this.searchByUser(new SearchTaskDTO(service.get().getId()), userProfile);

			if (tasksFinded.stream()
					.anyMatch(e -> e.getStatus().getId() == StatusType.EM_ANDAMENTO.getType().getId())) {
				return tasksFinded;
			} else {
				jiraIntegrationService.getServiceOrderByNumberOsOrChassi(service.get(), userProfile);
			}

		}

		List<TaskDTO> tasksFinded = this.searchByUser(new SearchTaskDTO(service.get().getId()), userProfile);

		if (tasksFinded.size() == 0)
			throw new BusException(
					this.messageSource.getMessage("error.task.notFound", null, LocaleContextHolder.getLocale()));

		return tasksFinded;

	}

	@Override
	public boolean changeStatusTask(Integer taskToChange, StatusType statusToGo, StatusType statusToConsider)
			throws AppException {

		this.dao.updateTaskStatus(taskToChange, statusToGo, statusToConsider);

		return true;
	}

	@Override
	public void generateTaskPayment(Integer taskID, StageModel stage) throws AppException {

		this.dao.generateTaskPayment(taskID, stage.getValue(), stage.getPaymentByTeam());

	}

	@Override
	public boolean saveChecklistFile(Integer taskToChange, String fileName) throws AppException {

		this.dao.saveChecklistFile(taskToChange, fileName);

		return true;
	}

	@Override
	public List<TaskWithTimeDTO> getAllByServiceOrderId(Integer id)
			throws AppException, NoSuchMessageException, BusException {

		Optional<List<TaskModel>> finded = this.dao.getByServiceOrderId(id);

		if (finded.isEmpty())
			throw new BusException(
					this.messageSource.getMessage("error.generic.getbyid", null, LocaleContextHolder.getLocale()));

		return factory.convertFromListOfModelsWithTime(finded.get());

	}

	@Override
	public List<TeamModel> getTeamWhiTaskUser(Integer id) throws NoSuchMessageException, AppException {
		return this.dao.getTeamWhiTaskUser(id);
	}

	@Override
	public Optional<TaskModel> getLastTaskByUser(Integer userID) throws NoSuchMessageException, AppException {
		return this.dao.getLastTaskByUser(userID);
	}

	@Override
	public List<TaskModel> getTasksActiveOnTeamFinded(Integer id) throws NoSuchMessageException, AppException {
		return this.dao.getTasksActiveOnTeamFinded(id);
	}

}
