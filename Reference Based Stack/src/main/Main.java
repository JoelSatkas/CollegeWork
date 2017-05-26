package main;

import java.util.Scanner;

import List.List;

public class Main 
{
	static Scanner keyboard = new Scanner(System.in);
	static Stack theStack = new Stack() ;
	
	
	public static void main(String[] args) 
	{
		
		String ans ;
		char currentPeak = ' ' ;
		
		
		while(true)
		{
			System.out.println("\n\nEnter a string") ;
			System.out.println("\nif a value never gets into the stack then is will say that it is balenced") ;
			System.out.println("eg random charecters that do not contain (,),{,},[,]") ;
			
			ans = getString() ;
			
			for(int x = 0 ; x < (ans.length()) ; x++)
			{
				if((ans.charAt(x) == '(') || (ans.charAt(x) == '[') || (ans.charAt(x) == '{') )
				{
					theStack.push(ans.charAt(x)) ;
					System.out.println("pushed") ;
				}
				else if((ans.charAt(x) == ')') || (ans.charAt(x) == ']') || (ans.charAt(x) == '}') )
				{
					currentPeak = theStack.peek();
					
					if((currentPeak == '(') && (ans.charAt(x) == ')'))
					{
						theStack.pop();
						System.out.println("poped") ;
					}
					else if((currentPeak == '{') && (ans.charAt(x) == '}'))
					{
						theStack.pop();
						System.out.println("poped") ;
					}
					else if((currentPeak == '[') && (ans.charAt(x) == ']'))
					{
						theStack.pop();
						System.out.println("poped") ;
					}
					else
					{
						System.out.println("ERROR") ;
					}
				}
				else
				{
					System.out.println(ans.charAt(x)+" is not a parameter") ;
				}
				
				theStack.print();
				System.out.println(theStack.getCounter()) ;
			}
			
			if(theStack.getCounter() > 0)
			{
				System.out.println(ans+" is Not Balenced") ;
			}
			else
			{
				System.out.println(ans+" is Balenced") ;
			}
			
			theStack.popAll(); 
			
			
		}
		
		
	}
	
	public static String getString()
	{
		return keyboard.nextLine();
	}
	
	public static void push(char c)
	{
		
		
		if(c == '(')
		{
			theStack.push(c) ;
		}
		else if(c == '{')
		{
			theStack.push(c) ;
		}
		else if(c == '[')
		{
			theStack.push(c) ;
		}
		else
		{
			
		}
		
		
		
	}
	
	public static void peekAndPop(char c)
	{
		
		
		char current = (char) theStack.peek();
		
		if(current == c)
		{
			theStack.pop();
		}
		else
		{
			
		}
		
	}

}
