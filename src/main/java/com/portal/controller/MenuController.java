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

import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.MenuModel;
import com.portal.service.IMenuService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/protected/menu")
@Tag(name = "Menu", description = "Menu Entity controller")
@CrossOrigin(origins = "*")
public class MenuController extends BaseController {

	@Autowired
	private IMenuService service;

	/**
	 * Retorna lista com todos usuários.
	 * 
	 * @return ResponseEntity<Response<MenuModel>>
	 * @throws BusException
	 * @throws AppException
	 */
	@GetMapping(value = "/listAll")
	public ResponseEntity<List<MenuModel>> listAll() throws AppException, BusException {
		List<MenuModel> menuList = this.service.list();
		return ResponseEntity.ok(menuList);
	}

	/**
	 * Retorna lista com todos usuários.
	 * 
	 * @return ResponseEntity<Response<MenuModel>>
	 * @throws BusException
	 * @throws AppException
	 */
	@GetMapping(value = "/listRoots")
	public ResponseEntity<List<MenuModel>> listRoots() throws AppException, BusException {
		List<MenuModel> menuList = this.service.listRoots();
		return ResponseEntity.ok(menuList);
	}

	/**
	 * Retorna lista de usuários filtrados
	 * 
	 * @param id
	 * @return ResponseEntity<Response<MenuModel>>
	 * @throws BusException
	 * @throws AppException
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<List<MenuModel>> search(@Valid @RequestBody MenuModel model)
			throws AppException, BusException {
		return ResponseEntity.ok(this.service.search(model));
	}

	/**
	 * Retorna menu por ID.
	 * 
	 * @param id
	 * @return ResponseEntity<Response<MenuModel>>
	 * @throws BusException
	 * @throws AppException
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<MenuModel> getById(@PathVariable("id") Integer id) throws AppException, BusException {
		return ResponseEntity.ok(this.service.getById(id).get());
	}

	/**
	 * Adiciona um novo menu.
	 * 
	 * @param lancamento
	 * @param result
	 * @return ResponseEntity<Response<MenuModel>>
	 * @throws ParseException
	 * @throws BusException
	 * @throws AppException
	 */
	@PostMapping
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<MenuModel> save(@Valid @RequestBody MenuModel model, BindingResult result)
			throws AppException, BusException {
		return ResponseEntity.ok(this.service.saveOrUpdate(model, this.getUserProfile()).get());
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

}
