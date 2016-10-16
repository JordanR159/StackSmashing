import java.util.ArrayList;

public class Beam extends Block
{
	private boolean toRight;
	private Player source;
	private Player target;
	
	public Beam(World world, Player s, Player t)
	{
		super(world, s.getPosX(), s.getPosY(), t.getPosX(), t.getPosY());
		
		source = s;
		target = t;
		
		pos.x += s.getSize() / 2;
		pos.y += s.getSize() / 2;
		pos.z += t.getSize() / 2;
		pos.w += t.getSize() / 2;
		
		if(s.getPosX() > t.getPosX())
			toRight = false;
		else
			toRight = true;
	}
	
	public boolean trace()
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
