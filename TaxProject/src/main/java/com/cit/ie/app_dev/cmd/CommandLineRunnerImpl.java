package com.cit.ie.app_dev.cmd;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cit.ie.app_dev.controllers.AppController;
import com.cit.ie.app_dev.models.Citizen;
import com.cit.ie.app_dev.models.Tax;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

	private Scanner keyboard = new Scanner(System.in);
	@Autowired
	private AppController appController;
	
	
	/***
	 * Main menu method. Gets options and calls relevant method to handle the request.
	 */
	
	@Override
	public void run(String... arg0) throws Exception {
		// CMD line interface
		
		int input = 0;
		System.out.println("\nKegdonia's Tax System");
		
		while (input != 4)
		{	
			print("\n--------------------------");
			print("Please Select an Option");
			print("--------------------------");
			print("1) List all citizens with their salaries and take-home pay");
			print("2) Add a new citizen");
			print("3) Change the taxation bands and rates.");
			print("4) Exit\n");
			
			input = getInt();
			
			switch (input)
			{
				case 1:
					ListAllCitizens();
					break;
				case 2:
					addCitizen();
					break;
				case 3:
					ChangeTax();
					break;
				case 4:
					print("Shutting down...");
					System.exit(0);
			}
		}
	}
	
	/***
	 * The method to change the tax rate. Gets the rate to change before calling the controller to change it.
	 */
	private void ChangeTax()
	{
		int option;
		double rate;
		Tax tax = new Tax();
		print("----------------");
		print("Change Tax");
		print("----------------");
		
		print("Which one would you like to change?");
		print("1) First amount to exempt");
		print("2) Amount after exemption to tax");
		print("3) Percentage at which to tax");
		print("4) Percentage to tax of whats left");
		print("5) Solidary tax charge\n");
		
		option = this.getInt();
		while((option <= 0)||(option > 5))
		{
			print("Not an option, try again.");
			option = this.getInt();
		}
		
		print("What is the new value to use?");
		rate = this.getDouble();
		
		switch(option)
		{
			case 1:
				tax.setExemptBase(rate);
				break;
			case 2:
				tax.setAfterBase(rate);
				break;
			case 3:
				tax.setAfterBasePer(rate);
				break;
			case 4:
				tax.setRemainderPer(rate);
				break;
			case 5:
				tax.setTaxCharge(rate);
				break;
		}
		
		if(this.appController.ChangeRate(tax))
		{
			print("Tax has been changed");
		}
		else
		{
			print("Failed to change tax");
		}
	}
	
	/***
	 * The method to add a citizen. Gets the citizens details before sending it to the controller.
	 */
	private void addCitizen()
	{
		String name;
		String surname;
		double salary;
		
		print("----------------");
		print("Add New Citizen");
		print("----------------");
		print("Please enter the citizens first name");
		name = keyboard.nextLine();
		print("Please enter the citizens surname");
		surname = keyboard.nextLine();
		print("Please enter the citizens salary");
		salary = this.getDouble();
		
		Citizen cit = new Citizen();
		cit.setName(name);
		cit.setSurName(surname);
		cit.setSalary(salary);
		
		if(appController.NewCitizen(cit))
		{
			print("Added new Citizen");
		}
		else
		{
			print("Failed to add citizen");
		}
	}
	
	/***
	 * The method to list all citizens and their taxes. Calls the controller for a list to print out.
	 */
	private void ListAllCitizens()
	{
		print("----------------");
		print("All Citizens");
		print("----------------");
		List<String> citizens = appController.ListAllCitizens();
		for(int x = 0; x < citizens.size(); x++)
		{
			print(citizens.get(x));
		}
	}
	
	/***
	 * Private method that makes sure the user enters an int
	 * @return
	 * an int data type
	 */
	private int getInt()
	{
		int answer = 0 ;
		
		while (! keyboard.hasNextInt ())
		{
			keyboard.nextLine();
			System.out.println("That is not a number!");
			System.out.println("Please choose");
		}
		answer = keyboard.nextInt();	
		keyboard.nextLine();
		
		return answer ;
	}
	
	/***
	 * Private method to make sure the user enters a double
	 * @return
	 * a double data type
	 */
	private double getDouble()
	{
		double answer = 0 ;
		
		while (! keyboard.hasNextDouble())
		{
			keyboard.nextLine();
			System.out.println("That is not a number!");
			System.out.println("Please choose");
		}
		answer = keyboard.nextDouble();	
		keyboard.nextLine();
		
		return answer ;
	}
	
	/***
	 * Private method to print to screen. Faster calling this then typing out full statement.
	 * @param message
	 * The message to print
	 */
	private void print(String message)
	{
		System.out.println(message);
	}

}
