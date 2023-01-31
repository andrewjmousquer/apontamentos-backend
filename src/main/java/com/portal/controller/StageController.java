package com.portal.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.portal.dto.StageDTO;
import com.portal.dto.request.InsertOrEditStageDTO;
import com.portal.dto.request.SearchStageDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.StageFactory;
import com.portal.model.StageModel;
import com.portal.service.IStageService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/protected/stage")
@Tag(name = "Stage", description = "Stage Entity Controller")
@CrossOrigin(origins = "*")
public class StageController extends BaseController {

	@Autowired
	private IStageService service;

	@Autowired
	private StageFactory factory;

	/**
	 * Retorna lista com todas Etapas.
	 * 
	 * @return ResponseEntity<Response<StageDTO>>
	 * @throws BusException
	 * @throws AppException
	 */
	@GetMapping(value = "/listAll")
	public ResponseEntity<List<StageDTO>> listAll() throws AppException, BusException {
		List<StageModel> list = this.service.list();
		return ResponseEntity.ok(factory.convertFromListOfModels(list));
	}

	/**
	 * Retorna lista de Etapas filtradas
	 * 
	 * @param id
	 * @return ResponseEntity<Response<StageDTO>>
	 * @throws BusException
	 * @throws AppException
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<List<StageDTO>> search(@Valid @RequestBody SearchStageDTO dto)
			throws AppException, BusException {
		return ResponseEntity
				.ok(factory.convertFromListOfModels(this.service.search(factory.convertFromFilterDto(dto))));
	}

	/**
	 * Retorna Etapas por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<StageDTO>>
	 * @throws BusException
	 * @throws AppException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<StageDTO> getById(@PathVariable("id") Integer id) throws AppException, BusException {
		return ResponseEntity.ok(factory.convertFromModel(this.service.getById(id).get()));
	}

	/**
	 * Adiciona uma nova Etapa.
	 * 
	 * @param lancamento
	 * @param result
	 * @return ResponseEntity<Response<StageDTO>>
	 * @throws ParseException
	 * @throws BusException
	 * @throws AppException
	 */
	@PostMapping
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<StageDTO> save(@Valid @RequestBody InsertOrEditStageDTO dto, BindingResult result)
			throws AppException, BusException {
		return ResponseEntity.ok(factory.convertFromModel(
				this.service.saveOrUpdate(factory.convertFromInsertDto(dto), this.getUserProfile()).get()));
	}

	/**
	 * Remove uma Etapa por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Lancamento>>
	 * @throws BusException
	 * @throws AppException
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") Integer id) throws AppException, BusException {
		this.service.delete(id, this.getUserProfile());
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}

	/**
	 * Remove uma Movimentação por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<Lancamento>>
	 * @throws BusException
	 * @throws AppException
	 */
	@DeleteMapping(value = "/movement/{id}")
	public ResponseEntity<Boolean> removeMoviment(@PathVariable("id") Integer id) throws AppException, BusException {
		this.service.deleteMovement(id, this.getUserProfile());
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}

}
