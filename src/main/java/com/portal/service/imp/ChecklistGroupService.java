package com.portal.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.portal.dao.IChecklistGroupDAO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistModel;
import com.portal.service.IChecklistGroupService;
import com.portal.service.IChecklistQuestionService;


@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChecklistGroupService implements IChecklistGroupService {

	@Autowired
	private IChecklistGroupDAO ichecklistGroupDAO;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IChecklistQuestionService checkListQuestionService;

	@Override
	public ChecklistModel save(ChecklistModel checklistModel) throws AppException, BusException {
		try {

			List<ChecklistGroupModel> checkListList = checklistModel != null && !checklistModel.getGroups().isEmpty()
					? new ArrayList<ChecklistGroupModel>(checklistModel.getGroups())
					: new ArrayList<ChecklistGroupModel>();

			delete(checklistModel);


			for (ChecklistGroupModel model : checkListList) {
				this.ichecklistGroupDAO.save(checklistModel, model);
				model.setCkgId(model.getCkgId());
				this.checkListQuestionService.save(model);
			}

			checklistModel.setGroups(checkListList);

			return checklistModel;

		} catch (Exception e) {
			throw new AppException(
					messageSource.getMessage("error.generic.save", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public ChecklistModel delete(ChecklistModel checklist) throws AppException, BusException {


		try {
			if (checklist != null) {

				checkListQuestionService.delete(checklist.getId());

				ichecklistGroupDAO.delete(checklist.getId());

			}

		} catch (Exception e) {
			throw new AppException(
					messageSource.getMessage("error.generic.delete", null, LocaleContextHolder.getLocale()));
		}

		return checklist;
	}

	@Override
	public ChecklistModel update(ChecklistModel checklistModel) throws AppException, BusException {
		try {

			List<ChecklistGroupModel> checkListList = checklistModel != null && !checklistModel.getGroups().isEmpty()
					? new ArrayList<ChecklistGroupModel>(checklistModel.getGroups())
					: new ArrayList<ChecklistGroupModel>();

			delete(checklistModel);

			for (ChecklistGroupModel model : checkListList) {
				this.ichecklistGroupDAO.update(checklistModel, model);
				model.setCkgId(model.getCkgId());
				this.checkListQuestionService.update(model);
			}

			checklistModel.setGroups(checkListList);

			return checklistModel;

		} catch (Exception e) {
			throw new AppException(
					messageSource.getMessage("error.generic.save", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<ChecklistGroupModel> search(ChecklistGroupModel model) throws AppException, BusException {
		try {
			return this.ichecklistGroupDAO.search(model);

		} catch (Exception e) {
			throw new AppException(this.messageSource.getMessage("error.generic.search",
					new Object[] { ChecklistGroupModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<ChecklistGroupModel> getByChecklist(Integer id) throws AppException, BusException {
		try {
			if (id == null)
				throw new BusException("ID de busca inválido.");

			List<ChecklistGroupModel> checklist = this.ichecklistGroupDAO.getByChecklistId(id);
			for (ChecklistGroupModel checklistGroupModel : checklist) {
				checklistGroupModel.setQuestions(this.checkListQuestionService.getById(checklistGroupModel.getCkgId()));
			}

			return checklist;

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { ChecklistGroupModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

}
