package com.portal.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.portal.config.BaseDAO;
import com.portal.dao.IAppointmentDAO;
import com.portal.dto.AppointmentDTO;
import com.portal.dto.request.SearchAppointmentDTO;
import com.portal.exceptions.AppException;
import com.portal.mapper.AppointmentMapper;

@Repository
public class AppointmentDAO extends BaseDAO implements IAppointmentDAO {

	private static final Logger logger = LoggerFactory.getLogger(AppointmentDAO.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public List<AppointmentDTO> search(SearchAppointmentDTO taskUser) throws AppException {

		List<AppointmentDTO> listToReturn = new ArrayList<>();

		try {
			StringBuilder query = new StringBuilder();

			query.append("SELECT ");
			query.append("tut.tut_id ");
			query.append(",so.number AS number_os ");
			query.append(",per.name AS user_name ");
			query.append(",so.model ");
			query.append(",so.brand ");
			query.append(",stg.name AS stg_name ");
			query.append(",stg.payment_by_team AS stg_payment_by_team ");
			query.append(",tu.stage_value AS stg_value ");
			query.append(",(CASE WHEN (tu.recived_value IS NOT NULL) THEN tu.recived_value ");
			query.append(
					"WHEN (tu.recived_value IS NULL and tu.stage_value > 0 and stg.payment_by_team = 1) THEN fnGetStagePrice(tsk.tsk_id, tu.stage_value)");
			query.append(" ELSE tu.stage_value END) AS stg_value_recived");
			query.append(
					",SEC_TO_TIME(TIMESTAMPDIFF(SECOND, tut.date_start, IFNULL(tut.date_finish, NOW()))) AS registred_time ");
			query.append(",tut.date_start ");
			query.append(",tut.date_finish ");
			query.append("FROM " + schemaName + "task_user_time_register AS tut ");
			query.append("INNER JOIN " + schemaName + "task_user AS tu ON tu.tsu_id = tut.tsu_id ");
			query.append("RIGHT JOIN " + schemaName + "user AS us ON us.usr_id = tu.usr_id ");
			query.append("RIGHT JOIN " + schemaName + "person AS per ON per.per_id = us.per_id ");
			query.append("RIGHT JOIN " + schemaName + "task AS tsk ON tu.tsk_id = tsk.tsk_id ");
			query.append("RIGHT JOIN " + schemaName + "stage AS stg ON stg.stg_id = tsk.stg_id ");
			query.append("RIGHT JOIN " + schemaName + "service_order AS so ON so.svo_id = tsk.svo_id ");
			query.append("WHERE tut.tut_id > 0 ");

			MapSqlParameterSource params = new MapSqlParameterSource();
			if (taskUser != null) {
				if (StringUtils.isNotBlank(taskUser.getBrand())) {
					query.append(" AND so.brand LIKE :brand ");
					params.addValue("brand", mapLike(taskUser.getBrand()));
				}

				if (StringUtils.isNotBlank(taskUser.getModel())) {
					query.append(" AND so.model LIKE :model ");
					params.addValue("model", mapLike(taskUser.getModel()));
				}

				if (StringUtils.isNotBlank(taskUser.getName())) {
					query.append(" AND per.name LIKE :name ");
					params.addValue("name", mapLike(taskUser.getName()));
				}

				if (StringUtils.isNotBlank(taskUser.getNumberOS())) {
					query.append(" AND so.number LIKE :numberOS ");
					params.addValue("numberOS", mapLike(taskUser.getNumberOS()));
				}

				if (taskUser.getDateStart() != null) {
					query.append(" AND tut.date_start = :dateStart ");
					params.addValue("dateStart", taskUser.getDateStart());
				}
				if (taskUser.getDateFinish() != null) {
					query.append(" AND tut.date_finish = :dateFinish ");
					params.addValue("dateFinish", taskUser.getDateFinish());
				}
				if (taskUser.getStage() != null && taskUser.getStage() > 0) {
					query.append(" AND stg.stg_id = :stageID ");
					params.addValue("stageID", taskUser.getStage());
				}
			}

			query.append("GROUP BY tut.tut_id");

			List<AppointmentDTO> appointments = this.getJdbcTemplatePortal().query(query.toString(), params,
					new AppointmentMapper());

			if (!CollectionUtils.isEmpty(appointments)) {
				listToReturn = appointments;
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new AppException(
					this.messageSource.getMessage("error.generic", null, LocaleContextHolder.getLocale()));
		}

		return listToReturn;
	}

}
