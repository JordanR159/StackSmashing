import java.util.ArrayList;

public class WorldGenerator 
{
	public ArrayList<Block> generateBlocks(int numBlocks)
	{
		ArrayList<Block> blocks = new ArrayList<Block>();
		double x, y, wid, hei;
		boolean overlap = true;
		x = 0;
		y = 0;
		for(int i = 0; i < numBlocks; i++)
		{
			while(overlap)
			{
				x = Math.random()*1904;
				y = Math.random()*952;
				overlap = false;
				for(int j = 0; j < blocks.size(); j++)
				{
					Block check = blocks.get(j);
					if(x > check.getPosX() && x < check.getPosX()+check.getWidth() 
					&& y > check.getPosY() && y < check.getPosY()+check.getHeight())
						overlap = true;
				}

			}
			wid = Math.random()*50;
			hei = Math.random()*50;
			Block curr = new Block(x,y,wid,hei);
			blocks.add(curr);
			overlap = true;
		}		
		return blocks;
	}

}
