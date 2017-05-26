package com.cit.ie.app_dev.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cit.ie.app_dev.models.Tax;
import com.cit.ie.app_dev.repositories.ITaxRepository;

@Service
public class TaxService implements ITaxService {
	
	@Autowired
	private ITaxRepository taxRepo;

	@Override
	public Tax getTaxModel() {
		return taxRepo.getTax();
	}

	@Override
	public boolean updateTaxModel(Tax tax) {
		Tax oldTax = this.getTaxModel();
		
		if(tax.getAfterBase() != 0)
		{
			oldTax.setAfterBase(tax.getAfterBase());
		}
		else if(tax.getAfterBasePer() != 0)
		{
			oldTax.setAfterBasePer(tax.getAfterBase());
		}
		else if(tax.getExemptBase() != 0)
		{
			oldTax.setExemptBase(tax.getExemptBase());
		}
		else if(tax.getRemainderPer() != 0)
		{
			oldTax.setRemainderPer(tax.getRemainderPer());
		}
		else if(tax.getTaxCharge() != 0)
		{
			oldTax.setTaxCharge(tax.getTaxCharge());
		}
		
		if(taxRepo.updateTax(oldTax))
			return true;
		return false;
	}

}
