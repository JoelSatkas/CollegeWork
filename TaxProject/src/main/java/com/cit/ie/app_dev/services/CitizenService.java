package com.cit.ie.app_dev.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cit.ie.app_dev.models.Citizen;
import com.cit.ie.app_dev.models.Tax;
import com.cit.ie.app_dev.repositories.ICitizensRepository;

@Service	
public class CitizenService implements ICitizenService  {
	
	@Autowired
	private ICitizensRepository citRepo;

	@Override
	public List<Citizen> getAllCitizens() {
		return citRepo.getAll();
	}

	@Override
	public boolean addCitizen(Citizen citizen) {
		if(citRepo.addCitizen(citizen))
			return true;
		return false;
	}

	/***
	 * Takes in the tax model and return all citizens with their tax calculated.
	 */
	@Override
	public List<Citizen> CalcCitizensTax(Tax tax) {
		List<Citizen> citizens = this.getAllCitizens();
		double base;
		double taxPayed;
		double takeHome;
		
		for(int x = 0 ; x < citizens.size(); x++)
		{
			base = citizens.get(x).getSalary();
			taxPayed = base * (tax.getTaxCharge()/100);
			takeHome = base;
			
			base -= tax.getExemptBase();
			if(!(base <= 0))
			{
				base -= tax.getAfterBase();
				if(!(base <= 0))
				{
					taxPayed += tax.getAfterBase() * (tax.getAfterBasePer()/100);
					taxPayed += base * (tax.getRemainderPer()/100);
				}
				else
				{
					taxPayed += (base+tax.getAfterBase()) * (tax.getAfterBasePer()/100);					
				}
				
			}

			citizens.get(x).setTaxPayed(taxPayed);
			citizens.get(x).setTakeHome(takeHome - taxPayed);
			
		}
		
		return citizens;
	}

}
