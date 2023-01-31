package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ChecklistGroupModel;

public class ChecklistGroupMapper implements ResultSetExtractor<List<ChecklistGroupModel>> {

	@Override
	public List<ChecklistGroupModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<ChecklistGroupModel> list = new LinkedList<ChecklistGroupModel>();

		if (rs != null) {
			while (rs.next()) {

				ChecklistGroupModel model = new ChecklistGroupModel();
				model.setCkgId(rs.getInt("ckg_id"));
				model.setName(rs.getString("name"));

				list.add(model);
			}
		}

		return list;
	}

}
