package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ChecklistModel;
import com.portal.model.CheckpointModel;
import com.portal.model.ClassifierModel;
import com.portal.model.ServiceOrderModel;
import com.portal.model.StageModel;
import com.portal.model.TaskModel;
import com.portal.utils.PortalNumberUtils;

public class TaskMapper implements ResultSetExtractor<List<TaskModel>> {

	@Override
	public List<TaskModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<TaskModel> list = new LinkedList<TaskModel>();

		if (rs != null) {
			while (rs.next()) {
				TaskModel model = new TaskModel();

				model.setId(rs.getInt("tsk_id"));
				model.setNumberJira(rs.getString("number_jira"));
				model.setDateStart(rs.getDate("date_start"));
				model.setDateFinish(rs.getDate("date_finish"));
				model.setName(rs.getString("name"));
				model.setPlace(rs.getString("special_service_place"));

				ClassifierModel status = new ClassifierModel();
				status.setId(rs.getInt("cla_id"));
				status.setValue(rs.getString("cla_value"));
				status.setType(rs.getString("cla_type"));
				status.setLabel(rs.getString("cla_label"));
				model.setStatus(status);

				if (rs.getInt("so_svo_id") > 0) {
					ServiceOrderModel serviceOrder = new ServiceOrderModel();

					serviceOrder.setId(rs.getInt("so_svo_id"));
					serviceOrder.setNumberJira(rs.getString("so_number_jira"));
					serviceOrder.setDateStart(rs.getDate("so_date_start"));
					serviceOrder.setDateFinish(rs.getDate("so_date_finish"));
					serviceOrder.setBrand(rs.getString("so_brand"));
					serviceOrder.setModel(rs.getString("so_model"));
					serviceOrder.setPlate(rs.getString("so_plate"));
					serviceOrder.setChassi(rs.getString("so_chassi"));
					serviceOrder.setNumber(rs.getString("so_number"));

					ClassifierModel type = new ClassifierModel();
					type.setId(rs.getInt("so_cla_id"));
					type.setValue(rs.getString("so_cla_value"));
					type.setType(rs.getString("so_cla_type"));
					type.setLabel(rs.getString("so_cla_label"));
					serviceOrder.setStatusOs(type);

					model.setServiceOrder(serviceOrder);
				}

				if (rs.getInt("stg_id") > 0) {
					StageModel stage = new StageModel();

					stage.setId(rs.getInt("stg_stg_id"));

					stage.setName(rs.getString("stg_name"));

					stage.setTask(PortalNumberUtils.intToBoolean(rs.getInt("stg_task")));
					stage.setSpecial(PortalNumberUtils.intToBoolean(rs.getInt("stg_special")));
					stage.setValue(rs.getBigDecimal("stg_value"));
					stage.setPaymentByTeam(rs.getBoolean("stg_payment_by_team"));

					stage.setStatusJiraID(rs.getInt("stg_status_jira_id"));

					if (rs.getInt("stg_ckp_id") > 0) {
						CheckpointModel checkpoint = new CheckpointModel();
						checkpoint.setId(rs.getInt("stg_ckp_id"));
						checkpoint.setName(rs.getString("stg_ckp_name"));
						checkpoint.setDescription(rs.getString("stg_ckp_description"));
						stage.setCheckpoint(checkpoint);
					}
					if (rs.getInt("stg_ckl_id") > 0) {
						ChecklistModel checklist = new ChecklistModel();
						checklist.setId(rs.getInt("stg_ckl_id"));
						checklist.setName(rs.getString("stg_cw_name"));
						stage.setChecklist(checklist);
					}

					model.setStage(stage);
				}

				list.add(model);
			}
		}

		return list;
	}
}
