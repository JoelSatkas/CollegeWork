package com.cit.ie.app_dev.repositories;

import java.util.List;

import com.cit.ie.app_dev.models.Citizen;

public interface ICitizensRepository {
	public List<Citizen> getAll();
	public boolean addCitizen(Citizen citizen);
}
