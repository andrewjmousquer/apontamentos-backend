package com.portal.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.portal.dao.IUserTeamDAO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.TeamModel;
import com.portal.model.UserModel;
import com.portal.service.IUserTeamService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserTeamService implements IUserTeamService {

	@Autowired
	private IUserTeamDAO dao;

	@Autowired
	private MessageSource messageSource;

	@Override
	public TeamModel save(TeamModel team) throws AppException, BusException {
		try {

			List<UserModel> userList = team != null && team.getUsers().size() > 0
					? new ArrayList<UserModel>(team.getUsers())
					: new ArrayList<UserModel>();

			delete(team);

			if (userList != null && userList.size() > 0) {
				for (UserModel item : userList) {
					this.dao.save(team, item);
				}
			}

			team.setUsers(userList);

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
