package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ChecklistModel;

public class ChecklistMapper implements ResultSetExtractor<List<ChecklistModel>> {

	@Override
	public List<ChecklistModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
		
		List<ChecklistModel> list = new LinkedList<ChecklistModel>();

		if (rs != null) {
			while (rs.next()) {
				ChecklistModel model = new ChecklistModel();
				model.setId(rs.getInt("ckl_id"));
				model.setName(rs.getString("name"));
				model.setDescrition(rs.getString("description"));
				model.setPriorityOrder(rs.getString("priority_order"));
				model.setTag(rs.getString("tag_id"));
				model.setNumberOfGroups(rs.getInt("numberOfGroups"));
				model.setNumberOfQuestions(rs.getInt("numberOfQuestions"));

				list.add(model);
			}
		}

		return list;
	}

}

