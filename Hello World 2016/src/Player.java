import org.joml.Vector3d;

public class Player 
{
	private Vector3d pos;
	private Vector3d movement;
	private int numJumps;
	
	public Player(double x, double y, double size)
	{
		pos = new Vector3d(x, y, size);
		movement = new Vector3d(0, 0, 0);
		numJumps = 2;
	}
	
	public double getPosX()
	{
		return pos.x;
	}

	public double getPosY()
	{
		return pos.y;
	}
	
	public double getSize()
	{
		return pos.z;
	}
	
	public double getVelX()
	{
		return movement.x;
	}
	
	public double getVelY()
	{
		return movement.y;
	}
	
	public double getAccelX()
	{
		return movement.z;
	}
	
	public int getNumJumps()
	{
		return numJumps;
	}
	
	public void setPosX(double x)
	{
		pos.x = x;
	}
	
	public void setPosY(double y)
	{
		pos.y = y;
	}
	
	public void setSize(double s)
	{
		pos.z = s;
	}
	
	public void setVelX(double vx)
	{
		movement.x = vx;		
	}
	
	public void setVelY(double vy)
	{
		movement.y = vy;
	}
	
	public void setAccelX(double ax)
	{
		movement.z = ax;
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
