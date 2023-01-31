package com.portal.dao;

import java.util.List;
import java.util.Optional;

import com.portal.exceptions.AppException;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistModel;

public interface IChecklistGroupDAO {

	public Optional<ChecklistGroupModel> save(ChecklistModel checklistModel, ChecklistGroupModel model)
			throws AppException;

	public Optional<ChecklistGroupModel> update(ChecklistModel checklistModel, ChecklistGroupModel model)
			throws AppException;

	public void delete(Integer checklist) throws AppException;

	public void delete(Integer cklId, Integer ckgId) throws AppException;

	List<ChecklistGroupModel> getByChecklistId(Integer id) throws AppException;

	public List<ChecklistGroupModel> search(ChecklistGroupModel model) throws AppException;

}
