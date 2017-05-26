package ie.OOSSP.Project1;

public class calculationBean 
{
	
	private int[][] votes;
	private int amountOfVotes;
	
	public calculationBean()
	{
		votes = null;
	}
	
	public void calculateAll(String[] allVotes, int amount)
	{
		amountOfVotes = allVotes.length;
		votes = new int[allVotes.length][amount];
		
		for(int x = 0 ; x < allVotes.length ; x++)
		{
			votes[x] = calcualteOne(allVotes[x],amount);
		}
		
		
	}
	
	
	public int[] calcualteOne(String vote, int amount)
	{
		
		String[] votes = new String[amount];
		String singleVote = "";
		int position = 0;
		
		// brake down the string into separate votes.
		for(int x = 0 ; x < vote.length() ; x++)
		{
			singleVote += vote.charAt(x);

			if(vote.charAt(x+1) == ':')
			{
				votes[position] = singleVote;
				singleVote = "";
				position++;
				x++;
			}
			else
			{
				
			}
		}
		
		int[] intVotes = new int[amount];
		
		// convert String array to int array for comparison.
		for(int x = 0 ; x < amount ; x++)
		{
			if(votes[x] == null || votes[x].equals("null"))
			{
				intVotes[x] = -1;
			}
			else
			{
				intVotes[x] = Integer.parseInt(votes[x]);
			}
			
		}
		
		
		
		return intVotes;
	}
	
	
	
	public int getScore(int position, int amount)
	{
		int score = 0;
		
		for(int x = 0 ; x < amountOfVotes ; x++)
		{
			
			
			for(int y = 0 ; y < amount ; y++)
			{
				System.out.print(votes[x][y]+" ");
			}
			
			System.out.println("");
			
			if(votes[x][position] == -1)
			{
				score += 0;
			}
			else
			{
				score += (amount - (votes[x][position]));
				System.out.println("will add "+amount+" - "+ votes[x][position]+" = "+(amount - (votes[x][position] - 1)));
			}
		}
		
		
		return score;
	}
	
	
}
