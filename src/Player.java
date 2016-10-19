import org.joml.Vector3d;

public class Player extends Block
{
	private Vector3d movement;
	private int numJumps;
	private Beam currentBeam;
	private double beamTicks = .25;
	private Explosion explosion;
	private double explosionTicks = .1d;
	private long dashStartT;
	private long slamStartT;
	private boolean isSlamming;
	private boolean isDashing;
	
	public Player(World world, double x, double y, double size)
	{
		super(world, x, y, size, size);
		movement = new Vector3d(0, 0, 0);
		numJumps = 2;
	}
	
	public void update(double delta)
	{
		movement.x /= 2d;
		if(currentBeam != null)
		{
			beamTicks -= delta;
			if(beamTicks <= 0)
			{
				currentBeam = null;
			}
		}
		if(explosion != null)
		{
			explosionTicks -= delta;
			if(explosionTicks <= 0)
			{
				explosion = null;
			}
		}
	}
	
	public void useJump()
	{
		numJumps--;
	}
	
	public void addJump()
	{
		numJumps++;
	}
	
	public void resetJumps()
	{
		numJumps = 2;
	}
	
	public void nullifyJumps()
	{
		numJumps = 0;
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
	
	public double getBeamTicks()
	{
		return beamTicks;
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
	
	public Vector3d getMoveVector()
	{
		return movement;
	}
	
	public void setBeam(Beam b)
	{
		currentBeam = b;
		beamTicks = .25;
	}
	
	public Beam getBeam()
	{
		return currentBeam;
	}
	
	public double getExplosionTicks()
	{
		return explosionTicks;
	}
	
	public void setExplosion(Explosion e)
	{
		explosion = e;
		explosionTicks = .1d;
	}
	
	public Explosion getExplosion()
	{
		return explosion;
	}
	
	public void startSlam()
	{
		slamStartT = System.currentTimeMillis();
		isSlamming = true;
	}
	
	public void startDash()
	{
		dashStartT = System.currentTimeMillis();
		isDashing = true;
	}
	
	public boolean checkSlam()
	{
		if(System.currentTimeMillis() - slamStartT <= 50 && movement.y > 0)
			return true;
		else
			return false;
	}
	
	public boolean checkDash()
	{
		if(System.currentTimeMillis() - dashStartT <= 100)
			return true;
		else
			return false;
	}
	
	public boolean isSlamming()
	{
		return isSlamming;
	}
	
	public boolean isDashing()
	{
		return isDashing;
	}
	
	public void stopSlam()
	{
		isSlamming = false;
	}
	
	public void stopDash()
	{
		isDashing = false;
	}
}
