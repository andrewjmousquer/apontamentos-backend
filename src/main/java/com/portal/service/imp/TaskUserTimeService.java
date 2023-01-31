package com.portal.service.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.portal.dao.IAppointmentDAO;
import com.portal.dao.ITaskUserTimeDAO;
import com.portal.dto.AppointmentDTO;
import com.portal.dto.TaskUserTimeDTO;
import com.portal.dto.UserProfileDTO;
import com.portal.dto.request.InsertOrEditTaskUserTimeDTO;
import com.portal.dto.request.SearchAppointmentDTO;
import com.portal.enums.AuditOperationType;
import com.portal.exceptions.AppException;
import com.portal.exceptions.BusException;
import com.portal.factory.TaskUserTimeFactory;
import com.portal.model.TaskUserTimeModel;
import com.portal.service.IAuditService;
import com.portal.service.ITaskUserTimeService;
import com.portal.utils.PortalNumberUtils;
import com.portal.utils.PortalStaticVariables;
import com.portal.utils.PortalTimeUtils;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TaskUserTimeService implements ITaskUserTimeService {

	@Autowired
	private ITaskUserTimeDAO dao;

	@Autowired
	private TaskUserTimeFactory factory;

	@Autowired
	private IAppointmentDAO appointmentDAO;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IAuditService auditService;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public Optional<TaskUserTimeDTO> saveOrUpdate(InsertOrEditTaskUserTimeDTO model, UserProfileDTO userProfile)
			throws AppException, BusException {

		if (model != null && (model.getId() == null || model.getId().equals(0))) {
			return this.saveNewTime(model, userProfile);
		} else {
			return this.update(model, userProfile);
		}

	}

	@Override
	public void finishByTaskUser(Integer taskUser) throws AppException {

		Optional<TaskUserTimeModel> taskUnfinish = this.dao.getUnfinishTimeByTask(taskUser);

		if (taskUnfinish.isPresent()) {

			taskUnfinish.get().setDateFinish(new Date());

			this.dao.update(taskUnfinish.get());
		}

	}

	@Override
	public Optional<TaskUserTimeDTO> saveNewTime(InsertOrEditTaskUserTimeDTO insertDTO, UserProfileDTO userProfile)
			throws AppException, BusException {

		this.validateUnfinishTime(insertDTO.getTaskUser());

		TaskUserTimeModel modelToSave = factory.convertFromInsertDto(insertDTO);

		Optional<TaskUserTimeModel> saved = this.dao.save(modelToSave);

		this.audit(saved.get(), AuditOperationType.TASK_USER_TIME_INSERTED, userProfile);

		return Optional.of(factory.convertFromModel(saved.get()));
	}

	@Override
	public Optional<TaskUserTimeDTO> update(InsertOrEditTaskUserTimeDTO insertDTO, UserProfileDTO userProfile)
			throws AppException, BusException {

		if (insertDTO.getDateFinish() == null)
			throw new BusException(
					this.messageSource.getMessage("error.time.register.finish", null, LocaleContextHolder.getLocale()));

		TaskUserTimeModel modelToSave = factory.convertFromInsertDto(insertDTO);

		Optional<TaskUserTimeModel> saved = this.dao.update(modelToSave);

		this.audit(saved.get(), AuditOperationType.TASK_USER_TIME_UPDATED, userProfile);

		return Optional.of(factory.convertFromModel(saved.get()));
	}

	private void audit(TaskUserTimeModel model, AuditOperationType operationType, UserProfileDTO userProfile)
			throws AppException, BusException {

		String details = String.format("id:%s;dateStart:%s;dateFinish:%s;taskUser:%s", model.getId(),
				model.getDateStart(), model.getDateFinish(),
				model.getTaskUser() != null ? model.getTaskUser().getId() : 0);
		this.auditService.save(details, operationType, userProfile);

	}

	private void validateUnfinishTime(Integer taskUserID) throws AppException, BusException {

		Optional<TaskUserTimeModel> taskUnfinish = this.dao.getUnfinishTimeByTask(taskUserID);

		if (taskUnfinish.isPresent()) {

			throw new BusException(this.messageSource.getMessage("error.time.register.unfinish", null,
					LocaleContextHolder.getLocale()));
		}

	}

	@Override
	public List<AppointmentDTO> searchAllRegistredTime(SearchAppointmentDTO searchDTO) throws AppException {

		return this.appointmentDAO.search(searchDTO);

	}

	@Override
	public byte[] exportAllRegistredTime(SearchAppointmentDTO searchDTO) throws AppException {
		final List<AppointmentDTO> lista = searchAllRegistredTime(searchDTO);

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				XSSFSheet sheet = workbook.createSheet("Apontamentos");
				writeHeaderLine(workbook, sheet);
				writeDataLine(workbook, sheet, lista);

				for (int i = 0; i < PortalStaticVariables.XLS_COLLUMNS.length; i++) {
					sheet.autoSizeColumn(i);
				}

				workbook.write(out);
				return out.toByteArray();
			}
		} catch (Exception e) {
			throw new AppException(e.getMessage(), e);
		}
	}

	private void writeHeaderLine(XSSFWorkbook workbook, XSSFSheet sheet) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);

		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < PortalStaticVariables.XLS_COLLUMNS.length; i++) {
			createCell(row, i, PortalStaticVariables.XLS_COLLUMNS[i], cellStyle);
		}
	}

	private void writeDataLine(XSSFWorkbook workbook, XSSFSheet sheet, List<AppointmentDTO> lista) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);

		int rowIndex = 1;
		for (AppointmentDTO app : lista) {
			XSSFRow row = sheet.createRow(rowIndex++);

			int columnIndex = 0;
			createCell(row, columnIndex++, app.getUserName(), cellStyle);
			createCell(row, columnIndex++, app.getNumberOS(), cellStyle);
			createCell(row, columnIndex++, app.getBrand(), cellStyle);
			createCell(row, columnIndex++, app.getModel(), cellStyle);
			createCell(row, columnIndex++, app.getStageName(), cellStyle);
			createCell(row, columnIndex++, PortalTimeUtils.dateFormat(app.getDateStart(), "dd/MM/yyyy"), cellStyle);
			createCell(row, columnIndex++, PortalTimeUtils.dateFormat(app.getDateStart(), "HH:mm:ss"), cellStyle);
			createCell(row, columnIndex++, PortalTimeUtils.dateFormat(app.getDateFinish(), "dd/MM/yyyy"), cellStyle);
			createCell(row, columnIndex++, PortalTimeUtils.dateFormat(app.getDateFinish(), "HH:mm:ss"), cellStyle);
			createCell(row, columnIndex++, app.getTotalTime(), cellStyle);
			createCell(row, columnIndex++,
					PortalNumberUtils.formatBigDecimal(app.getValue() == null ? BigDecimal.ZERO : app.getValue()),
					cellStyle);
			createCell(row, columnIndex++, PortalNumberUtils.formatBigDecimal(
					app.getValueRecived() == null ? BigDecimal.ZERO : app.getValueRecived()), cellStyle);
			createCell(row, columnIndex++, app.getPaymentByTeam() ? "Sim" : "Não", cellStyle);
		}
	}

	private void createCell(XSSFRow row, int i, Object value, XSSFCellStyle style) {
		XSSFCell cell = row.createCell(i);
		cell.setCellStyle(style);

		if (Objects.nonNull(value)) {
			if (value instanceof BigDecimal) {
				cell.setCellValue("R$ " + ((BigDecimal) value).toString());
			} else {
				cell.setCellValue(value.toString());
			}
		}
	}

}
