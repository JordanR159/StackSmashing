import java.util.ArrayList;

public class Beam 
{
	private double startX;
	private double startY;
	private boolean toRight;
	private double endX;
	
	public Beam(Player shooter, Player target)
	{
		startX = shooter.getPosX() + shooter.getSize() / 2;
		startY = shooter.getPosY() + shooter.getSize() / 2;
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
	
	public double getStartX()
	{
		return startX;
	}
	
	public double getStartY()
	{
		return startY;
	}
	
	public double getEndX()
	{
		return endX;
	}
	

}
