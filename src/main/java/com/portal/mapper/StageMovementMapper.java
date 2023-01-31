package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ClassifierModel;
import com.portal.model.StageMovementModel;

public class StageMovementMapper implements ResultSetExtractor<List<StageMovementModel>> {

	public List<StageMovementModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<StageMovementModel> models = new LinkedList<StageMovementModel>();

		if (rs != null) {
			while (rs.next()) {

				StageMovementModel model = new StageMovementModel();
				model.setId(rs.getInt("sps_id"));
				model.setJiraID(rs.getInt("jira_id"));

				ClassifierModel type = new ClassifierModel();
				type.setId(rs.getInt("cla_id"));
				type.setValue(rs.getString("cla_value"));
				type.setType(rs.getString("cla_type"));
				model.setType(type);

				model.setName(rs.getString("name"));
				model.setIcon(rs.getString("icon"));

				models.add(model);
			}
		}

		return models;
	}
}
