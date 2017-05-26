package queue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import List.List;

public class Main 
{
	public static void main(String[] args) 
	{
		int x = 40;
		int y = 90;
		int z = 30;
		int t = 999;
		//String info = "";
		boolean loop = true;
		String next = "";
		
		SingleReferenceQueue que = new SingleReferenceQueue();
		que.enQue(x);
		que.enQue(y);
		que.enQue(z);
		que.enQue(t);
		
		System.out.println("\nHead: "+que.getHead());
		System.out.println("Tail: "+que.getTail());
		
		que.print();
		System.out.println("Emptying list now");
		
		que.deleteAll();
		
		que.print();
		
		File textFile = new File("Numbers.txt");
		
		try 
		{
			Scanner input = new Scanner(textFile);
			
			while(loop)
			{
				if(input.hasNext())
				{
					next = input.next();
					System.out.println("getting data: "+next);
					
					if(isNumber(next))
					{
						//info += input.next();
						System.out.println("input is number");
						que.enQue(next);
					}
				}
				else
				{
					System.out.println("input has nothing left");
					loop = false;
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("\nThis is the queue");
		System.out.println("Head: "+que.getHead());
		System.out.println("Tail: "+que.getTail());
		que.print();
		
		System.out.println("Positive numbers first:");
		
		int count = que.getAmount();
		int current = 0;
		
		for(int r = 0; r < (count+1); r++)
		{
			current = Integer.parseInt((String) que.get(r)) ;
			
			if(current >= 0)
			{
				System.out.println(current);
				System.out.println("----------");
			}
		}
		
		current = 0;
		
		System.out.println("Negative numbers next:");
		
		for(int r = 0; r < (count+1); r++)
		{
			current = Integer.parseInt((String) que.get(r)) ;
			
			if(current < 0)
			{
				System.out.println(current);
				System.out.println("----------");
			}
		}
	
		/*
		 * iii) 
		 * A circular array is more suitable for this 
		 * example because it can grow which is important as the amount of numbers in the
		 * file is not known.
		 */
	}
	
	public static boolean isNumber(String s)
	{
		try
		{
			int i = Integer.parseInt(s);
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		
		return true;
	}

}
