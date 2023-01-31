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

import com.portal.dto.ServiceOrderDTO;
import com.portal.dto.ServiceOrderDashboardDTO;
import com.portal.dto.request.InsertOrEditServiceOrderDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.ServiceOrderFactory;
import com.portal.model.ServiceOrderModel;
import com.portal.service.IServiceOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/protected/service-order")
@Tag(name = "Service Order", description = "Service Order Entity controller")
public class ServiceOrderController extends BaseController {

	@Autowired
	private IServiceOrderService iServiceOrderService;

	@Autowired
	private ServiceOrderFactory serviceOrderFactory;

	@Operation(summary = "Retorna uma lista de ordem de serviços")
	@GetMapping(path = "/listAll")
	public @ResponseBody ResponseEntity<List<ServiceOrderDTO>> list() throws Exception {
		List<ServiceOrderModel> model = this.iServiceOrderService.list();
		return ResponseEntity.ok(serviceOrderFactory.convertFromListOfModels(model));

	}

	@Operation(summary = "Buscar Ordens de Serviço")
	@PostMapping(path = "/search")
	public ResponseEntity<List<ServiceOrderDTO>> search(@Valid @RequestBody ServiceOrderModel soModel)
			throws AppException, BusException {
		return ResponseEntity
				.ok(this.serviceOrderFactory.convertFromListOfModels(this.iServiceOrderService.search(soModel)));
	}

	@Operation(summary = "Buscar ordem de serviço por id")
	@GetMapping(path = "/{id}")
	public @ResponseBody ResponseEntity<ServiceOrderDashboardDTO> getById(
			@PathVariable(name = "id", required = true) @Parameter(description = "ID da ordem de serviço a ser pesquisado") int id)
			throws Exception {

		ServiceOrderDashboardDTO returned = this.iServiceOrderService.getDashboardById(id);

		return ResponseEntity.ok(returned);

	}

	@Operation(summary = "Salvar ou Atualizar ordem de serviço")
	@PostMapping(path = "/save")
	public ResponseEntity<ServiceOrderDTO> saveOrUpdate(@Valid @RequestBody InsertOrEditServiceOrderDTO so,
			BindingResult result) throws AppException, BusException {

		Optional<ServiceOrderModel> service = this.iServiceOrderService
				.saveOrUpdate(serviceOrderFactory.convertFromInsertDto(so), this.getUserProfile());
		return ResponseEntity.status(HttpStatus.CREATED).body(serviceOrderFactory.convertFromModel(service.get()));

	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Boolean> delete(
			@PathVariable("id") @Parameter(description = " ID para ser deletado") Integer id)
			throws AppException, BusException {
		this.iServiceOrderService.delete(id, this.getUserProfile());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
