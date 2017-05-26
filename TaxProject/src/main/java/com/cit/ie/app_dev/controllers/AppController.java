package com.cit.ie.app_dev.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.cit.ie.app_dev.models.Citizen;
import com.cit.ie.app_dev.models.Tax;
import com.cit.ie.app_dev.services.ICitizenService;
import com.cit.ie.app_dev.services.ITaxService;

@Controller
public class AppController {
	
	@Autowired
	private ICitizenService citService;
	@Autowired
	private ITaxService taxSerive;
	
	/***
	 * Controller method that return a list of string to the front end. 
	 * Gets all citizens, calculates their tax and builds the string to be printed before sending a string list back.
	 * @return
	 * A list of strings. Each string is a citizen and their taxes.
	 */
	public List<String> ListAllCitizens()
	{
		Tax tax = taxSerive.getTaxModel();
		List<String> result = new ArrayList<String>();
		List<Citizen> citizens =  citService.CalcCitizensTax(tax);
		
		for(int x = 0; x < citizens.size(); x++)
		{
			result.add("\n* " + citizens.get(x).getName() + " " + citizens.get(x).getSurName() + " :\nSalary: €" + citizens.get(x).getSalary() + " Tax Payed = " + citizens.get(x).getTaxPayed() + " Take home = " + citizens.get(x).getTakeHome());
		}
		
		return result;
	}
	
	/***
	 * Calls the service to add a new citizen to the repository.
	 * @param citizen
	 * The citizen object to add. I passed in a citizen object but in real life, it might be a json
	 * or a few parameters and the actual object would be made here before being passed into the service layer.
	 * @return
	 * returns a boolean to indicate if it successfully added the citizen or not.
	 */
	public boolean NewCitizen(Citizen citizen)
	{
		if(this.citService.addCitizen(citizen))
		{
			return true;
		}
		return false;
	}
	
	/***
	 * Calls the service to change the tax rate.
	 * @param tax
	 * The new tax model to update. Again, im passing in the object were as in real life it might be a json or a few parameters and
	 * the actual object would be created here before being passed to the service layer.
	 * @return
	 */
	public boolean ChangeRate(Tax tax)
	{
		if(this.taxSerive.updateTaxModel(tax))
		{
			return true;
		}
		return false;
	}
}
