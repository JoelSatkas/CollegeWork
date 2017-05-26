package com.cit.ie.app_dev.row_mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.cit.ie.app_dev.models.Tax;

public class TaxRowMapper implements RowMapper<Tax> {

	@Override
	public Tax mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tax tax = new Tax();
		tax.setExemptBase(rs.getDouble("ExemptBand"));
		tax.setAfterBase(rs.getDouble("AfterBand"));
		tax.setAfterBasePer(rs.getDouble("AfterBandPer"));
		tax.setRemainderPer(rs.getDouble("RemainderPer"));
		tax.setTaxCharge(rs.getDouble("TaxCharge"));
		return tax;
	}

}
