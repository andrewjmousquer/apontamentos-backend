package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ChecklistAnswerPhotoModel;

public class ChecklistAnswerPhotoMapper implements ResultSetExtractor<List<ChecklistAnswerPhotoModel>> {

	@Override
	public List<ChecklistAnswerPhotoModel> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<ChecklistAnswerPhotoModel> list = new LinkedList<ChecklistAnswerPhotoModel>();

		if (rs != null) {
			while (rs.next()) {

				ChecklistAnswerPhotoModel model = new ChecklistAnswerPhotoModel();

				model.setId(rs.getInt("qap_id"));
				model.setFileName(rs.getString("file_name"));
				model.setCreationDate(rs.getDate("creation_date"));

				list.add(model);
			}
		}

		return list;
	}
}
