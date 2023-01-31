package com.portal.service.imp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.dao.ITaskUserDAO;
import com.portal.dto.TaskUserDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.dto.request.InsertOrEditSpecialServiceDTO;
import com.portal.dto.request.InsertOrEditTaskUserDTO;
import com.portal.dto.request.InsertOrEditTaskUserTimeDTO;
import com.portal.dto.request.SearchTaskUserDTO;
import com.portal.enums.AuditOperationType;
import com.portal.enums.StageMovementType;
import com.portal.enums.StatusType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.TaskUserFactory;
import com.portal.model.ClassifierModel;
import com.portal.model.StageMovementModel;
import com.portal.model.TaskModel;
import com.portal.model.TaskUserModel;
import com.portal.model.TeamModel;
import com.portal.model.UserModel;
import com.portal.service.IAuditService;
import com.portal.service.IChecklistService;
import com.portal.service.IClassifierService;
import com.portal.service.IJiraIntegrationService;
import com.portal.service.IStageMovementService;
import com.portal.service.ITaskService;
import com.portal.service.ITaskUserService;
import com.portal.service.ITaskUserTimeService;
import com.portal.service.ITeamService;
import com.portal.utils.PortalTimeUtils;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TaskUserService implements ITaskUserService {

	@Autowired
	private ITaskUserDAO dao;

	@Autowired
	private TaskUserFactory factory;

	@Autowired
	private ITaskUserTimeService timeRegisterService;

	@Autowired
	private ITaskService taskService;

	@Autowired
	private IAuditService auditService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IStageMovementService movementService;

	@Autowired
	private IJiraIntegrationService jiraIntegrationService;

	@Autowired
	private IChecklistService checklistService;

	@Autowired
	private ITeamService teamService;

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	IClassifierService classifierService;

	@Override
	public Optional<TaskUserDTO> find(SearchTaskUserDTO search) throws AppException, BusException {

		Optional<TaskUserModel> finded = this.dao.find(factory.convertFromFilterDto(search));

		return finded.isPresent() ? Optional.of(this.factory.convertFromModel(finded.get())) : Optional.empty();
	}

	@Override
	public Optional<TaskUserDTO> getById(Integer id) throws AppException, BusException {
		Optional<TaskUserModel> finded = this.dao.getById(id);
		return Optional.of(this.factory.convertFromModel(finded.get()));
	}

	@Override
	public List<TaskUserDTO> list() throws AppException, BusException {

		List<TaskUserModel> finded = this.dao.list();

		return factory.convertFromListOfModels(finded);
	}

	@Override
	public List<TaskUserDTO> search(SearchTaskUserDTO search) throws AppException, BusException {

		List<TaskUserModel> finded = this.dao.search(factory.convertFromFilterDto(search));

		return factory.convertFromListOfModels(finded);
	}

	@Override
	public Optional<TaskUserDTO> saveOrUpdate(InsertOrEditTaskUserDTO model, UserProfileDTO userProfile)
			throws AppException, BusException {

		if (model != null && (model.getId() == null || model.getId().equals(0))) {
			return this.save(model, userProfile);
		} else {
			return this.update(model, userProfile);
		}

	}

	@Override
	public Optional<TaskUserDTO> save(InsertOrEditTaskUserDTO insertDTO, UserProfileDTO userProfile)
			throws AppException, BusException {

		TaskUserModel modelToSave = factory.convertFromInsertDto(insertDTO);

		/*
		 * RF-04 Adiciona valor do serviço realizado a Tarefa do usuário , valor não
		 * será mais modificado
		 */
		Optional<TaskModel> taskToSave = taskService.getModelById(modelToSave.getTask().getId());

		if (taskToSave.isPresent() && taskToSave.get().getStage().getValue() != null)
			modelToSave.setStageValue(taskToSave.get().getStage().getValue());

		this.validateDuplicate(modelToSave);

		Optional<TaskUserModel> saved = this.dao.save(modelToSave);

		this.audit(saved.get(), AuditOperationType.TASK_USER_INSERTED, userProfile);

		return getById(saved.get().getId());
	}

	@Override
	public Optional<TaskUserDTO> update(InsertOrEditTaskUserDTO model, UserProfileDTO userProfile)
			throws AppException, BusException {

		TaskUserModel toEdit = factory.convertFromInsertDto(model);

		if (toEdit.getStatus() != null && toEdit.getStatus().equals(StatusType.FINALIZADO.getType())) {

			toEdit.setDateFinish(new Date());

		}

		Optional<TaskUserModel> saved = this.dao.update(toEdit);

		this.audit(saved.get(), AuditOperationType.TASK_USER_UPDATED, userProfile);

		return getById(saved.get().getId());
	}

	@Override
	public void delete(Integer id, UserProfileDTO userProfile) throws AppException, BusException {

		Optional<TaskUserModel> finded = this.dao.getById(id);

		if (finded.isPresent()) {
			this.audit(finded.get(), AuditOperationType.TASK_USER_DELETED, userProfile);

			this.dao.delete(id);
		}

	}

	@Override
	public void audit(TaskUserModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {

		String details = String.format("id:%s;dateStart:%s;dateFinish:%s;status:%s;task:%s;user:%s;", model.getId(),
				model.getDateStart(), model.getDateFinish(), model.getStatus() != null ? model.getStatus().getId() : 0,
				model.getTask() != null ? model.getTask() : 0, model.getUser() != null ? model.getUser().getId() : 0);
		this.auditService.save(details, operationType, userProfile);

	}

	private void validateDuplicate(TaskUserModel model) throws AppException, BusException {

		if (model != null) {

			Optional<TaskUserModel> foundParam = this.dao.find(new TaskUserModel(model.getTask(), model.getUser()));

			if (foundParam.isPresent()) {
				throw new BusException(
						this.messageSource.getMessage("error.stage.duplicate", null, LocaleContextHolder.getLocale()));
			}
		}
	}

	@Override
	public Optional<TaskUserDTO> finishTask(Integer taskID, Integer userID, Integer movementID,
			UserProfileDTO userProfile) throws AppException, BusException {

		Optional<TaskUserModel> taskUserFinded = this.dao.find(new TaskUserModel(new TaskModel(taskID),
				userID != null && userID > 0 ? new UserModel(userID) : userProfile.getUser()));

		if (taskUserFinded.isPresent()
				&& taskUserFinded.get().getStatus().getId().equals(StatusType.EM_ANDAMENTO.getType().getId())) {
			Integer numberOfUsersInTask = this.dao.getNumberOfUsersInTask(taskUserFinded.get().getTask().getId(),
					userID != null && userID > 0 ? userID : userProfile.getUser().getId());

			if (numberOfUsersInTask > 0) {

				this.dao.finishOneTaskByID(taskUserFinded.get().getId());

				return this.getById(taskUserFinded.get().getId());

			}

			if (movementID == null || movementID == 0)
				throw new BusException(this.messageSource.getMessage("error.stage.finish.notAllowed",
						new Object[] { "Não foi informado o proximo estagio a ser realizado." },
						LocaleContextHolder.getLocale()));

			Boolean canFinish = this.dao.canFinishTheTask(taskID);

			if (canFinish) {

				this.dao.finishOneTaskByID(taskUserFinded.get().getId());

				this.taskService.changeStatusTask(taskID, StatusType.FINALIZADO, null);

				Optional<TaskModel> taskFinded = this.taskService.getModelById(taskUserFinded.get().getTask().getId());

				if (taskFinded.get().getStage().getChecklist() != null
						&& taskFinded.get().getStage().getChecklist().getId() > 0) {

					String fileName = checklistService.generateChecklistPDF(taskID);
					this.taskService.saveChecklistFile(taskID, fileName);
				}

				if (taskFinded.get().getStage().getValue() != null)
					this.taskService.generateTaskPayment(taskID, taskFinded.get().getStage());

				this.transitJiraChoice(StageMovementType.MOVEMENT,
						taskService.getModelById(taskUserFinded.get().getTask().getId()).get(), userProfile, movementID,
						null);

				return this.getById(taskUserFinded.get().getId());
			} else {
				throw new BusException(this.messageSource.getMessage("error.stage.finish.notAllowed",
						new Object[] { "Perguntas sem resposta. " }, LocaleContextHolder.getLocale()));
			}

		}
		if (taskUserFinded.isPresent()
				&& taskUserFinded.get().getStatus().getId().equals(StatusType.PAUSADO.getType().getId())) {
			throw new BusException(this.messageSource.getMessage("error.stage.finish.notAllowed", new Object[] {
					"Tarefa encontra-se pausada. Solicite ao seu gestor, realizar o apontamento de continuidade da OS!" },
					LocaleContextHolder.getLocale()));
		}
		throw new BusException(this.messageSource.getMessage("error.stage.finish.notAllowed",
				new Object[] { "A tarefa não encontrada ou em situação que impossibilita a finalização. " },
				LocaleContextHolder.getLocale()));

	}

	@Override
	public Optional<TaskUserDTO> resumeTask(Integer taskID, Integer userID, UserProfileDTO userProfile)
			throws AppException, BusException {

		Optional<TaskUserModel> taskFinded = this.dao.find(new TaskUserModel(new TaskModel(taskID),
				userID != null && userID > 0 ? new UserModel(userID) : userProfile.getUser()));

		if (taskFinded.isPresent()
				&& taskFinded.get().getStatus().getId().equals(StatusType.PAUSADO.getType().getId())) {

			this.dao.pauseAllTaskOfUser(userID != null && userID > 0 ? userID : userProfile.getUser().getId());

			this.taskService.changeStatusTask(taskFinded.get().getTask().getId(), StatusType.EM_ANDAMENTO, null);

			Optional<TaskUserDTO> taskSaved = this.update(new InsertOrEditTaskUserDTO(taskFinded.get().getId(), taskID,
					userID != null && userID > 0 ? userID : userProfile.getUser().getId(),
					StatusType.EM_ANDAMENTO.getType().getId()), userProfile);

			this.timeRegisterService.saveNewTime(new InsertOrEditTaskUserTimeDTO(
					PortalTimeUtils.localDateTimeToDate(LocalDateTime.now()), taskSaved.get().getId()), userProfile);

			this.transitJiraChoice(StageMovementType.RESUME,
					taskService.getModelById(taskFinded.get().getTask().getId()).get(), userProfile, null, null);

			return this.getById(taskFinded.get().getId());
		}

		return Optional.empty();

	}

	@Override
	public Optional<TaskUserDTO> pauseTask(Integer taskID, Integer userID, UserProfileDTO userProfile)
			throws AppException, BusException {

		Optional<TaskUserModel> taskFinded = this.dao.find(new TaskUserModel(new TaskModel(taskID),
				userID != null && userID > 0 ? new UserModel(userID) : userProfile.getUser()));

		if (taskFinded.isPresent()
				&& taskFinded.get().getStatus().getId().equals(StatusType.EM_ANDAMENTO.getType().getId())) {

			this.dao.pauseOneTaskByID(taskFinded.get().getId());

			this.transitJiraChoice(StageMovementType.PAUSE,
					taskService.getModelById(taskFinded.get().getTask().getId()).get(), userProfile, null, null);

			return this.getById(taskFinded.get().getId());
		}

		throw new BusException(
				this.messageSource.getMessage("error.generic.getbyid", null, LocaleContextHolder.getLocale()));

	}

	@Override
	public Optional<TaskUserDTO> startTask(Integer taskID, Integer userID, UserProfileDTO userProfile)
			throws AppException, BusException {

		Optional<TaskModel> taskFinded = taskService.getModelById(taskID);

		if (taskFinded.isPresent()) {

			this.canStartTask(userID != null && userID > 0 ? userID : userProfile.getUser().getId(), taskFinded.get());

			List<TaskUserDTO> taskInProgress = this.search(new SearchTaskUserDTO(
					userID != null && userID > 0 ? userID : userProfile.getUser().getId(), StatusType.EM_ANDAMENTO));

			if (taskInProgress.size() > 0) {
				throw new BusException(
						this.messageSource.getMessage("error.task.inProgress", null, LocaleContextHolder.getLocale()));
			}

			this.dao.pauseAllTaskOfUser(userID != null && userID > 0 ? userID : userProfile.getUser().getId());

			Optional<TaskUserDTO> taskSaved = this.save(new InsertOrEditTaskUserDTO(0, taskID,
					userID != null && userID > 0 ? userID : userProfile.getUser().getId(),
					StatusType.EM_ANDAMENTO.getType().getId()), userProfile);

			this.timeRegisterService.saveNewTime(
					new InsertOrEditTaskUserTimeDTO(taskSaved.get().getDateStart(), taskSaved.get().getId()),
					userProfile);

			this.taskService.changeStatusTask(taskFinded.get().getId(), StatusType.EM_ANDAMENTO, null);

			if (taskFinded.get().getStatus().getId().equals(StatusType.AGUARDANDO_INICIO.getType().getId()))
				this.transitJiraChoice(StageMovementType.START, taskFinded.get(), userProfile, null, null);

			return this.getById(taskSaved.get().getId());
		}

		throw new BusException(
				this.messageSource.getMessage("error.generic.getbyid", null, LocaleContextHolder.getLocale()));

	}

	/**
	 * Implementação de regra de negocio para bloqueio de novas atividades.
	 * RF-1,RF-2 e RF-3.
	 * 
	 * <br>
	 * <a href="">Documentação</a> //TODO: Criar a documentação de regra de negocio!
	 * 
	 */
	private void canStartTask(Integer userID, TaskModel task)
			throws AppException, NoSuchMessageException, BusException {

		// RF-1:
		List<TeamModel> teamsByUserFinded = this.teamService.getAllTeamByUser(userID);
		List<TeamModel> taskTeamFinded = this.taskService.getTeamWhiTaskUser(task.getId());

		if (task.getStatus().getId() != StatusType.AGUARDANDO_INICIO.getType().getId()) {

			Boolean userTeamIsPresent = teamsByUserFinded.stream()
					.anyMatch(e -> taskTeamFinded.stream().anyMatch(t -> t.getId().equals(e.getId())));

			if (!userTeamIsPresent)
				throw new BusException(this.messageSource.getMessage("error.task.userTeamIsPresent", null,
						LocaleContextHolder.getLocale()));

		}

		// RF-2:
		Optional<TaskModel> lastTaskByUser = this.taskService.getLastTaskByUser(userID);

		if (lastTaskByUser.isPresent()
				&& (lastTaskByUser.get().getStatus().getId().equals(StatusType.EM_ANDAMENTO.getType().getId()))) {
			throw new BusException(this.messageSource.getMessage("error.task.teamTaskInProgress", new Object[] {
					lastTaskByUser.get().getServiceOrder().getNumber(), LocaleContextHolder.getLocale() }, null));
		}

		// RF-3:
		List<TaskModel> tasksActiveOnTeamFinded = new ArrayList<TaskModel>();

		if (teamsByUserFinded.size() >= 1) {
			for (TeamModel team : teamsByUserFinded) {
				tasksActiveOnTeamFinded.addAll(this.taskService.getTasksActiveOnTeamFinded(team.getId()));
			}

			Boolean hasTask = tasksActiveOnTeamFinded.stream().anyMatch(e -> e.getId().equals(task.getId()));

			if (!hasTask && tasksActiveOnTeamFinded.size() > 0) {
				throw new BusException(this.messageSource.getMessage("error.task.taskInProgressOnTeam", new Object[] {
						tasksActiveOnTeamFinded.get(0).getServiceOrder().getNumber(), LocaleContextHolder.getLocale() },
						null));
			}
		}

	}

	@Override
	public boolean specialService(InsertOrEditSpecialServiceDTO insertDTO, UserProfileDTO userProfile)
			throws AppException, BusException {

		if (insertDTO.getMovementID() == null || insertDTO.getMovementID() == 0)
			throw new BusException(this.messageSource.getMessage("error.stage.finish.notAllowed",
					new Object[] { "Não foi informado o proximo estagio a ser realizado." },
					LocaleContextHolder.getLocale()));

		Optional<TaskModel> taskFinded = taskService.getModelById(insertDTO.getTaskID());

		String movementDescriptionText = this.getMovementDescriptionText(insertDTO.getClassifiersId());

		Optional<StageMovementModel> movementToMake = movementService.getById(insertDTO.getMovementID());

		jiraIntegrationService.transitJiraSpecialStatus(taskFinded.get(), movementToMake.get(), userProfile,
				movementDescriptionText);

		return true;
	}

	private String getMovementDescriptionText(List<Integer> movementDescriptionIds)
			throws NoSuchMessageException, BusException, AppException {

		List<ClassifierModel> classifierListModel = this.classifierService.searchInIds(movementDescriptionIds);

		if (classifierListModel == null || classifierListModel.size() == 0)
			throw new BusException(this.messageSource.getMessage("error.stage.finish.notAllowed", new Object[] {
					"Não existe nenhum local para abertura do serviço especial selecionado, selecione ao menos um local." },
					LocaleContextHolder.getLocale()));

		return classifierListModel.stream().map(e -> "\"" + e.getLabel() + "\"").collect(Collectors.joining(", "));
	}

	private void transitJiraChoice(StageMovementType type, TaskModel task, UserProfileDTO userProfile,
			Integer movementID, String movementDescription) throws AppException, BusException {
		task.getStage().setMoviments(movementService.search(new StageMovementModel(task.getStage())));

		if (task.getStage().getMoviments().size() > 0) {
			Optional<StageMovementModel> movementToMake = Optional.empty();
			switch (type) {
			case START:
				movementToMake = task.getStage().getMoviments().stream()
						.filter(m -> m.getType().getId().equals(type.getType().getId())).findFirst();

				if (movementToMake.isPresent()) {
					jiraIntegrationService.transitJiraStatus(task, movementToMake.get(), userProfile);
				}

				break;

			case MOVEMENT:
				movementToMake = task.getStage().getMoviments().stream()
						.filter(m -> m.getType().getId().equals(type.getType().getId()) && m.getId().equals(movementID))
						.findFirst();
				if (movementToMake.isEmpty())
					throw new BusException(this.messageSource.getMessage("error.stage.finish.notAllowed",
							new Object[] {
									"Não existe finalização cadastrada para esta etapa, contate o administrador." },
							LocaleContextHolder.getLocale()));

				if (movementToMake.isPresent()) {
					jiraIntegrationService.transitJiraStatus(task, movementToMake.get(), userProfile);
				}

				break;

			case PAUSE:
				boolean startMovementRegistred = task.getStage().getMoviments().stream()
						.anyMatch(m -> m.getType().getId().equals(type.getType().getId()));
				if (startMovementRegistred)
					movementToMake = task.getStage().getMoviments().stream()
							.filter(m -> m.getType().getId().equals(type.getType().getId())).findFirst();

				if (movementToMake.isPresent()) {
					jiraIntegrationService.transitJiraStatus(task, movementToMake.get(), userProfile);
				}

				break;

			case SPECIAL:
				movementToMake = task.getStage().getMoviments().stream()
						.filter(m -> m.getType().getId().equals(type.getType().getId()) && m.getId().equals(movementID))
						.findFirst();

				if (movementToMake.isEmpty())
					throw new BusException(this.messageSource.getMessage("error.stage.finish.notAllowed",
							new Object[] {
									"Não existe finalização cadastrada para esta etapa, contate o administrador." },
							LocaleContextHolder.getLocale()));

				if (movementToMake.isPresent() && StringUtils.isNotEmpty(movementDescription)) {
					jiraIntegrationService.transitJiraSpecialStatus(task, movementToMake.get(), userProfile,
							movementDescription);
				}

				break;

			case RESUME:
				boolean pauseMovementRegistred = task.getStage().getMoviments().stream()
						.anyMatch(m -> m.getType().getId().equals(StageMovementType.PAUSE.getType().getId()));

				if (pauseMovementRegistred)
					movementToMake = task.getStage().getMoviments().stream()
							.filter(m -> m.getType().getId().equals(StageMovementType.START.getType().getId()))
							.findFirst();

				if (movementToMake.isPresent()) {
					jiraIntegrationService.transitJiraStatus(task, movementToMake.get(), userProfile);
				}

				break;
			}

		}

	}

	@Override
	public List<TaskUserDTO> searchInProgress(SearchTaskUserDTO search) throws AppException, BusException {

		List<TaskUserModel> finded = this.dao.searchInProgress(factory.convertFromFilterDto(search));

		return factory.convertFromListOfModels(finded);
	}

}
