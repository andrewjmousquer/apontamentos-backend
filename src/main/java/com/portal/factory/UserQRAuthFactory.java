package com.portal.factory;

import org.springframework.stereotype.Component;

import com.portal.dto.UserQRAuthDTO;
import com.portal.model.UserModel;

@Component
public class UserQRAuthFactory {

	public UserQRAuthDTO convertFromModel(UserModel model) {

		UserQRAuthDTO dtoToReturn = new UserQRAuthDTO();
		dtoToReturn.setQrCode(model.getHashQRCode());

		return dtoToReturn;
	}

	/**
	 * @param dto
	 * @return
	 */
	public UserModel convertFromInsertDto(UserQRAuthDTO dto) {

		UserModel modelToReturn = new UserModel();
		modelToReturn.setHashQRCode(dto.getQrCode());

		return modelToReturn;

	}

}
