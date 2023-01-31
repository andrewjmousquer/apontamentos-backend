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
import com.portal.model.PersonModel;
import com.portal.model.ServiceOrderModel;
import com.portal.model.StageModel;
import com.portal.model.TaskModel;
import com.portal.model.TaskUserModel;
import com.portal.model.UserModel;
import com.portal.utils.PortalNumberUtils;

public class TaskUserMapper implements ResultSetExtractor<List<TaskUserModel>> {

	@Override
	public List<TaskUserModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<TaskUserModel> list = new LinkedList<TaskUserModel>();

		if (rs != null) {
			while (rs.next()) {
				TaskUserModel model = new TaskUserModel();
				model.setId(rs.getInt("tsu_id"));
				model.setDateStart(rs.getTimestamp("date_start"));
				model.setDateFinish(rs.getTimestamp("date_finish"));
				model.setStageValue(rs.getBigDecimal("stage_value"));
				model.setRecivedValue(rs.getBigDecimal("recived_value"));

				ClassifierModel status = new ClassifierModel();
				status.setId(rs.getInt("cla_id"));
				status.setValue(rs.getString("cla_value"));
				status.setType(rs.getString("cla_type"));
				status.setLabel(rs.getString("cla_label"));
				model.setStatus(status);

				if (rs.getInt("tsk_tsk_id") > 0) {

					TaskModel task = new TaskModel();

					task.setId(rs.getInt("tsk_tsk_id"));
					task.setNumberJira(rs.getString("tsk_number_jira"));
					task.setDateStart(rs.getDate("tsk_date_start"));
					task.setDateFinish(rs.getDate("tsk_date_finish"));
					task.setName(rs.getString("tsk_name"));
					task.setPlace(rs.getString("tsk_special_service_place"));

					ClassifierModel tskStatus = new ClassifierModel();
					tskStatus.setId(rs.getInt("tsk_cla_id"));
					tskStatus.setValue(rs.getString("tsk_cla_value"));
					tskStatus.setType(rs.getString("tsk_cla_type"));
					tskStatus.setLabel(rs.getString("tsk_cla_label"));
					task.setStatus(tskStatus);

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

						task.setServiceOrder(serviceOrder);
					}

					if (rs.getInt("stg_stg_id") > 0) {
						StageModel stage = new StageModel();

						stage.setId(rs.getInt("stg_stg_id"));

						stage.setName(rs.getString("stg_name"));

						stage.setTask(PortalNumberUtils.intToBoolean(rs.getInt("stg_task")));
						stage.setSpecial(PortalNumberUtils.intToBoolean(rs.getInt("stg_special")));

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

						task.setStage(stage);
					}

					model.setTask(task);
				}

				UserModel user = new UserModel(rs.getInt("usr_id"));
				user.setUsername(rs.getString("username"));

				PersonModel person = new PersonModel();
				person.setName(rs.getString("name"));
				person.setId(rs.getInt("per_id"));
				person.setJobTitle(rs.getString("per_job_title"));
				user.setPerson(person);

				model.setUser(user);

				list.add(model);
			}
		}

		return list;
	}
}
