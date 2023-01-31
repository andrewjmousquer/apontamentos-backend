package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ChecklistModel;
import com.portal.model.CheckpointModel;
import com.portal.model.StageModel;
import com.portal.utils.PortalNumberUtils;

public class StageMapper implements ResultSetExtractor<List<StageModel>> {

	public List<StageModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<StageModel> models = new LinkedList<StageModel>();

		if (rs != null) {
			while (rs.next()) {

				StageModel model = new StageModel();

				model.setId(rs.getInt("stg_id"));

				model.setName(rs.getString("stg_name"));

				model.setTask(PortalNumberUtils.intToBoolean(rs.getInt("task")));
				model.setSpecial(PortalNumberUtils.intToBoolean(rs.getInt("special")));
				model.setPaymentByTeam(PortalNumberUtils.intToBoolean(rs.getInt("payment_by_team")));        
 
				model.setStatusJiraID(rs.getInt("status_jira_id"));
				
				model.setValue(rs.getBigDecimal("value"));

				if (rs.getInt("ckp_id") > 0) {
					CheckpointModel checkpoint = new CheckpointModel();
					checkpoint.setId(rs.getInt("ckp_id"));
					checkpoint.setName(rs.getString("ckp_name"));
					checkpoint.setDescription(rs.getString("description"));
					model.setCheckpoint(checkpoint);
				}
				if (rs.getInt("ckl_id") > 0) {
					ChecklistModel checklist = new ChecklistModel();
					checklist.setId(rs.getInt("ckl_id"));
					checklist.setName(rs.getString("cw_name"));
					model.setChecklist(checklist);
				}

				models.add(model);
			}
		}

		return models;
	}
}
