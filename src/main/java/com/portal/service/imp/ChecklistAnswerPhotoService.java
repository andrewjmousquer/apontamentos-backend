package com.portal.service.imp;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portal.dao.IChecklistAnswerPhotoDAO;
import com.portal.dto.ChecklistAnswerPhotoDTO;
import com.portal.enums.FileType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.ChecklistAnswerPhotoFactory;
import com.portal.model.ChecklistAnswerModel;
import com.portal.model.ChecklistAnswerPhotoModel;
import com.portal.service.IChecklistAnswerPhotoService;
import com.portal.service.IFilesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChecklistAnswerPhotoService implements IChecklistAnswerPhotoService {

	@Autowired
	private IChecklistAnswerPhotoDAO dao;

	@Autowired

	private ChecklistAnswerPhotoFactory factory;
	@Autowired
	private IFilesService filesService;

	@Override
	public List<ChecklistAnswerPhotoDTO> listPhotosByAnswer(Integer answerID) throws AppException, BusException {
		List<ChecklistAnswerPhotoModel> models = this.dao.search(new ChecklistAnswerPhotoModel(answerID));
		return factory.convertFromListOfModels(models);
	}

	@Override
	public List<ChecklistAnswerPhotoModel> search(ChecklistAnswerPhotoModel model) throws AppException, BusException {
		List<ChecklistAnswerPhotoModel> models = this.dao.search(model);
		return models;
	}

	@Override
	public void savePhotos(ChecklistAnswerModel model, List<MultipartFile> arquivos) throws AppException, BusException {
		if (arquivos != null && arquivos.size() > 0) {

			List<ChecklistAnswerPhotoModel> models = this.dao.search(new ChecklistAnswerPhotoModel(model.getId()));
			int filesNumberCount = models.size() + arquivos.size();

			for (MultipartFile multipartFile : arquivos) {

				String fileName = this.generateFileName(model, filesNumberCount);

				filesNumberCount--;

				Optional<ChecklistAnswerPhotoModel> savedFile = this.dao
						.save(factory.convertFromInsertDto(fileName + ".png", model));

				try {
					this.filesService.uploadFile(multipartFile, FileType.CHECKLIST_PHOTO, fileName);
				} catch (Exception e) {
					log.error("Erro ao tentar gerar a SALVAR do arquivo no servidor: {}", savedFile.get().getFileName(),
							e);
					throw new BusException("Erro ao tentar salvar arquivo no servidor!");
				}
			}
		}

	}

	@Override
	public void delete(Integer id) throws AppException, BusException {

		Optional<ChecklistAnswerPhotoModel> fileToDelete = this.dao.getById(id);

		if (fileToDelete.isEmpty())
			throw new BusException(
					"Erro ao tentar excluir arquivo no servidor, o arquivo não existe em nossa base de dados!");

		this.dao.delete(id);
	}

	@Override
	public void deleteByAnswer(Integer checklistAnswerID) throws AppException, BusException {
		this.dao.deleteByAnswer(checklistAnswerID);
		List<ChecklistAnswerPhotoModel> models = this.dao.search(new ChecklistAnswerPhotoModel(checklistAnswerID));
		try {
			for (ChecklistAnswerPhotoModel checklistAnswerPhotoModel : models) {
				this.filesService.deleteIfExists(this.filesService.getPhysicalPath(FileType.CHECKLIST_PHOTO,
						checklistAnswerPhotoModel.getFileName()));
			}
		} catch (IOException e) {
			throw new BusException("Erro ao tentar excluir arquivo no servidor!");
		}
	}

	private String generateFileName(ChecklistAnswerModel model, int fileNumber) {
		return "task-" + model.getTask().getId() + "/" + "question-" + model.getQuestion().getId() + "-file-"
				+ fileNumber + "-task-" + model.getTask().getId();
	}

}
