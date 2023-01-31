package com.portal.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.portal.dto.ChecklistAnswerPhotoDTO;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.model.ChecklistAnswerModel;
import com.portal.model.ChecklistAnswerPhotoModel;

public interface IChecklistAnswerPhotoService {

	public List<ChecklistAnswerPhotoDTO> listPhotosByAnswer(Integer answerID) throws AppException, BusException;

	public void savePhotos(ChecklistAnswerModel model, List<MultipartFile> arquivos) throws AppException, BusException;

	public void delete(Integer id) throws AppException, BusException;

	public void deleteByAnswer(Integer checklistAnswerID) throws AppException, BusException;

	List<ChecklistAnswerPhotoModel> search(ChecklistAnswerPhotoModel model) throws AppException, BusException;
}
