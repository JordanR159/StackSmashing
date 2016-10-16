import org.joml.Vector3d;
import org.joml.Vector4d;

public class Player 
{
	private Vector4d pos;
	private Vector3d movement;
	private int numJumps;
	private Beam currentBeam;
	
	public Player(double x, double y, double size)
	{
		pos = new Vector4d(x, y, size, size);
		movement = new Vector3d(0, 0, 0);
		numJumps = 2;
	}
	
	public void update(double delta)
	{
		movement.x /= 2d;
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
		pos.w = s;
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
	
	public Vector4d getPosVector()
	{
		return pos;
	}
	
	public Vector3d getMoveVector()
	{
		return movement;
	}
	
	public void setBeam(Beam b)
	{
		currentBeam = b;
	}
	
	public Beam getBeam()
	{
		return currentBeam;
	}

}
