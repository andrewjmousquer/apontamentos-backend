package com.portal.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.portal.dao.IChecklistQuestionDAO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ChecklistGroupModel;
import com.portal.model.ChecklistQuestionModel;
import com.portal.service.IChecklistQuestionService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ChecklistQuestionService implements IChecklistQuestionService {

	@Autowired
	private IChecklistQuestionDAO iChecklistQuestionDAO;

	@Autowired
	private MessageSource messageSource;

	@Override
	public ChecklistGroupModel save(ChecklistGroupModel checklistModel) throws AppException, BusException {

		try {

			List<ChecklistQuestionModel> checklistlist = checklistModel != null
					&& !checklistModel.getQuestions().isEmpty()
							? new ArrayList<ChecklistQuestionModel>(checklistModel.getQuestions())
							: new ArrayList<ChecklistQuestionModel>();

			delete(checklistModel.getCkgId());

			for (ChecklistQuestionModel model : checklistlist)
				this.iChecklistQuestionDAO.save(checklistModel, model);

			checklistModel.setQuestions(checklistlist);

			return checklistModel;

		} catch (Exception e) {
			throw new AppException(
					messageSource.getMessage("error.generic.save", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public void delete(Integer checklistModelId) throws AppException, BusException {

		try {
			if (checklistModelId != null) {
				this.iChecklistQuestionDAO.delete(checklistModelId);
			}

		} catch (Exception e) {
			throw new AppException(
					messageSource.getMessage("error.generic.delete", null, LocaleContextHolder.getLocale()));
		}

	}

	@Override
	public ChecklistGroupModel update(ChecklistGroupModel checklistModel) throws AppException, BusException {

		try {

			List<ChecklistQuestionModel> checklistlist = checklistModel != null
					&& !checklistModel.getQuestions().isEmpty()
							? new ArrayList<ChecklistQuestionModel>(checklistModel.getQuestions())
							: new ArrayList<ChecklistQuestionModel>();

			delete(checklistModel.getCkgId());

			for (ChecklistQuestionModel model : checklistlist)
				this.iChecklistQuestionDAO.save(checklistModel, model);

			checklistModel.setQuestions(checklistlist);

			return checklistModel;

		} catch (Exception e) {
			throw new AppException(
					messageSource.getMessage("error.generic.save", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<ChecklistQuestionModel> search(ChecklistQuestionModel model) throws AppException, BusException {

		try {
			return this.iChecklistQuestionDAO.search(model);

		} catch (Exception e) {
			throw new AppException(this.messageSource.getMessage("error.generic.search",
					new Object[] { ChecklistQuestionModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<ChecklistQuestionModel> getById(Integer id) throws AppException, BusException {
		try {
			if (id == null)
				throw new BusException("ID de busca inválido.");

			List<ChecklistQuestionModel> checklist = this.iChecklistQuestionDAO.getChecklistListQuestionByChecklistId(id);

			return checklist;

		} catch (BusException e) {
			throw e;

		} catch (Exception e) {
			throw new AppException(this.messageSource.getMessage("error.generic.getById",
					new Object[] { ChecklistQuestionModel.class.getSimpleName() }, LocaleContextHolder.getLocale()));
		}
	}

}
