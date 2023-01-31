package com.portal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.portal.dto.UserAndOfficeDTO;
import com.portal.dto.UserDTO;
import com.portal.dto.request.InsertOrEditUserDTO;
import com.portal.dto.request.SearchUserDTO;
import com.portal.model.UserModel;

@Component
public class UserFactory {

	public UserDTO convertFromModel(UserModel model) {

		UserDTO dtoToReturn = new UserDTO();
		dtoToReturn.setId(model.getId());
		dtoToReturn.setName(model.getPerson().getName());

		return dtoToReturn;
	}

	public UserAndOfficeDTO convertToUserAndOfficeFromModel(UserModel model) {

		UserAndOfficeDTO dtoToReturn = new UserAndOfficeDTO();

		dtoToReturn.setName(model.getPerson().getName());
		dtoToReturn.setOffice(model.getPerson().getJobTitle());

		return dtoToReturn;
	}

	public List<UserDTO> convertFromListOfModels(List<UserModel> list) {
		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<UserDTO>();
	}

	/**
	 * @param dto
	 * @return
	 */
	public UserModel convertFromInsertDto(InsertOrEditUserDTO dto) {

		UserModel modelToReturn = new UserModel();

		modelToReturn.setId(dto.getId() != null ? dto.getId() : 0);
		modelToReturn.getPerson().setName(dto.getName());
		return modelToReturn;

	}

	/**
	 * @param dto
	 * @return
	 */
	public UserModel convertFromFilterDto(SearchUserDTO dto) {

		UserModel modelToReturn = new UserModel();

		modelToReturn.setId(dto.getId() != null ? dto.getId() : 0);
		modelToReturn.getPerson().setName(dto.getName());

		return modelToReturn;

	}
}
