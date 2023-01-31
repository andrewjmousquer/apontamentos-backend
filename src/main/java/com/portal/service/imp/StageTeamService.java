package com.portal.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.portal.dao.IStageTeamDAO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.StageModel;
import com.portal.model.TeamModel;
import com.portal.service.IStageTeamService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StageTeamService implements IStageTeamService {

	@Autowired
	private IStageTeamDAO dao;

	@Autowired
	private MessageSource messageSource;

	@Override
	public TeamModel save(TeamModel team) throws AppException, BusException {
		try {
			List<StageModel> stages = team != null && team.getStages().size() > 0
					? new ArrayList<StageModel>(team.getStages())
					: new ArrayList<StageModel>();

			delete(team);

			for (StageModel item : stages) {
				this.dao.save(team, item);
			}

			team.setStages(stages);

			return team;

		} catch (Exception e) {
			throw new AppException(
					messageSource.getMessage("error.generic.save", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public TeamModel delete(TeamModel team) throws AppException, BusException {
		try {
			if (team != null) {
				this.dao.delete(team.getId());
			}

		} catch (Exception e) {
			throw new AppException(
					messageSource.getMessage("error.generic.delete", null, LocaleContextHolder.getLocale()));
		}

		return team;
	}
}
