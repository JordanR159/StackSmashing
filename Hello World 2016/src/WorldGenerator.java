import java.util.ArrayList;

public class WorldGenerator 
{
	public static ArrayList<Block> generateBlocks(int numBlocks)
	{
		ArrayList<Block> blocks = new ArrayList<Block>();
		double x, y, wid, hei;
		boolean overlap = true;
		x = 0;
		y = 0;
		for(int i = 0; i < numBlocks; i++)
		{
			wid = Math.random();
			hei = Math.random();
			if(wid < 0.333)
				wid = 50;
			else if(wid > 0.666)
				wid = 150;
			else
				wid = 100;
			if(hei < 0.555)
				hei = 50;
			else if(wid > 0.888)
				hei = 150;
			else
				hei = 100;
			while(overlap)
			{
				x = Math.random()*1654+50;
				y = Math.random()*521+150;
				overlap = false;
				for(int j = 0; j < blocks.size(); j++)
				{
					Block check = blocks.get(j);
					if((x >= check.getPosX() && x <= check.getPosX()+check.getWidth()) 
					|| (x+wid >= check.getPosX() && x+wid <= check.getPosX()+check.getWidth())
					&& (y >= check.getPosY() && y <= check.getPosY()+check.getHeight())
					|| (y+hei >= check.getPosY() && y+hei <= check.getPosY()+check.getHeight()))
						overlap = true;
				}

			}
			Block curr = new Block(x,y,wid,hei);
			blocks.add(curr);
			overlap = true;
		}		
		return blocks;
	}

}
