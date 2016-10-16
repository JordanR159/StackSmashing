import org.joml.Vector4d;

public class CollisionHelper 
{
	
	public static boolean colliding(Vector4d rect1, Vector4d rect2)
	{
		if(rect1.x < rect2.x + rect2.z)
			return false;
		if(rect1.x + rect1.z > rect2.x)
			return false;
		if(rect1.y < rect2.y + rect2.w)
			return false;
		if(rect1.y + rect1.w > rect2.y)
			return false;
		return true;
	}

}
