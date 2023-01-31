package com.portal.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.portal.dto.StageDTO;
import com.portal.dto.TeamDTO;
import com.portal.dto.UserDTO;
import com.portal.dto.request.InsertOrEditTeamDTO;
import com.portal.dto.request.SearchTeamDTO;
import com.portal.model.StageModel;
import com.portal.model.TeamModel;
import com.portal.model.UserModel;

@Component
public class TeamFactory {

	public TeamDTO convertFromModel(TeamModel model) {

		TeamDTO dtoToReturn = new TeamDTO();
		dtoToReturn.setId(model.getId());
		dtoToReturn.setName(model.getName());
		dtoToReturn.setAbbreviation(model.getAbbreviation());
		dtoToReturn.setUsers(model.getUsers() != null && model.getUsers().size() > 0 ? model.getUsers().stream()
				.map(dt -> new UserDTO(dt.getId(), dt.getPerson().getName())).collect(Collectors.toList())
				: new ArrayList<UserDTO>());
		dtoToReturn.setStages(model.getStages() != null && model.getStages().size() > 0 ? model.getStages().stream()
				.map(dt -> new StageDTO(dt.getId(), dt.getName())).collect(Collectors.toList())
				: new ArrayList<StageDTO>());

		return dtoToReturn;
	}

	public List<TeamDTO> convertFromListOfModels(List<TeamModel> list) {
		return list != null && list.size() > 0
				? list.stream().map(model -> convertFromModel(model)).collect(Collectors.toList())
				: new ArrayList<TeamDTO>();
	}

	/**
	 * @param dto
	 * @return
	 */
	public TeamModel convertFromInsertDto(InsertOrEditTeamDTO dto) {

		TeamModel modelToReturn = new TeamModel();

		modelToReturn.setId(dto.getId() != null ? dto.getId() : 0);

		if (StringUtils.isNotEmpty(dto.getName()))
			modelToReturn.setName(dto.getName());

		modelToReturn.setAbbreviation(dto.getAbbreviation());
		modelToReturn.setUsers(dto.getUsers() != null && dto.getUsers().size() > 0
				? dto.getUsers().stream().map(id -> new UserModel(id)).collect(Collectors.toList())
				: new ArrayList<UserModel>());
		modelToReturn.setStages(dto.getStages() != null && dto.getStages().size() > 0
				? dto.getStages().stream().map(id -> new StageModel(id)).collect(Collectors.toList())
				: new ArrayList<StageModel>());

		return modelToReturn;

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
		modelToReturn.setUsers(dto.getUsers() != null && dto.getUsers().size() > 0
				? dto.getUsers().stream().map(id -> new UserModel(id)).collect(Collectors.toList())
				: new ArrayList<UserModel>());
		modelToReturn.setStages(dto.getStages() != null && dto.getStages().size() > 0
				? dto.getStages().stream().map(id -> new StageModel(id)).collect(Collectors.toList())
				: new ArrayList<StageModel>());

		return modelToReturn;

	}

}
