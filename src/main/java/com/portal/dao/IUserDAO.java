package com.portal.dao;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.portal.exceptions.AppException;
import com.portal.model.UserModel;

public interface IUserDAO extends IBaseDAO<UserModel> {

	public Optional<UserModel> saveUserConfig(UserModel model) throws AppException;

	public Optional<UserModel> findLogin(UserModel model) throws AppException;

	public Optional<UserModel> changePassword(UserModel model) throws AppException;

	public List<UserModel> findByTeam(Integer id) throws AppException;

	public List<UserModel> listUsers() throws AppException;

	public List<UserModel> listUsersAvalibleByStage(Integer stage, Integer taskID) throws AppException;

	public Optional<UserModel> getUserAndOffice(@Valid String qrCode) throws AppException;

}
