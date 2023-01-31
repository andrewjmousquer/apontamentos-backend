package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.portal.dto.AppointmentDTO;

public class AppointmentMapper implements ResultSetExtractor<List<AppointmentDTO>> {

	@Override
	public List<AppointmentDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {

		List<AppointmentDTO> list = new LinkedList<AppointmentDTO>();

		if (rs != null) {
			while (rs.next()) {
				AppointmentDTO dto = new AppointmentDTO();

				dto.setDateStart(rs.getTimestamp("date_start"));
				dto.setDateFinish(rs.getTimestamp("date_finish"));
				dto.setTotalTime(rs.getString("registred_time"));
				dto.setBrand(rs.getString("brand"));
				dto.setModel(rs.getString("model"));
				dto.setUserName(rs.getString("user_name"));
				dto.setNumberOS(rs.getString("number_os"));
				dto.setIdRegisterTime(rs.getInt("tut_id"));
				dto.setStageName(rs.getString("stg_name"));
				dto.setValue(rs.getBigDecimal("stg_value"));
				dto.setPaymentByTeam(rs.getBoolean("stg_payment_by_team"));
				dto.setValueRecived(rs.getBigDecimal("stg_value_recived"));
				

				list.add(dto);
			}
		}

		return list;
	}
}
