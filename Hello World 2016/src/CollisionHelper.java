import org.joml.Vector4d;

public class CollisionHelper 
{
	
	public static boolean colliding(Vector4d rect1, Vector4d rect2)
	{
		if(Math.abs(rect1.x - rect2.x + (rect1.z - rect2.z) / 2d) >= (rect1.z + rect2.z) / 2d)
			return false;
		if(Math.abs(rect1.y - rect2.y + (rect1.w - rect2.w) / 2d) >= (rect1.w + rect2.w) / 2d)
			return false;
		return true;
	}
	
}
