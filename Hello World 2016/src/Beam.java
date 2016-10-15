import java.util.ArrayList;

public class Beam 
{
	private double startX;
	private double startY;
	private boolean toRight;
	
	public boolean useBeam(Player att,Player def,ArrayList<Block> obstacles)
	{
		Beam shot = new Beam(att.getPosX(),att.getPosY(),att.getVelocityX());
		return isOnTarget(def,obstacles);
		
	}
	
	public Beam(double x,double y,double vx)
	{
		startX = x;
		startY = y;
		if(vx > 0)
			toRight = true;
		else if(vx < 0)
			toRight = false;
		else if(x > 802)
			toRight = false;
		else
			toRight = true;
		

	}
	
	public boolean isOnTarget(Player def, ArrayList<Block> obstacles)
	{
		return false;
	}
	

}
