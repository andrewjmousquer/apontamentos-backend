package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.TeamModel;

public class TeamMapper implements ResultSetExtractor<List<TeamModel>> {

	public List<TeamModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<TeamModel> list = new LinkedList<TeamModel>();
		if (rs != null) {
			while (rs.next()) {
				TeamModel model = new TeamModel();
				model.setId(rs.getInt("tam_id"));
				model.setName(rs.getString("name"));
				model.setAbbreviation(rs.getString("abbreviation"));
				list.add(model);
			}
		}

		return list;
	}
}