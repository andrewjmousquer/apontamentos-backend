package com.portal.dao;

import java.util.List;
import java.util.Optional;

import com.portal.exceptions.AppException;
import com.portal.model.TaskUserTimeModel;

public interface ITaskUserTimeDAO {

	public Optional<TaskUserTimeModel> getById(Integer id) throws AppException;

	public List<TaskUserTimeModel> searchByTaskUser(Integer taskUser) throws AppException;

	public Optional<TaskUserTimeModel> save(TaskUserTimeModel model) throws AppException;

	public void delete(Integer id) throws AppException;

	public void deleteByTaskUser(Integer idTaskUser) throws AppException;

	public Optional<TaskUserTimeModel> update(TaskUserTimeModel model) throws AppException;

	public Optional<TaskUserTimeModel> getUnfinishTimeByTask(Integer idTaskUser) throws AppException;

}
