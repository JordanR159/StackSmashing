import org.joml.Vector4d;

public class Block 
{
	
	protected World worldObj;
	protected Vector4d pos;
	
	public Block(World world, double x, double y, double width, double height)
	{
		worldObj = world;
		pos = new Vector4d(x, y, width, height);
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

	public Vector4d getPosVector()
	{
		return pos;
	}
	
	public void setPosX(double x)
	{
		pos.x = x;
	}
	
	public void setPosY(double y)
	{
		pos.y = y;
	}
	
	public void setWidth(double width)
	{
		pos.z = width;
	}
	
	public void setHeight(double height)
	{
		pos.w = height;
	}
	
}
