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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portal.dto.UserDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.UserFactory;
import com.portal.model.UserModel;
import com.portal.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/protected/user")
@Tag(name = "User", description = "User Entity controller")
@CrossOrigin(origins = "*")
public class UserController extends BaseController {

	@Autowired
	private IUserService service;

	@Autowired
	private UserFactory factory;

	/**
	 * Retorna lista com todos usuários.
	 * 
	 * @return ResponseEntity<Response<UserModel>>
	 * @throws BusException
	 * @throws AppException
	 */
	@GetMapping(value = "/listAll")
	public ResponseEntity<List<UserModel>> listAll() throws AppException, BusException {
		List<UserModel> userList = this.service.list();
		return ResponseEntity.ok(userList);
	}

	/**
	 * Retorna lista de usuários filtrados
	 * 
	 * @param id
	 * @return ResponseEntity<Response<UserModel>>
	 * @throws BusException
	 * @throws AppException
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<List<UserModel>> search(@Valid @RequestBody UserModel userModel)
			throws AppException, BusException {
		return ResponseEntity.ok(this.service.search(userModel));
	}

	/**
	 * Retorna user por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<UserModel>>
	 * @throws BusException
	 * @throws AppException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserModel> getById(@PathVariable("id") Integer id) throws AppException, BusException {
		return ResponseEntity.ok(this.service.getById(id).get());
	}

	/**
	 * Retorna user por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<UserModel>>
	 * @throws BusException
	 * @throws AppException
	 */
	@RequestMapping(value = "/getByUsername", method = RequestMethod.POST)
	public ResponseEntity<UserModel> getByUsername(@Valid @RequestBody UserModel model, BindingResult result)
			throws AppException, BusException {
		return ResponseEntity.ok(this.service.findByUsername(model).get());
	}

	/**
	 * Adiciona um novo user.
	 * 
	 * @param lancamento
	 * @param result
	 * @return ResponseEntity<Response<UserModel>>
	 * @throws ParseException
	 * @throws BusException
	 * @throws AppException
	 */
	@PostMapping
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<UserModel> save(@Valid @RequestBody UserModel model, BindingResult result)
			throws AppException, BusException {
		return ResponseEntity.ok(this.service.saveOrUpdate(model, this.getUserProfile()).get());
	}

	/**
	 * Adiciona um novo user.
	 * 
	 * @param lancamento
	 * @param result
	 * @return ResponseEntity<Response<UserModel>>
	 * @throws ParseException
	 * @throws BusException
	 * @throws AppException
	 */
	@PostMapping
	@RequestMapping(value = "/saveUserConfig", method = RequestMethod.POST)
	public ResponseEntity<UserModel> saveUserConfig(@Valid @RequestBody UserModel model, BindingResult result)
			throws AppException, BusException {
		if (model != null && model.getPassword() != null && !model.getPassword().equals("")) {
			this.service.changePassword(model, this.getUserProfile()).get();
		}
		return ResponseEntity.ok(this.service.saveUserConfig(model).get());
	}

	/**
	 * Remove um usuario por ID.
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

	@PostMapping(value = "/changePassword")
	public ResponseEntity<UserModel> changePassword(@Valid @RequestBody UserModel model)
			throws AppException, BusException {
		return ResponseEntity.ok(this.service.changePassword(model, this.getUserProfile()).get());
	}

	/**
	 * Retorna usuário atual.
	 *
	 * @return ResponseEntity<Response<UserModel>>
	 * @throws BusException
	 * @throws AppException
	 */
	@GetMapping(value = "/current")
	public ResponseEntity<UserModel> getCurrent() throws AppException, BusException {
		return ResponseEntity.ok(this.getUserProfile().getUser());
	}

	@GetMapping(value = "/list")
	public ResponseEntity<List<UserDTO>> getUsers() throws AppException, BusException {
		List<UserModel> models = this.service.list();
		return ResponseEntity.ok(factory.convertFromListOfModels(models));

	}

	@Operation(summary = "Retorna lista de usuários que podem executar uma tarefa")
	@GetMapping(path = "/avalible-task")
	public @ResponseBody ResponseEntity<List<UserDTO>> getUsersAvalibleByTaskAndStage(
			@RequestParam(name = "task", required = true) @Parameter(description = "Identificador da tarefa") Integer taskID,
			@RequestParam(name = "stage", required = true) @Parameter(description = "Identificador da etapa") Integer stageID)
			throws Exception {
		List<UserModel> avalible = this.service.listUsersAvalibleByTask(stageID, taskID);
		return ResponseEntity.ok(factory.convertFromListOfModels(avalible));
	}

}
