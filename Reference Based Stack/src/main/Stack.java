package main;

import List.List;

public class Stack 
{
	private List listNode = new List("the stack") ; 
	private int counter = 0 ;
	
	public Stack()
	{
		
	}
	
	public boolean isEmpty()
	{
		return listNode.isEmpty() ;
	}
	
	public void push(char newItem)
	{
		listNode.insertAtBack(newItem) ;
		counter ++ ;
	}
	
	
	public void pop()
	{
		listNode.removeFromBack() ;
		counter-- ;
	}
	
	
	public void popAll()
	{
		for(int x = 0 ; x < counter ; x++)
		{
			pop() ;
		}
		counter = 0 ;
	}
	
	
	public char peek()
	{
		return listNode.get(counter) ;
	}
	
	public void print()
	{
		listNode.print();
	}
	
	public int getCounter()
	{
		return counter ;
	}
}
