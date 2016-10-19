import java.util.ArrayList;

import org.joml.Vector4d;

public class WorldGenerator 
{
	public static ArrayList<Block> generateBlocks(World world, int width, int height, int numBlocks)
	{
		ArrayList<Block> blocks = new ArrayList<Block>();
		
		int baseX = width % 10 / 2 + 100;
		int baseY = height % 10 / 2 + 100;
		
		width -= baseX + baseX;
		height -= baseY + 100;
		
		double blockX, blockY, blockWidth, blockHeight;
		boolean notOverlapping = true;
		
		int size = 50;
		
		for(int i = 0; i < numBlocks; i++)
		{	
			int numTallBlocks = 0;
			blockX = baseX + (int) (Math.random() * (width - size * 2) / size) * size;
			blockY = baseY + (int) (Math.random() * (height - size) / size) * size;
			blockWidth = (int) (Math.random() * Math.min((width - blockX) / size, 5) + 1) * size;
			if(blockWidth <= 100 && numTallBlocks <= 4) {
				if(blockWidth == 100)
					blockWidth = 75;
				blockHeight = (int)(Math.random() * Math.min((height - blockY) / size, 5) + 2) * size;
				numTallBlocks++;
			}
			else
				blockHeight = (int) (Math.random() * Math.min((height - blockY) / size, 2) + 1) * size;
			
			notOverlapping = true;
			
			Block block = new Block(world, blockX, blockY, blockWidth, blockHeight);
			
			for(int j = 0; j < blocks.size(); j++)
			{
				Block collider = blocks.get(j);
				Vector4d colVec = new Vector4d(collider.getPosVector());
				colVec.x -= 20;
				colVec.y -= 20;
				colVec.z += 20;
				colVec.w += 20;
				if(CollisionHelper.colliding(block.getPosVector(), colVec))
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
