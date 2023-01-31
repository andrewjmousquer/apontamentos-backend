package com.portal.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portal.dto.ChecklistAnswerDTO;
import com.portal.dto.request.InsertOrEditChecklistAnswerDTO;
import com.portal.dto.request.SearchChecklistAnswerDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.service.IChecklistAnswerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/protected/checklist-answer")
@Tag(name = "Checklist Answer", description = "Checklist Answer controller")
public class ChecklistAnswerController extends BaseController {

	@Autowired
	private IChecklistAnswerService iChecklistQuestionService;

	@Operation(summary = "Retorna uma lista de Answer")
	@GetMapping(path = "/listAll")
	public @ResponseBody ResponseEntity<List<ChecklistAnswerDTO>> listAll() throws Exception {
		return ResponseEntity.ok(this.iChecklistQuestionService.list());
	}

	@Operation(summary = "Buscar lista de Answer")
	@PostMapping(path = "/search")
	public ResponseEntity<List<ChecklistAnswerDTO>> search(@Valid @RequestBody SearchChecklistAnswerDTO searchDTO)
			throws AppException, BusException {
		return ResponseEntity.ok(this.iChecklistQuestionService.search(searchDTO));
	}

	@Operation(summary = "Buscar Answer por id")
	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<ChecklistAnswerDTO> getById(
			@PathVariable(name = "id", required = true) @Parameter(description = "ID de Answer a ser pesquisado") int id)
			throws Exception {

		Optional<ChecklistAnswerDTO> objectReturned = this.iChecklistQuestionService.getById(id);

		if (objectReturned != null && objectReturned.isPresent()) {
			return ResponseEntity.ok(objectReturned.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@Operation(summary = "Salvar ou Atualizar Answer")
	@PostMapping(value = "/save-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ChecklistAnswerDTO> saveOrUpdate(
			@RequestPart(value = "dto", required = true) InsertOrEditChecklistAnswerDTO insertDTO, BindingResult result,
			@RequestPart(value = "file", required = false) MultipartFile[] files) throws AppException, BusException {

		if (files != null)
			insertDTO.setFiles(List.of(files));

		Optional<ChecklistAnswerDTO> objectReturned = this.iChecklistQuestionService.saveOrUpdate(insertDTO,
				this.getUserProfile());

		return ResponseEntity.status(HttpStatus.CREATED).body(objectReturned.get());
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Boolean> delete(
			@PathVariable("id") @Parameter(description = " ID para ser deletado") Integer id)
			throws AppException, BusException {
		this.iChecklistQuestionService.delete(id, this.getUserProfile());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}