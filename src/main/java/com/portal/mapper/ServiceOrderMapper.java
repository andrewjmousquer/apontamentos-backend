package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.model.ClassifierModel;
import com.portal.model.ServiceOrderModel;

public class ServiceOrderMapper implements ResultSetExtractor<List<ServiceOrderModel>> {

	public List<ServiceOrderModel> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<ServiceOrderModel> list = new LinkedList<ServiceOrderModel>();

		if (rs != null) {
			while (rs.next()) {

				ServiceOrderModel model = new ServiceOrderModel();

				model.setId(rs.getInt("svo_id"));
				model.setNumberJira(rs.getString("number_jira"));
				model.setDateStart(rs.getDate("date_start"));
				model.setDateFinish(rs.getDate("date_finish"));
				model.setBrand(rs.getString("brand"));
				model.setModel(rs.getString("model"));
				model.setPlate(rs.getString("plate"));
				model.setChassi(rs.getString("chassi"));
				model.setNumber(rs.getString("number"));

				ClassifierModel type = new ClassifierModel();
				type.setId(rs.getInt("cla_id"));
				type.setValue(rs.getString("cla_value"));
				type.setType(rs.getString("cla_type"));
				type.setLabel(rs.getString("cla_label"));
				model.setStatusOs(type);

				list.add(model);
			}
		}

		return list;
	}
}
