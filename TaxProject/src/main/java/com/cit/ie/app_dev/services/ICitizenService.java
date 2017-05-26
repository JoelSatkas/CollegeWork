package com.cit.ie.app_dev.services;

import java.util.List;

import com.cit.ie.app_dev.models.Citizen;
import com.cit.ie.app_dev.models.Tax;

public interface ICitizenService {

	public List<Citizen> getAllCitizens();
	public boolean addCitizen(Citizen citizen);
	public List<Citizen> CalcCitizensTax(Tax tax);
}
