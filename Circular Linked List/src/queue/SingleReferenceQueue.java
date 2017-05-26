package queue;

import List.List;

public class SingleReferenceQueue extends List implements QueueInterface 
{
	public SingleReferenceQueue()
	{
		
	}
	
	@Override
	public boolean isEmpty() 
	{
		return super.isEmpty();
	}
	
	public void enQue(Object o) 
	{
		super.insertAtBack(o);
	}

	public void deQue() 
	{
		super.removeFromFront();
	}
}
