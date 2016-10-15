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
			if(wid < 0.111)
				wid = 100;
			else if(wid > 0.555)
				wid = 200;
			else
				wid = 150;
			if(hei < 0.555)
				hei = 100;
			else if(hei > 0.999)
				hei = 200;
			else
				hei = 150;
			while(overlap)
			{
				x = Math.random()*1600+50;
				y = Math.random()*500+150;
				overlap = false;
				for(int j = 0; j < blocks.size(); j++)
				{
					Block check = blocks.get(j);
					if((x <= check.getPosX()+check.getWidth()) && (x+wid >= check.getPosX())
					&& (y <= check.getPosY()+check.getHeight()) && (y+hei >= check.getPosY()))
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
