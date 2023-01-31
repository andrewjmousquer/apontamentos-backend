package com.portal.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.portal.dto.ServiceOrderDTO;
import com.portal.dto.request.InsertOrEditServiceOrderDTO;
import com.portal.dto.request.SearchServiceOrderDTO;
import com.portal.enums.StatusType;
import com.portal.integration.jira.IntegrationJIRA;
import com.portal.model.ClassifierModel;
import com.portal.model.ServiceOrderModel;

@Component
public class ServiceOrderFactory {

	@Autowired
	ClassifierFactory classifierFactory;

	public ServiceOrderDTO convertFromModel(ServiceOrderModel model) {

		ServiceOrderDTO dtoToReturn = new ServiceOrderDTO();
		dtoToReturn.setId(model.getId());
		dtoToReturn.setNumberJira(model.getNumberJira());
		dtoToReturn.setDateStart(model.getDateStart());
		dtoToReturn.setDateFinish(model.getDateFinish());
		dtoToReturn.setStatusOs(classifierFactory.convertFromModel(model.getStatusOs()));
		dtoToReturn.setBrand(model.getBrand());
		dtoToReturn.setModel(model.getModel());
		dtoToReturn.setPlate(model.getPlate());
		dtoToReturn.setChassi(model.getChassi());
		dtoToReturn.setNumber(model.getNumber());

		return dtoToReturn;
	}

	public List<ServiceOrderDTO> convertFromListOfModels(List<ServiceOrderModel> list) {
		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<ServiceOrderDTO>();
	}

	/**
	 * @param dto
	 * @return
	 */
	public ServiceOrderModel convertFromInsertDto(InsertOrEditServiceOrderDTO dto) {

		ServiceOrderModel modelToReturn = new ServiceOrderModel();

		modelToReturn.setId(dto.getId() != null ? dto.getId() : 0);
		modelToReturn.setNumberJira(dto.getNumberJira());
		modelToReturn.setDateStart(dto.getDateStart());
		modelToReturn.setDateFinish(dto.getDateFinish());
		if (dto.getStatusOs() != null && dto.getStatusOs() > 0)
			modelToReturn.setStatusOs(new ClassifierModel(dto.getStatusOs()));

		modelToReturn.setBrand(dto.getBrand());
		modelToReturn.setModel(dto.getModel());
		modelToReturn.setPlate(dto.getPlate());
		modelToReturn.setChassi(dto.getChassi());

		return modelToReturn;

	}

	/**
	 * @param dto
	 * @return
	 */
	public ServiceOrderModel convertFromFilterDto(SearchServiceOrderDTO dto) {

		ServiceOrderModel modelToReturn = new ServiceOrderModel();

		modelToReturn.setId(dto.getId());
		modelToReturn.setNumberJira(dto.getNumberJira());
		modelToReturn.setDateStart(dto.getDateStart());
		modelToReturn.setDateFinish(dto.getDateFinish());
		if (dto.getStatusOs() != null && dto.getStatusOs() > 0)
			modelToReturn.setStatusOs(new ClassifierModel(dto.getStatusOs()));

		modelToReturn.setBrand(dto.getBrand());
		modelToReturn.setModel(dto.getModel());
		modelToReturn.setPlate(dto.getPlate());
		modelToReturn.setChassi(dto.getChassi());

		return modelToReturn;

	}

	public ServiceOrderModel convertFromIntegrationJira(IntegrationJIRA jira) {
		ServiceOrderModel toReturn = new ServiceOrderModel();

		toReturn.setBrand(jira.getFields().getMarca());
		toReturn.setChassi(jira.getFields().getChassi());
		toReturn.setDateStart(new Date());
		toReturn.setModel(jira.getFields().getModelo());
		toReturn.setNumber(jira.getFields().getOS());
		toReturn.setNumberJira(jira.getFields().getIssuetype().getSubtask() && jira.getFields().getParent() != null
				&& jira.getFields().getParent().getKey() != null ? jira.getFields().getParent().getKey()
						: jira.getKey());
		toReturn.setPlate(jira.getFields().getPlaca());
		toReturn.setStatusOs(StatusType.AGUARDANDO_INICIO.getType());

		return toReturn;
	}

}