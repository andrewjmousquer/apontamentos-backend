package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.TaskUserModel;
import com.portal.model.TaskUserTimeModel;

public class TaskUserTimeMapper implements ResultSetExtractor<List<TaskUserTimeModel>> {

	@Override
	public List<TaskUserTimeModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<TaskUserTimeModel> list = new LinkedList<TaskUserTimeModel>();

		if (rs != null) {
			while (rs.next()) {
				TaskUserTimeModel model = new TaskUserTimeModel();
				model.setId(rs.getInt("tut_id"));
				model.setDateStart(rs.getDate("date_start"));
				model.setDateFinish(rs.getDate("date_finish"));
				model.setTaskUser(new TaskUserModel(rs.getInt("tsu_id")));

				list.add(model);
			}
		}

		return list;
	}
}
