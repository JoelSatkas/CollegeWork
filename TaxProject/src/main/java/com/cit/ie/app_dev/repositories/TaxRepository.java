package com.cit.ie.app_dev.repositories;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cit.ie.app_dev.models.Tax;
import com.cit.ie.app_dev.row_mappers.CitizenRowMapper;
import com.cit.ie.app_dev.row_mappers.TaxRowMapper;

@Repository
public class TaxRepository implements ITaxRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Tax getTax() {
		return jdbcTemplate.queryForObject("SELECT * FROM Taxation where ID = ?", new TaxRowMapper(), 1);
	}

	@Override
	public boolean updateTax(Tax tax) {
		try
		{
			jdbcTemplate.update("UPDATE Taxation set ExemptBand = ?, AfterBand = ?, AfterBandPer = ?, RemainderPer = ?, TaxCharge = ? where ID = ?", tax.getExemptBase(), tax.getAfterBase(), tax.getAfterBasePer(), tax.getRemainderPer(), tax.getTaxCharge(), 1);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
