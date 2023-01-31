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

import com.portal.dto.TeamDTO;
import com.portal.dto.TeamListDTO;
import com.portal.dto.request.InsertOrEditTeamDTO;
import com.portal.dto.request.SearchTeamDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.TeamFactory;
import com.portal.factory.TeamListFactory;
import com.portal.model.TeamModel;
import com.portal.service.ITeamService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/protected/team")
@Tag(name = "Team", description = "Team Entity controller")
public class TeamController extends BaseController {

	@Autowired
	private ITeamService service;

	@Autowired
	private TeamFactory factory;

	@Autowired
	private TeamListFactory teamListFactory;

	@Operation(summary = "Retornar uma lista de equipes")
	@GetMapping(path = "/list")
	public @ResponseBody ResponseEntity<List<TeamListDTO>> list() throws Exception {
		List<TeamModel> models = this.service.list();
		return ResponseEntity.ok(teamListFactory.convertFromListOfModels(models));

	}

	@Operation(summary = "Buscar equipe")
	@PostMapping(path = "/search")
	public ResponseEntity<List<TeamListDTO>> search(@Valid @RequestBody SearchTeamDTO teamDto)
			throws AppException, BusException {
		return ResponseEntity.ok(teamListFactory
				.convertFromListOfModels(this.service.search(teamListFactory.convertFromFilterDto(teamDto))));
	}

	@Operation(summary = "Buscar equipe por id")
	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<TeamDTO> getById(
			@PathVariable(name = "id", required = true) @Parameter(description = "ID da equipe a ser pesquisado") int id)
			throws Exception {

		Optional<TeamModel> team = this.service.getById(id);

		if (team != null && team.isPresent()) {
			return ResponseEntity.ok(factory.convertFromModel(team.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@Operation(summary = "Salvar ou Atualizar uma equipe")
	@PostMapping(path = "/")
	public ResponseEntity<TeamDTO> saveOrUpdate(@RequestBody InsertOrEditTeamDTO teamDTO, BindingResult result)
			throws AppException, BusException {

		Optional<TeamModel> team = this.service.saveOrUpdate(factory.convertFromInsertDto(teamDTO),
				this.getUserProfile());
		return ResponseEntity.status(HttpStatus.CREATED).body(factory.convertFromModel(team.get()));

	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Boolean> delete(
			@PathVariable("id") @Parameter(description = " ID para ser deletado") Integer id)
			throws AppException, BusException {
		this.service.delete(id, this.getUserProfile());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
