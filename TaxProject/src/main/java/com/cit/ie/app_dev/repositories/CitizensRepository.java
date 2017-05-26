package com.cit.ie.app_dev.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cit.ie.app_dev.models.Citizen;
import com.cit.ie.app_dev.row_mappers.CitizenRowMapper;

@Repository
public class CitizensRepository implements ICitizensRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Citizen> getAll() {
		return jdbcTemplate.query("SELECT * FROM citizens", new CitizenRowMapper());
	}

	@Override
	public boolean addCitizen(Citizen citizen) {
		try
		{
			jdbcTemplate.update("INSERT INTO Citizens (LastName,FirstName,Salary) VALUES ('"+ citizen.getSurName() +"', '" + citizen.getName() + "', " + citizen.getSalary() + " )");
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

}