package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ChecklistQuestionModel;
import com.portal.utils.PortalNumberUtils;

public class ChecklistQuestionMapper implements ResultSetExtractor<List<ChecklistQuestionModel>> {

	@Override
	public List<ChecklistQuestionModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<ChecklistQuestionModel> list = new LinkedList<ChecklistQuestionModel>();

		if (rs != null) {
			while (rs.next()) {
				ChecklistQuestionModel model = new ChecklistQuestionModel();
				model.setId(rs.getInt("qas_id"));
				model.setQuestion(rs.getString("question"));
				model.setCreationDate(rs.getDate("creation_date"));
				model.setActive(PortalNumberUtils.intToBoolean(rs.getInt("active")));

				list.add(model);
			}
		}

		return list;
	}

}
