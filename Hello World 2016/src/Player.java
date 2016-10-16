
public class Player 
{
	private double posX;
	private double posY;
	private double size;
	private double velocityX;
	private double velocityY;
	private int numJumps;
	
	public Player(double x, double y, double s)
	{
		posX = x;
		posY = y;
		size = s;
		velocityX = 0;
		velocityY = 0;
		numJumps = 2;
	}
	
	public double getPosX()
	{
		return posX;
	}

	public double getPosY()
	{
		return posY;
	}
	
	public double getSize()
	{
		return size;
	}
	
	public double getVelocityX()
	{
		return velocityX;
	}
	
	public double getVelocityY()
	{
		return velocityY;
	}
	
	public int getNumJumps()
	{
		return numJumps;
	}
	
	public void setPosX(double x)
	{
		posX = x;
	}
	
	public void setPosY(double y)
	{
		posY = y;
	}
	
	public void setSize(double s)
	{
		size = s;
	}
	
	public void setVelocityX(double vx)
	{
		velocityX = vx;		
	}
	
	public void setVelocityY(double vy)
	{
		velocityY = vy;
	}
	
	public void useJump()
	{
		numJumps--;
	}
	
	public void resetJumps()
	{
		numJumps = 2;
	}
	
	public void nullifyJumps()
	{
		numJumps = 0;
	}


}
