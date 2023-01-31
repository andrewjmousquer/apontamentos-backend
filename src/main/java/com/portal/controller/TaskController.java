package com.portal.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portal.dto.AppointmentDTO;
import com.portal.dto.TaskDTO;
import com.portal.dto.TaskUserDTO;
import com.portal.dto.request.InsertOrEditSpecialServiceDTO;
import com.portal.dto.request.InsertOrEditTaskUserTimeDTO;
import com.portal.dto.request.SearchAppointmentDTO;
import com.portal.dto.request.SearchTaskDTO;
import com.portal.dto.request.SearchTaskUserDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.service.ITaskUserService;
import com.portal.service.ITaskUserTimeService;
import com.portal.service.imp.TaskService;
import com.portal.utils.PortalControllerUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/protected/task")
@Tag(name = "Task", description = "Task Entity controller")
@CrossOrigin(origins = "*")
public class TaskController extends BaseController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private ITaskUserService taskUserService;

	@Autowired
	private ITaskUserTimeService userTimeService;

	@Operation(summary = "Buscar tarefas por chassi or OS")
	@GetMapping(path = "/osOrChassi")
	public @ResponseBody ResponseEntity<List<TaskDTO>> getByOsChassi(
			@RequestParam(name = "osOrChassi", required = false) @Parameter(description = "Numero da ordem de serviço ou Chassi do veiculo") String osOrChassi)
			throws AppException, BusException, NoSuchMessageException, IOException, InterruptedException {

		return ResponseEntity.ok(taskService.returnTasksByServiceOrderOrChassi(osOrChassi, getUserProfile()));

	}

	@Operation(summary = "Modifica situação de uma tarefa")
	@GetMapping(path = "/finish")
	public @ResponseBody ResponseEntity<TaskUserDTO> finishTask(
			@RequestParam(name = "task", required = true) @Parameter(description = "Identificador da tarefa") Integer taskID,
			@RequestParam(name = "moviment", required = true) @Parameter(description = "Identificador da situação a ser executada") Integer movementID,
			@RequestParam(name = "usuario", required = false) @Parameter(description = "Usuário a ser atribuido a tarefa") Integer usuarioID)
			throws Exception {
		return ResponseEntity.ok(taskUserService.finishTask(taskID, usuarioID, movementID, getUserProfile()).get());
	}

	@Operation(summary = "Inicia uma tarefa por usuário")
	@GetMapping(path = "/start")
	public @ResponseBody ResponseEntity<TaskUserDTO> startTask(
			@RequestParam(name = "task", required = true) @Parameter(description = "Identificador da tarefa") Integer taskID,
			@RequestParam(name = "usuario", required = false) @Parameter(description = "Usuário a ser atribuido a tarefa") Integer usuarioID)
			throws Exception {
		return ResponseEntity.ok(taskUserService.startTask(taskID, usuarioID, getUserProfile()).get());
	}

	@Operation(summary = "Continua uma tarefa por usuário")
	@GetMapping(path = "/resume")
	public @ResponseBody ResponseEntity<TaskUserDTO> resumeTask(
			@RequestParam(name = "task", required = true) @Parameter(description = "Identificador da tarefa") Integer taskID,
			@RequestParam(name = "usuario", required = false) @Parameter(description = "Usuário a ser atribuido a tarefa") Integer usuarioID)
			throws Exception {
		return ResponseEntity.ok(taskUserService.resumeTask(taskID, usuarioID, getUserProfile()).get());
	}

	@Operation(summary = "Pausa uma tarefa por usuário")
	@GetMapping(path = "/pause")
	public @ResponseBody ResponseEntity<TaskUserDTO> pauseTask(
			@RequestParam(name = "task", required = true) @Parameter(description = "Identificador da tarefa") Integer taskID,
			@RequestParam(name = "usuario", required = false) @Parameter(description = "Usuário a ser atribuido a tarefa") Integer usuarioID)
			throws Exception {
		return ResponseEntity.ok(taskUserService.pauseTask(taskID, usuarioID, getUserProfile()).get());
	}

	@Operation(summary = "Busca uma tarefa por ID e Usuário")
	@GetMapping(path = "/by-user")
	public @ResponseBody ResponseEntity<TaskUserDTO> getTaskUserByIdTask(
			@RequestParam(name = "taskID", required = true) @Parameter(description = "Identificador de tarefa em progresso ") Integer taskID)
			throws AppException, BusException, NoSuchMessageException, IOException, InterruptedException {

		Optional<TaskUserDTO> taskUserFinded = taskUserService
				.find(new SearchTaskUserDTO(taskID, getUserProfile().getUser().getId()));

		if (taskUserFinded.isPresent()) {
			return ResponseEntity.ok(taskUserFinded.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@Operation(summary = "Buscar todas as tarefas em aberto e disponivel por Usuário")
	@GetMapping(path = "/user/avalible")
	public @ResponseBody ResponseEntity<List<TaskDTO>> listAvalible() throws Exception {
		return ResponseEntity.ok(taskService.searchByUser(new SearchTaskDTO(), this.getUserProfile()));
	}

	@Operation(summary = "Buscar todas as tarefas por Usuário")
	@GetMapping(path = "/user/progress")
	public @ResponseBody ResponseEntity<List<TaskUserDTO>> listInProgress() throws Exception {

		return ResponseEntity.ok(taskUserService.searchInProgress(new SearchTaskUserDTO(getUserProfile().getUser())));
	}

	@Operation(summary = "Pesquisa todos os registros de horários em tarefas")
	@PostMapping(path = "/appointments")
	public @ResponseBody ResponseEntity<List<AppointmentDTO>> searchObject(
			@Valid @RequestBody @Parameter(description = "Objeto de pesquisa da tarefa") SearchAppointmentDTO dto)
			throws Exception {
		return ResponseEntity.ok(userTimeService.searchAllRegistredTime(dto));
	}
	
	@Operation(summary = "Exportta todos os registros de horários em tarefas")
	@PostMapping(path = "/appointments/excel")
	public @ResponseBody ResponseEntity<InputStreamResource> exportObject(
			@Valid @RequestBody @Parameter(description = "Objeto de pesquisa da tarefa") SearchAppointmentDTO dto)
			throws Exception {
		byte[] content = userTimeService.exportAllRegistredTime(dto);
		return PortalControllerUtils.createDownload(content, PortalControllerUtils.APPLICATION_XLSX, "Apontamentos.xlsx");
	}

	@Operation(summary = "Edita um apontamento")
	@PostMapping(path = "/appointments/edit")
	public @ResponseBody ResponseEntity<Boolean> appointmentEdit(@Valid @RequestBody InsertOrEditTaskUserTimeDTO dto)
			throws Exception {
		return ResponseEntity.ok(userTimeService.update(dto, this.getUserProfile()).isPresent());
	}

	@Operation(summary = "Buscar todos os Usuários por tarefa")
	@GetMapping(path = "/list-users")
	public @ResponseBody ResponseEntity<List<TaskUserDTO>> listUsers(
			@RequestParam(name = "task", required = true) @Parameter(description = "Identificador da tarefa") Integer taskID)
			throws Exception {
		return ResponseEntity.ok(this.taskUserService.search(new SearchTaskUserDTO(taskID)));
	}

	@Operation(summary = "Adiciona um serviço especial")
	@PostMapping(path = "/special")
	public @ResponseBody ResponseEntity<Boolean> specialService(@Valid @RequestBody InsertOrEditSpecialServiceDTO dto)
			throws Exception {
		return ResponseEntity.ok(taskUserService.specialService(dto, this.getUserProfile()));
	}

}
