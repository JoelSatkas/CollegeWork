package com.cit.ie.app_dev.repositories;

import com.cit.ie.app_dev.models.Tax;

public interface ITaxRepository {

	public Tax getTax();
	public boolean updateTax(Tax tax);
}
