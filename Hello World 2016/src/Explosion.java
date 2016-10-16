import org.joml.Vector2d;
import org.joml.Vector4d;
import org.lwjgl.system.windows.POINT;

import com.polaris.engine.util.MathHelper;

public class Explosion extends Block 
{

	private Player target;
	private double radius;
	public static double[] rayRadii;
	private double damage;

	public Explosion(World world, Player s, Player t, double r)
	{
		super(world, s.getPosX(), s.getPosY(), t.getPosX(), t.getPosY());

		pos.x += s.getSize() / 2;
		pos.y += s.getSize() / 2;
		pos.z = t.getSize() / 2;
		pos.w = t.getSize();

		target = t;
		radius = r;
		rayRadii = new double[360];
		damage = 0;
	}

	public boolean trace()
	{
		boolean hitPlayer = false;

		double rad;

		Vector2d axis = new Vector2d();

		Vector2d point1 = new Vector2d();
		Vector2d point2 = new Vector2d();

		Block block;

		for(int i = 90; i < 91; i ++)
		{
			rad = Math.toRadians(i);

			axis.x = Math.cos(rad);
			axis.y = Math.sin(rad);

			point1.x = 0;
			point1.y = 0;
			
			point2.x = pos.x + radius * axis.x;
			point2.y = pos.y + radius * axis.y;

			for(int j = 0; j < worldObj.getBlocks().size(); j++)
			{
				block = worldObj.getBlocks().get(j);

				checkCollision(block.getPosVector(), axis, point1, point2);
				System.out.println(worldObj.getBlocks().size() + " " + point2.x + " " + point2.y + " " + " " + block.getPosY() + " " + block.getHeight());
			}
			Vector2d checkPoint = new Vector2d(point2);
			
			checkCollision(target.getPosVector(), axis, point1, point2);
			
			if(!MathHelper.isEqual(checkPoint.x, point2.x) || !MathHelper.isEqual(checkPoint.y, point2.y))
			{
				hitPlayer = true;
				damage += rayRadii[i] / radius * 10;
			}
			rayRadii[i] = point2.distance(pos.x, pos.y);
		}
		return hitPlayer;
	}

	public void checkCollision(Vector4d pos, Vector2d axis, Vector2d point1, Vector2d point2)
	{
		if(!MathHelper.isEqual(axis.x, 0))
		{
			point1.x = pos.x;
			point1.y = pos.y - (point1.x - pos.x) / axis.x * axis.y;

			if(point1.y <= pos.y + pos.w && point1.y >= pos.y)
			{
				if(point1.distance(pos.x, pos.y) < point2.distance(pos.x, pos.y))
				{
					point2.x = point1.x;
					point2.y = point1.y;
				}
			}

			point1.x = pos.x + pos.z;
			point1.y = pos.y - (point1.x - pos.x) / axis.x * axis.y;
			if(point1.y <= pos.y + pos.w && point1.y >= pos.y)
			{
				if(point1.distance(pos.x, pos.y) < point2.distance(pos.x, pos.y))
				{
					point2.x = point1.x;
					point2.y = point1.y;
				}
			}
		}
		if(!MathHelper.isEqual(axis.y, 0))
		{
			point1.y = pos.y;
			point1.x = pos.x + (point1.y - pos.y) / axis.y * axis.x;

			if(point1.x <= pos.x + pos.z && point1.x >= pos.x)
			{
				if(point1.distance(pos.x, pos.y) < point2.distance(pos.x, pos.y))
				{
					point2.x = point1.x;
					point2.y = point1.y;
				}
			}
			
			point1.y = pos.y + pos.w;
			point1.x = pos.x + (point1.y - pos.y) / axis.y * axis.x;
			
			if(point1.x <= pos.x + pos.z && point1.x >= pos.x)
			{
				if(point1.distance(pos.x, pos.y) < point2.distance(pos.x, pos.y))
				{
					point2.x = point1.x;
					point2.y = point1.y;
				}
			}
		}
	}
	
	public double getTargetDamage()
	{
		return damage;
	}

}
