package com.portal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.portal.dto.TeamListDTO;
import com.portal.dto.request.SearchTeamDTO;
import com.portal.model.TeamModel;

@Component
public class TeamListFactory {

	public TeamListDTO convertFromModel(TeamModel model) {

		TeamListDTO dtoToReturn = new TeamListDTO();
		dtoToReturn.setId(model.getId());
		dtoToReturn.setName(model.getName());
		dtoToReturn.setAbbreviation(model.getAbbreviation());
		dtoToReturn.setNumberOfUsers(model.getNumberOfUsers());
		dtoToReturn.setNumberOfStages(model.getNumberOfStages());

		return dtoToReturn;
	}

	public List<TeamListDTO> convertFromListOfModels(List<TeamModel> list) {
		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<TeamListDTO>();
	}

	/**
	 * @param dto
	 * @return
	 */
	public TeamModel convertFromFilterDto(SearchTeamDTO dto) {

		TeamModel modelToReturn = new TeamModel();

		modelToReturn.setId(dto.getId());
		modelToReturn.setName(dto.getName());
		modelToReturn.setAbbreviation(dto.getAbbreviation());
		modelToReturn.setNumberOfUsers(dto.getNumberOfUsers());
		modelToReturn.setNumberOfStages(dto.getNumberOfStages());

		return modelToReturn;

	}

}
