package com.cit.ie.app_dev.row_mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cit.ie.app_dev.models.Citizen;

public class CitizenRowMapper implements RowMapper<Citizen> {

	@Override
	public Citizen mapRow(ResultSet rs, int rowNum) throws SQLException {
		Citizen cit = new Citizen();
		cit.setName(rs.getString("FirstName"));
		cit.setSurName(rs.getString("LastName"));
		cit.setSalary(rs.getDouble("Salary"));
		return cit;
	}

}
