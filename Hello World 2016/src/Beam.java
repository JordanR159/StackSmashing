import java.util.ArrayList;

public class Beam extends Block
{
	private boolean toRight;
	
	public Beam(World world, Player shooter, Player target)
	{
		super(world, shooter.getPosX(), shooter.getPosY(), target.getPosX(), target.getPosY());
		pos.x += shooter.getSize() / 2;
		pos.y += shooter.getSize() / 2;
		pos.z += target.getSize() / 2;
		pos.w += target.getSize() / 2;
		if(shooter.getPosX() > target.getPosX())
			toRight = false;
		else
			toRight = true;
	}
	
	public boolean isOnTarget(Player def, ArrayList<Block> obstacles)
	{
		if(toRight)
		{
			if(!(startY > def.getPosY() && startY < def.getPosY()+def.getSize()))
			{
				endX = 1904;
				return false;
			}
			for(double i = startX; i < 1954; i++)
			{
				for(Block curr : obstacles)
				{
					if((i > curr.getPosX() && i<curr.getPosX()+curr.getWidth())
					&& (startY > curr.getPosY() && startY < curr.getPosY()+curr.getHeight()))
					{
						endX = i;
						return false;
					}
				}
				if((i > def.getPosX() && i < def.getPosX()+def.getSize()))
						return true;
			}
		}
		else
		{
			if(!(startY > def.getPosY() && startY < def.getPosY()+def.getSize()))
			{
				endX = 0;
				return false;
			}
			for(double i = startX; i > 0; i--)
			{
				for(Block curr : obstacles)
				{
					if((i > curr.getPosX() && i<curr.getPosX()+curr.getWidth())
					&& (startY > curr.getPosY() && startY < curr.getPosY()+curr.getHeight()))
					{
						endX = i;
						return false;
					}
				}
				if((i > def.getPosX() && i < def.getPosX()+def.getSize()))
						return true;
			}
		}
		return false;
	}

}
