package com.portal.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.portal.model.PersonModel;

public class PersonMapper implements RowMapper<PersonModel> {

	@Override
	public PersonModel mapRow(ResultSet rs, int rowNum) throws SQLException {

		return PersonModel.builder().id(rs.getInt("per_id")).name(rs.getString("name"))
				.jobTitle(rs.getString("job_title")).build();
	}
}
