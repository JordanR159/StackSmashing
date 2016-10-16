import org.joml.Vector2d;
import org.joml.Vector4d;

public class Explosion extends Block 
{
	
	private Player target;
	private double radius;
	private double[] rayRadii;
	private double damage;

	public Explosion(World world, Player s, Player t, double r)
	{
		super(world, s.getPosX(), s.getPosY(), t.getPosX(), t.getPosY());
		
		pos.x += s.getSize() / 2;
		pos.y += s.getSize() / 2;
		pos.z += t.getSize() / 2;
		pos.w += t.getSize() / 2;
		
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
		
		Vector2d point3 = new Vector2d();
		Vector2d point4 = new Vector2d();
		
		double length1, length2, length3, length4;
		
		Block block;
		
		for(int i = 0; i < 360; i ++)
		{
			rad = Math.toRadians(i);
			
			axis.x = Math.cos(rad);
			axis.y = Math.sin(rad);
			
			point1.x = pos.x;
			point1.y = pos.y;
			
			point2.x = pos.x + radius;
			point2.y = pos.y + radius;
			
			length1 = point1.dot(axis);
			length2 = point2.dot(axis);
			
			for(int j = 0; j < worldObj.getBlocks().size(); j++)
			{
				block = worldObj.getBlocks().get(j);
				
				getPoints(block.getPosVector(), point3, point4, i);
				
				length3 = point3.dot(axis);
				length4 = point4.dot(axis);
				
				if(length1 <= length3 && length2 >= length3)
					length2 = length3;
			}
			
			getPoints(target.getPosVector(), point3, point4, i);
			
			length3 = point3.dot(axis);
			length4 = point4.dot(axis);
			
			if(length1 <= length3 && length2 >= length3)
			{
				hitPlayer = true;
				length2 = length3 + length4;
				length2 /= 2;
				damage += (length2 - length1) / 10d;
				System.out.println(i);
			}
			
			rayRadii[i] = length2 - length1;
			
		}
		return hitPlayer;
	}
	
	private void getPoints(Vector4d pos, Vector2d point3, Vector2d point4, int degree)
	{
		if(degree % 180 <= 90)
		{
			point3.x = pos.x;
			point3.y = pos.y + pos.w;
			
			point4.x = pos.x + pos.z;
			point4.y = pos.y;
		}
		else
		{
			point3.x = pos.x + pos.z;
			point3.y = pos.y + pos.w;
			
			point4.x = pos.x;
			point4.y = pos.y;
		}
		
		if(degree > 180)
		{
			Vector2d temp = point3;
			point3 = point4;
			point4 = temp;
		}
	}
	
	public double getTargetDamage()
	{
		return damage;
	}
	
}
