
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

// Author:(JOEL_SATKAUSKAS)

public class LotteryProject 
{
	static final double SEED = 5000; // the amount of money given to the draw at the start
	static final int ORIGINAL_JACKPOT = 500; // the amount to be won weekly
	static final int MINIMUM_NUMBER = 1; // the minimum number that is allowed to be selected
	static final int MAXIMUM_NUMBER = 28; // the maximum number that is allowed to be selected
	static final int ADDED = 150; // the amount added to the jack-pot in the next draw if there are no winners
	static final double TICKET = 1.5; // the price of the ticket
	static final int NUMBER_OF_EMPLOYEES = 3; // the amount of employees
	static final int NUMBER_OF_CUSTOMERS = 10000; // estimation of the maximum number of customers playing at the same time
	static final int NUMBER_OF_HISTORY_FILES = 10000; // the amount of history file that can be stored
	
	/* NOTE: the difference between the minimum and maximum numbers(range) must always be 4 or greater because if it is less then 4 the validation will always come up 
	 * as invalid and trying to get a quick-pick will put the program into an infinite loop. */
	
	/* The File customer_Position.txt should be set to 1 at the end of every draw. */
	
	/* NOTE: if the programs source has to change destination, its new destination will have to be written into runDraw and readHistory  */
	
	static Scanner keyboard = new Scanner(System.in);
	static Random generator = new Random();
	
	public static void main(String[] args) // the main method that will login and then go to menu
	{
		String employeeNames[] = new String[NUMBER_OF_EMPLOYEES];
		String employeePassWords[] = new String[NUMBER_OF_EMPLOYEES];
		int employeePosition = 0;
		
		/* Employees names and passwords can either be stored on the program or be read in from a file. Here I choose to read them in from a file. */
		/* NOTE( If the files contain more information then there is allocated space in the arrays, the arrays will not take in any more information then there are employees listed as Final.  */
		/*       If the file contains less information then the array is allocated too, the array will be partially filled )   */
		
		File employee_Names = new File("employee_Names.txt");
		File employee_PassWords = new File("employee_PassWords.txt");
		
		try 
		{
			Scanner inputFile1 = new Scanner(employee_Names);
			Scanner inputFile2 = new Scanner(employee_PassWords);
			
			for (int x = 0; x < NUMBER_OF_EMPLOYEES; x++)
			{
				if (inputFile1.hasNext())
				{
					employeeNames[x] = inputFile1.next();				
				}
			}
				
			for (int x = 0; x < NUMBER_OF_EMPLOYEES; x++)
			{
				if (inputFile2.hasNext())
				{
					employeePassWords[x] = inputFile2.nextLine();
				}
			}
			
			inputFile1.close();
			inputFile2.close();
		} 
		catch (FileNotFoundException e) 
		{
			print("Exception caught. File not found");
			e.printStackTrace();
			System.exit(1);
		}
		
		/* here the user will attempt to login,the names and passwords are given, the users position in the arrays is expected. The user will not enter unless the username and passwords are correct  */
		employeePosition = login(employeeNames, employeePassWords);
		menu(employeePosition);
	}

	public static int login(String[] employeeNames, String[] employeePassWords) // user will login, their position will be recorded
	{
		int position = 0;
		String userName = "";
		String password = "";
		boolean valid = false;
		
		while (valid == false)
		{
			boolean validUserName = false;
			boolean validPassword = false;
			
			while (validUserName == false)
			{
				print("Please enter username: ");
				userName = keyboard.nextLine();
				
				for (int x = 0; x < NUMBER_OF_EMPLOYEES; x++)
				{
					if(employeeNames[x].equals(userName))
					{
						validUserName = true;
						position = x;
					}
				}
				
				if (validUserName == false)
				{
					print("That is an invalid username.");
				}
			}
			
			while (validPassword == false)
			{
				print("Please enter your password "+userName+" : ");
				password = keyboard.nextLine();
				
				for (int x = 0; x < NUMBER_OF_EMPLOYEES; x++)
				{
					if(employeePassWords[x].equals(password))
					{
						validPassword = true;
					}
				}
				
				if (validPassword == false)
				{
					print("Invalid password");
				}
			}
			
			valid = true;
		}
			
		return position;
	}
	
	public static void menu(int employeePosition) 
	{
		int option = 0;
		int customerPosition = 0;
		int amountOfDraws = 0;
		boolean loop = true;
		
		/* In this program I have chosen to save a ticket purchase right after it is sold instead of saving all of the purchases when the user exits the program,
		 * this way if the employee has sold 10 tickets and a power failure or an error comes up before the employee has time to exit, all the data will have been saved.
		 * therefore every time the main loop is run, it recreates the arrays and reloads the information into them. The LOOP boolean is always on and the exit option closes the program.  */
		
		String customerNamesArray[] = new String[NUMBER_OF_CUSTOMERS];
		String customerPhoneNuArray[] = new String[NUMBER_OF_CUSTOMERS];
		String numbersUsedArray[] = new String[NUMBER_OF_CUSTOMERS];
		
		while (loop == true)
		{
			try 
			{
				File customer_Names = new File("customer_Names.txt");
				File customer_PhoneNu = new File("customer_PhoneNu.txt");
				File numbers_Used = new File("numbers_Used.txt");
				File customer_Position = new File("customer_Position.txt");
				File amount_Of_Draws = new File ("amount_Of_Draws.txt");
				
				Scanner customerNamesFile = new Scanner(customer_Names);
				Scanner customerPhoneNuFile = new Scanner(customer_PhoneNu);
				Scanner numbersUsedFile = new Scanner(numbers_Used);
				Scanner customerPositionFile = new Scanner(customer_Position);
				Scanner amountOfDrawsFile = new Scanner(amount_Of_Draws);
				
				for (int x = 0; x < NUMBER_OF_CUSTOMERS; x++)
				{
					if (customerNamesFile.hasNext())
					{
						customerNamesArray[x] = customerNamesFile.next();
					}
					
					if (customerPhoneNuFile.hasNext() )
					{				
						customerPhoneNuArray[x] = customerPhoneNuFile.next();
					}
					
					if (numbersUsedFile.hasNext())
					{
						numbersUsedArray[x] = numbersUsedFile.next();
					}
				}
				
				if (customerPositionFile.hasNextInt())
				{
						customerPosition = customerPositionFile.nextInt();
				}
				
				if (amountOfDrawsFile.hasNext())
				{
					amountOfDraws = amountOfDrawsFile.nextInt();
				}
				
				customerNamesFile.close();
				customerPhoneNuFile.close();
				numbersUsedFile.close();
				customerPositionFile.close();
				amountOfDrawsFile.close();
			} 
			catch (FileNotFoundException e) 
			{
				print("Exception caught. Failed to access files");
				e.printStackTrace();
				System.exit(1);
			}
		
			print("Choose your option: ");
			print("1) Change Password");
			print("2) Sell Ticket");
			print("3) Sell Quick Pick");
			print("4) Run a Draw");
			print("5) History");
			print("6) exit");
			
			option = getInt();
			
			while(option < 1 || option > 6)
			{
				print("That is not an option");
				print("please choose an option that is available");	
				option = getInt();
			}
			
			switch (option) 
			{
			case 1 : changePassword(employeePosition);
			break;
			case 2 : sellTicket(customerNamesArray, customerPhoneNuArray, numbersUsedArray, customerPosition);
			break;
			case 3 : quickPick(customerNamesArray, customerPhoneNuArray, numbersUsedArray, customerPosition);
			break;
			case 4 : runDraw(customerNamesArray, customerPhoneNuArray, numbersUsedArray, customerPosition, amountOfDraws, employeePosition);
			break;
			case 5 : history(amountOfDraws);
			break;
			case 6 : System.exit(0);
			break;
			default : print("invalid option");
			break;
			}
		}
	}
	
	public static void changePassword(int employeePosition) // uses the employees position to change their password
	{
		String names[] = new String[NUMBER_OF_EMPLOYEES];
		String passwords[] = new String[NUMBER_OF_EMPLOYEES];
		String newPassword = " ";
		
		try 
		{
			File employee_Names = new File("employee_Names.txt");
			File employee_Passwords = new File("employee_PassWords.txt");
			
			Scanner inputFile1 = new Scanner(employee_Names);
			Scanner inputFile2 = new Scanner(employee_Passwords);
			
			while (inputFile1.hasNext())
			{
				for (int x = 0; x < NUMBER_OF_EMPLOYEES; x++)
				{
					names[x] = inputFile1.next();
					passwords[x] = inputFile2.nextLine();
				}
			}
				
			print("Please type in your new password ");	
			newPassword = keyboard.nextLine();
			
			passwords[employeePosition] = newPassword;
			
			PrintWriter output = new PrintWriter("employee_PassWords.txt");
			
			for (int x = 0; x < NUMBER_OF_EMPLOYEES; x++)
			{
				output.println(passwords[x]);
			}
			
			output.close();
			
			print("Password has been changed ");
			
			inputFile1.close();
			inputFile2.close();
		} 
		catch (FileNotFoundException e) 
		{
			print("Exception caught. Failed to change password.");
			e.printStackTrace();
		}
	}
	
	public static void sellTicket(String[] customerNamesArray, String[] customerPhoneNuArray, String[] numbersUsedArray, int customerPosition) // customer selects what numbers they want on their tickets
	{
		String firstNumber = "";
		String secondNumber = "";
		String thirdNumber = "";
		String fourthNumber = "";
		String ticket = "";
		boolean validTicket = false;
		
		customerDetails(customerNamesArray, customerPhoneNuArray, customerPosition);
		
		while (validTicket == false)
		{
			ticket = "";
			
			firstNumber = validateNumberRange("1st");
			secondNumber = validateNumberRange("2nd");
			thirdNumber = validateNumberRange("3rd");
			fourthNumber = validateNumberRange("4th");
			
			ticket += firstNumber+secondNumber+thirdNumber+fourthNumber;
			
			validTicket = validateSingleTicket(ticket);
			
			if (validTicket == false)
			{
				print("ticket is invalid");
			}
			else
			{
				print("thank you for participating \n\n");
			}	
		}
		
		numbersUsedArray[customerPosition] = ticket;
		
		try 
		{
			PrintWriter Numbers_Used = new PrintWriter("numbers_Used.txt");
			PrintWriter customer_Position = new PrintWriter("customer_Position.txt");
			
			for(int x = 0; x < numbersUsedArray.length; x++)
			{
				Numbers_Used.println(numbersUsedArray[x]);
			}
			
			customerPosition++;
			customer_Position.println(customerPosition);
			
			Numbers_Used.close();
			customer_Position.close();
		} 
		catch (FileNotFoundException e) 
		{
			print("Exception caught. File not found");
			e.printStackTrace();
		}
	}
	
	public static void quickPick(String[] customerNamesArray, String[] customerPhoneNuArray, String[] numbersUsedArray, int customerPosition) // random numbers are selected for the customers ticket
	{
		String ticket = "";
		
		customerDetails(customerNamesArray, customerPhoneNuArray, customerPosition);
		
		ticket = GenerateRandomValidNumber();
		
		print("Your number is "+ticket.charAt(0)+ticket.charAt(1)+"-"+ticket.charAt(2)+ticket.charAt(3)+"-"+ticket.charAt(4)+ticket.charAt(5)+"-"+ticket.charAt(6)+ticket.charAt(7));
		
		numbersUsedArray[customerPosition] = ticket;
		customerPosition++;
		
		try 
		{
			PrintWriter Numbers_Used = new PrintWriter("numbers_Used.txt");
			PrintWriter customer_Position = new PrintWriter("customer_Position.txt");
			
			for(int x = 0; x < numbersUsedArray.length; x++)
			{
				Numbers_Used.println(numbersUsedArray[x]);
			}
			
			customer_Position.println(customerPosition);
			
			Numbers_Used.close();
			customer_Position.close();
		} 
		catch (FileNotFoundException e) 
		{
			print("Exception caught. Failed to save number");
			e.printStackTrace();
		}
	}
	
	public static void runDraw(String[] customerNamesArray, String[] customerPhoneNuArray, String[] numbersUsedArray, int customerPosition, int amountOfDraws, int employeePosition) // draw is run, money is calculated and draw is recorded
	{
		double curentTotal = 0;
		double totalAddition = 0;
		int amountToBeWon = 0;
		int winners[] = new int[NUMBER_OF_CUSTOMERS]; 
		int amountOfWinners = 0;
		int DrawsWithNoWinners = 0;
		String nameOfFile = "";
		String history[] = new String[NUMBER_OF_HISTORY_FILES];
		int overRide = 0;
		String answer = "";
		char answer2 = 'x';
		String employeeNames[] = new String[NUMBER_OF_EMPLOYEES];
		
		try 
		{
			File employees = new File("employee_Names.txt");
			Scanner employee_Names = new Scanner(employees);
			
			for(int x = 0; x < NUMBER_OF_EMPLOYEES; x++)
			{
				if(employee_Names.hasNext())
				{
					employeeNames[x] = employee_Names.nextLine();
				}
			}
			
			employee_Names.close();
			
			File curent_Total = new File("current_Total.txt");
			Scanner curentTotalFile = new Scanner(curent_Total);
			
			File no_Wins_Last_Draw = new File("last_Draw.txt");
			Scanner noWinsLastDraw = new Scanner(no_Wins_Last_Draw);
			
			DrawsWithNoWinners = noWinsLastDraw.nextInt();
			
			noWinsLastDraw.close();
			
			if (amountOfDraws == 0)
			{
				curentTotal = SEED;
			}
			else
			{
				curentTotal = curentTotalFile.nextDouble();
			}
			
			curentTotalFile.close();
		} 
		catch (FileNotFoundException e) 
		{
			print("Exception caught. Failed to access files.");
			e.printStackTrace();
		}
		
		totalAddition = TICKET*(customerPosition - 1 );
		
		curentTotal += totalAddition;
		
		amountToBeWon = ORIGINAL_JACKPOT + (ADDED * DrawsWithNoWinners );
		
		print(curentTotal+"");
		print(amountToBeWon+"");
		print(amountOfDraws+"");
		print(DrawsWithNoWinners+"");
		
		if(curentTotal < amountToBeWon)
		{
			print("Not enough funds currently available to initiate a draw.  ");
			print("Curent funds available with the adition of cuomer tickets: "+ curentTotal);
			print("Funds needed to run a draw: "+amountToBeWon);
			print("need an extra "+ (amountToBeWon-curentTotal) );
			print("System will shutdown and be unable to run a draw untill the minimum funds are met");
			System.exit(0);
		}
		
		String winningTicket = GenerateRandomValidNumber();
		
		print(winningTicket);
		
		int y = 0;
		String winPosition1 = "" + winningTicket.charAt(0) + winningTicket.charAt(1);
		String winPosition2 = "" + winningTicket.charAt(2) + winningTicket.charAt(3);
		String winPosition3 = "" + winningTicket.charAt(4) + winningTicket.charAt(5);
		String winPosition4 = "" + winningTicket.charAt(6) + winningTicket.charAt(7);
		
		boolean validPosition1 = false;
		boolean validPosition2 = false;
		boolean validPosition3 = false;
		boolean validPosition4 = false;
		
		String tikPosition1 = "";
		String tikPosition2 = "";
		String tikPosition3 = "";
		String tikPosition4 = "";
		
		for (int x = 0; x < NUMBER_OF_CUSTOMERS; x++)
		{
			tikPosition1 = "" + numbersUsedArray[x].charAt(0) + numbersUsedArray[x].charAt(1);
			tikPosition2 = "" + numbersUsedArray[x].charAt(2) + numbersUsedArray[x].charAt(3);
			tikPosition3 = "" + numbersUsedArray[x].charAt(4) + numbersUsedArray[x].charAt(5);
			tikPosition4 = "" + numbersUsedArray[x].charAt(6) + numbersUsedArray[x].charAt(7);
			
			if(tikPosition1.equals(winPosition1) || tikPosition1.equals(winPosition2) || tikPosition1.equals(winPosition3) || tikPosition1.equals(winPosition4))
			{
				validPosition1 = true;
			}
			
			if(tikPosition2.equals(winPosition2) || tikPosition2.equals(winPosition2) || tikPosition2.equals(winPosition3) || tikPosition2.equals(winPosition4))
			{
				validPosition2 = true;
			}
			
			if(tikPosition3.equals(winPosition3) || tikPosition3.equals(winPosition2) || tikPosition3.equals(winPosition3) || tikPosition3.equals(winPosition4))
			{
				validPosition3 = true;
			}
			
			if(tikPosition4.equals(winPosition4) || tikPosition4.equals(winPosition2) || tikPosition4.equals(winPosition3) || tikPosition4.equals(winPosition4))
			{
				validPosition4 = true;
			}
			
			if(validPosition1 == true && validPosition2 == true && validPosition3 == true && validPosition4 == true )
			{
				winners[y] = x;
				y++;
			}
		}
		
		for (int x = 0; x < winners.length; x++ )
		{
			if(winners[x] != 0)
			{
				amountOfWinners++; 
			}
		}
		
		if (amountOfWinners == 0)
		{
			print("no winners, Next weeks draw will have an aditional 150 euro\n ");
			DrawsWithNoWinners++;
			try 
			{
				PrintWriter last_draw = new PrintWriter("last_Draw.txt");
				last_draw.println(DrawsWithNoWinners);
				last_draw.close();
				
				PrintWriter Current_Total = new PrintWriter("current_Total.txt");
				Current_Total.println(curentTotal);
				Current_Total.close();
			} 
			catch (FileNotFoundException e) 
			{
				print("Exception caught. File not found");
				e.printStackTrace();
			}
		}
		else
		{
			if(amountOfWinners == 1)
			{
				print("There is "+amountOfWinners+" winner\n");
			}
			else
			{
				print("There are "+amountOfWinners+" winners\n");
			}
			
			
			try 
			{
				PrintWriter last_draw = new PrintWriter("last_Draw.txt");
				last_draw.println(0);
				last_draw.close();
			} 
			catch (FileNotFoundException e) 
			{
				print("Exception caught. File not found");
				e.printStackTrace();
			}
			
			curentTotal -= amountToBeWon;
			
			amountToBeWon = amountToBeWon/amountOfWinners;
			
			for (int x = 0; x < amountOfWinners; x++)
			{
				print(customerNamesArray[winners[x]]+", Phone number: "+customerPhoneNuArray[winners[x]]+" has won "+amountToBeWon+" euro");
				print("\n\n");
			}
			
			try 
			{
				PrintWriter Current_Total = new PrintWriter("current_Total.txt");
				Current_Total.println(curentTotal);
				Current_Total.close();
			} 
			catch (FileNotFoundException e) 
			{
				print("Exception caught. File not found");
				e.printStackTrace();
			}
			
		}
		
		amountOfDraws++;
		
		try 
		{
			PrintWriter amount_Of_Draws = new PrintWriter("amount_Of_Draws.txt");
			amount_Of_Draws.println(amountOfDraws);
			amount_Of_Draws.close();
			
			File name_Of_History_Files = new File("name_Of_History_Files.txt");
			Scanner nameOfHistoryFiles = new Scanner(name_Of_History_Files);
			
			for (int x = 0; x < NUMBER_OF_HISTORY_FILES; x++)
			{
				
				if (nameOfHistoryFiles.hasNext())
				{
					history[x] = nameOfHistoryFiles.nextLine();
				}
			}
			
			nameOfHistoryFiles.close();
		} 
		catch (FileNotFoundException e) 
		{
			print("Exception caught. File not found");
			e.printStackTrace();
		}
		
		
		while(overRide == 0)
		{
			overRide = 2;
			
			print("What would you like to call the history file?");
			print("This is draw number "+amountOfDraws);
			
			nameOfFile = keyboard.nextLine();
			
			for (int x = 0; x < NUMBER_OF_HISTORY_FILES; x++ )
			{
				if(nameOfFile.equals(history[x]))
				{
					print("The file "+nameOfFile+" already exists. Saving this file will override the existing file. Override? Y/N ");
					answer = keyboard.nextLine().toLowerCase(); 
					answer2 = answer.charAt(0);
					
					if( answer2 == 'y' )
					{
						overRide = 1;						
					}
					else
					{
						overRide = 0;
					}
				}
			}
		}
		
		if(overRide != 1)
		{
			history[amountOfDraws] = nameOfFile;
		}
		
		try 
		{
			PrintWriter History_output = new PrintWriter("..\\History\\"+nameOfFile+".txt");
			
			if (amountOfWinners == 0)
			{
				History_output.println("Winning number: "+winningTicket);
				History_output.println("employee supervising draw: "+employeeNames[employeePosition]);
				History_output.println("no winner");
			}
			else
			{
				History_output.println("Winning number: "+winningTicket);
				History_output.println("employee supervising draw: "+employeeNames[employeePosition]+"\n");
				
				for (int x = 0; x < amountOfWinners; x++ )
				{
					History_output.println("Name: "+customerNamesArray[winners[x]]); 
					History_output.println("Phone number: "+customerPhoneNuArray[winners[x]]);
					History_output.println("Amount won: "+amountToBeWon);
					History_output.println("\n\n"); 
				}
			}
			
			History_output.close();
			
			PrintWriter history_files = new PrintWriter("name_Of_History_Files.txt");
			
			for(int x = 0; x < history.length; x++)
			{
				history_files.println(history[x]);
			}
			
			history_files.close();
			
			PrintWriter numbers_Used = new PrintWriter("numbers_Used.txt");
			numbers_Used.close();
			
			PrintWriter customer_Names = new PrintWriter("customer_Names.txt");
			customer_Names.close();
			
			PrintWriter customer_PhoneNu = new PrintWriter("customer_PhoneNu.txt");
			customer_PhoneNu.close();
			
			PrintWriter customer_Position = new PrintWriter("customer_Position.txt");
			customer_Position.print("1");
			customer_Position.close();
		} 
		catch (FileNotFoundException e) 
		{
			print("Exception caught. File not found");
			e.printStackTrace();
		}
	}
	
	public static void history(int amountOfDraws) // employees can access previous draws
	{
		String history[] = new String[NUMBER_OF_HISTORY_FILES];
		String fileName = "";
		boolean fileTrue = false;
		String repeat = "";
		char repeat2 = 'y';
		String information = "";
		
		try 
		{
			File history_Files_Names = new File("name_Of_History_Files.txt");
			Scanner historyFilesNames = new Scanner(history_Files_Names);
			
			for(int x = 0; x < NUMBER_OF_HISTORY_FILES; x++)
			{
				if(historyFilesNames.hasNext())
				{
					history[x] = historyFilesNames.nextLine();
				}
			}
			
			historyFilesNames.close();
		} 
		catch (FileNotFoundException e) 
		{
			print("Exception caught. File not found");
			e.printStackTrace();
		}
		
		while(fileTrue == false)
		{
			 print("What is the name of the file you want to open?");
			 fileName = keyboard.nextLine();
			 
			 for(int x = 0; x < NUMBER_OF_HISTORY_FILES; x++)
				{
					if(fileName.equals(history[x]))
					{
						fileTrue = true;
					}
				}
			 
			 if(fileTrue == false)
			 {
				 print("You have either misspelled the files name or the file does not exist");
				 print("Would you like to search again? Y/N ");
				 repeat = keyboard.nextLine().toLowerCase();
				 repeat2 = repeat.charAt(0);
				 
				 if(repeat2 != 'y')
				 {
					 fileTrue = true;
				 }
			 }
		}
		
		if(repeat2 == 'y')
		{
			try 
			{
				File historyFile = new File("H:\\Software\\eclipse\\Eclipse work space\\Project\\History\\"+fileName+".txt");
				Scanner printHistoryFile = new Scanner(historyFile);
				
				while(printHistoryFile.hasNext())
				{
					information += printHistoryFile.nextLine() +"\n";
				}
				
				print("\n"+fileName);
				print("-------------------");
				print(information);
				
				printHistoryFile.close();
			} 
			catch (FileNotFoundException e) 
			{
				print("Exception caught. File not found");
				e.printStackTrace();
			}
		}
	}
	
	public static void customerDetails(String[] customerNamesArray, String[] customerPhoneNuArray, int customerPosition) // customer details are recoded
	{
		String name = "";
		String surname = "";
		String phoneNumber = "";
		
		print("Please enter you name: ");
		name = keyboard.nextLine().toLowerCase();
		print("Please enter you surname: ");
		surname = keyboard.nextLine().toLowerCase();
		
		name += surname;
		
		print(name);
		customerNamesArray[customerPosition] = name;
		
		print("Please enter you phone number: ");
		phoneNumber = keyboard.nextLine();
		
		customerPhoneNuArray[customerPosition] = phoneNumber;
		
		try //Try to save the customers details
		{
			PrintWriter customer_Names = new PrintWriter("customer_Names.txt");
			PrintWriter customer_PhoneNu = new PrintWriter("customer_PhoneNu.txt");
			
			for(int x = 0; x < customerNamesArray.length; x++)
			{
				customer_Names.println(customerNamesArray[x]);
			}
			
			for(int x = 0; x < customerPhoneNuArray.length; x++)
			{
				customer_PhoneNu.println(customerPhoneNuArray[x]);
			}
			
			customer_Names.close();
			customer_PhoneNu.close();
		}
		catch (FileNotFoundException e) 
		{
			print("Exception caught. File not found");
			e.printStackTrace();
		}
	}
	
	public static boolean validateSingleTicket(String ticket) // ticket is validated to check if it does not contain multiple uses of a single number 
	{
		boolean validTicket = false;
		
		String position1 = "";
		String position2 = "";
		String position3 = "";
		String position4 = "";
		
		position1 = "" + ticket.charAt(0) + ticket.charAt(1);
		position2 = "" + ticket.charAt(2) + ticket.charAt(3);
		position3 = "" + ticket.charAt(4) + ticket.charAt(5);
		position4 = "" + ticket.charAt(6) + ticket.charAt(7);
		
		if (position4.equals(position3))
		{
			validTicket = false;
		}
		else if (position4.equals(position2))
		{
			validTicket = false;
		}
		else if (position4.equals(position1))
		{
			validTicket = false;
		}
		else if (position3.equals(position2))
		{
			validTicket = false;
		}
		else if (position3.equals(position1))
		{
			validTicket = false;
		}
		else if (position2.equals(position1))
		{
			validTicket = false;
		}
		else
		{
			validTicket = true;
		}
		
		return validTicket;
	}
	
	public static String validateNumberRange(String position) // tickets number is validated to be between the minimum and maximum number
	{
		int number = 0;
		String numberString = "";
		
		print("Please enter your " + position + " number("+MINIMUM_NUMBER+"-"+MAXIMUM_NUMBER+"): ");
		number = getInt();	
		
		while (number < MINIMUM_NUMBER || number > MAXIMUM_NUMBER)
		{
			print("Please choose a number between "+MINIMUM_NUMBER+" and "+MAXIMUM_NUMBER);
			number = getInt();
		}
		
		if (number < 10)
		{
			numberString = "0" + number;
		}
		else
		{
			numberString += number;
		}
	
		return numberString;
	}
	
	public static String GenerateRandomValidNumber() // a random ticket is generated that already is between the minimum and maximum numbers, it is then checked to see if it has multiple uses of a single number
	{
		int firstNumber = 0;
		int secondNumber = 0;
		int thirdNumber = 0;
		int fourthNumber = 0;
		String firstNumberString = "";
		String secondNumberString = "";
		String thirdNumberString = "";
		String fourthNumberString = "";
		String ticket = "";
		boolean validTicket = false;
		
		while (validTicket == false)
		{
			firstNumber = 0;
			secondNumber = 0;
			thirdNumber = 0;
			fourthNumber = 0;
			firstNumberString = "";
			secondNumberString = "";
			thirdNumberString = "";
			fourthNumberString = "";
			ticket = "";
			
			int randomIndex = generator.nextInt(( MAXIMUM_NUMBER + 1 ) - MINIMUM_NUMBER ) + MINIMUM_NUMBER;
			
			firstNumber = randomIndex;
			
			if (firstNumber < 10)
			{
				firstNumberString = "0" + firstNumber;
			}
			else
			{
				firstNumberString += firstNumber;
			}
			
			randomIndex = generator.nextInt(( MAXIMUM_NUMBER + 1 ) - MINIMUM_NUMBER ) + MINIMUM_NUMBER;
			
			secondNumber = randomIndex;
			
			if (secondNumber < 10)
			{
				secondNumberString = "0" + secondNumber;
			}
			else
			{
				secondNumberString += secondNumber;
			}
			
			randomIndex = generator.nextInt(( MAXIMUM_NUMBER + 1 ) - MINIMUM_NUMBER ) + MINIMUM_NUMBER;
			
			thirdNumber = randomIndex;
			
			if (thirdNumber < 10)
			{
				thirdNumberString = "0" + thirdNumber;
			}
			else
			{
				thirdNumberString += thirdNumber;
			}
			
			randomIndex = generator.nextInt(( MAXIMUM_NUMBER + 1 ) - MINIMUM_NUMBER ) + MINIMUM_NUMBER;
			
			fourthNumber = randomIndex;
			
			if (fourthNumber < 10)
			{
				fourthNumberString = "0" + fourthNumber;
			}
			else
			{
				fourthNumberString += fourthNumber;
			}
			
			ticket = firstNumberString+secondNumberString+thirdNumberString+fourthNumberString;
			
			validTicket = validateSingleTicket(ticket);
		}
		
		return ticket;
	}
	
	private static int getInt() //Gets an integer from the user.
	{
		int answer = 0;
		
		while (! keyboard.hasNextInt())
		{
			keyboard.nextLine();
			print("That is not a number!");
			print("Please choose a valid number");
		}
		answer = keyboard.nextInt();	
		keyboard.nextLine();
		
		return answer;
	} 
	
	private static void print(String message)
	{
		System.out.println(message);
	} 
}
