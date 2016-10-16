import org.joml.Vector4d;

public class Block 
{
	
	private Vector4d pos;
	
	public Block(double x, double y, double w, double h)
	{
		pos = new Vector4d(x, y, w, h);
	}
	
	public double getPosX()
	{
		return pos.x;
	}
	
	public double getPosY()
	{
		return pos.y;
	}
	
	public double getWidth()
	{
		return pos.z;
	}
	
	public double getHeight()
	{
		return pos.w;
	}

	public Vector4d getVector()
	{
		return pos;
	}
	
	public String toString()
	{
		return "x:" + getPosX() + " y:" + getPosY() + " width:" + getWidth() + " height:" + getHeight();
	}
}
