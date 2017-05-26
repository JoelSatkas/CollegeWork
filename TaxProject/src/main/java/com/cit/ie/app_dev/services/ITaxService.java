package com.cit.ie.app_dev.services;

import com.cit.ie.app_dev.models.Tax;

public interface ITaxService {

	public Tax getTaxModel();
	public boolean updateTaxModel(Tax tax);
}
