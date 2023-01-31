package com.portal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portal.dto.ChecklistDTO;
import com.portal.dto.ChecklistResponseDTO;
import com.portal.dto.request.InsertOrEditChecklistDTO;
import com.portal.dto.request.SearchChecklistDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.ChecklistFactory;
import com.portal.model.ChecklistModel;
import com.portal.service.IChecklistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/protected/checklist")
@Tag(name = "Checklist", description = "Checklist Entity controller")
public class ChecklistController extends BaseController {

	@Autowired
	private IChecklistService iChecklistService;

	@Autowired
	private ChecklistFactory checklistFactory;

	@Operation(summary = "Retorna uma lista de checklist")
	@GetMapping(path = "/listAll")
	public @ResponseBody ResponseEntity<List<ChecklistDTO>> listAll() throws Exception {
		List<ChecklistModel> model = this.iChecklistService.list();
		return ResponseEntity.ok(checklistFactory.convertFromListOfModels(model));
	}

	@Operation(summary = "Buscar checklist")
	@PostMapping(path = "/search")
	public ResponseEntity<List<ChecklistDTO>> search(@Valid @RequestBody SearchChecklistDTO searchChecklistDTO)
			throws AppException, BusException {
		ChecklistModel modelToSearch = checklistFactory.convertFromFilterDto(searchChecklistDTO);
		List<ChecklistModel> modelsFinded = iChecklistService.search(modelToSearch);
		List<ChecklistDTO> convertedListToReturn = checklistFactory.convertFromListOfModels(modelsFinded);
		return ResponseEntity.ok(convertedListToReturn);
	}

	@Operation(summary = "Buscar checklist por id")
	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<ChecklistDTO> getById(
			@PathVariable(name = "id", required = true) @Parameter(description = "ID de checklist a ser pesquisado") int id)
			throws Exception {

		Optional<ChecklistModel> service = this.iChecklistService.getById(id);

		if (service != null && service.isPresent()) {
			return ResponseEntity.ok(checklistFactory.convertFromModel(service.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@Operation(summary = "Buscar checklist por id")
	@GetMapping(path = "/answerByChecklist/{id}")
	public @ResponseBody ResponseEntity<Optional<ChecklistResponseDTO>> getByCheckListId(
			@PathVariable(name = "id", required = true) @Parameter(description = "ID da Task a ser pesquisado") int id)
			throws Exception {

		Optional<ChecklistResponseDTO> service = this.iChecklistService.getAnswerByTask(id);

		if (service != null && service.isPresent()) {
			return ResponseEntity.ok(service);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@Operation(summary = "Salvar ou Atualizar checklist")
	@PostMapping(path = "/")
	public ResponseEntity<ChecklistDTO> saveOrUpdate(@RequestBody InsertOrEditChecklistDTO checklistDTO,
			BindingResult result) throws AppException, BusException {

		Optional<ChecklistModel> checklist = this.iChecklistService
				.saveOrUpdate(checklistFactory.convertFromInsertDto(checklistDTO), this.getUserProfile());
		return ResponseEntity.status(HttpStatus.CREATED).body(checklistFactory.convertFromModel(checklist.get()));

	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Boolean> delete(
			@PathVariable("id") @Parameter(description = " ID para ser deletado") Integer id)
			throws AppException, BusException {
		this.iChecklistService.delete(id, this.getUserProfile());
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}

}
