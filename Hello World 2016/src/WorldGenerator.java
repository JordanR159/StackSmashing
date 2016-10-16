import java.util.ArrayList;

public class WorldGenerator 
{
	public static ArrayList<Block> generateBlocks(World world, int width, int height, int numBlocks)
	{
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		int baseX = width % 10 / 2 + 100;
		int baseY = height % 10 / 2 + 10;
		
		width -= baseX + baseX;
		height -= baseY + 200;
		
		double blockX, blockY, blockWidth, blockHeight;
		boolean notOverlapping = true;
		
		int size = 50;
		
		for(int i = 0; i < numBlocks; i++)
		{
			blockX = baseX + (int) (Math.random() * (width - size * 2) / size) * size;
			blockY = baseY + (int) (Math.random() * (height - size) / size) * size;
			blockWidth = (int) (Math.random() * Math.min((width - blockX) / size, 5) + 2) * size;
			blockHeight = (int) (Math.random() * Math.min((height - blockY) / size, 2) + 1) * size;
			
			notOverlapping = true;
			
			Block block = new Block(world, blockX, blockY, blockWidth, blockHeight);
			
			for(int j = 0; j < blocks.size(); j++)
			{
				Block collider = blocks.get(j);
				if(CollisionHelper.colliding(block.getPosVector(), collider.getPosVector()))
				{
					notOverlapping = false;
					j = blocks.size();
				}
			}
			if(notOverlapping)
			{
				blocks.add(block);
			}
		}
		return blocks;
	}

}
