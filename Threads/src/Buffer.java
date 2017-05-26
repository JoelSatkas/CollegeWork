
public class Buffer 
{
	private int randomInt ;
	private boolean valueSwitch = false; 
	/*
	 * 0 = value was consumed and new value was not given
	 * 1 = value was given and not consumed yet
	 */
	private boolean sleep = false ;
	
	public Buffer()
	{
		System.out.println("Creating: Buffer");
		
	}
	
	public boolean getValueSwitch()
	{
		return valueSwitch;
	}
	
	public synchronized void setValueSwitch(boolean b)
	{
		valueSwitch = b;
	}
	
	public int getNumber()
	{
		return randomInt ;
	}
	
	public void setNumber(int number)
	{
		randomInt = number;
	}
	
	public boolean finish()
	{
		return sleep ;
	}
	
	public void setFinish()
	{
		sleep = true ;
	}
	
	
}
