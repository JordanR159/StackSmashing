import java.util.ArrayList;

public class Explosion
{
	private double xPos;
	private double yPos;
	private double size;
	private double midX;
	private double midY;
	
	public Explosion(Player user) {
		xPos = user.getPosX() - (2 * user.getSize());
		yPos = user.getPosY() - (2 * user.getSize());
		size = 5 * user.getSize();
		midX = user.getPosX() + user.getSize()/2;
		midY = user.getPosY() + user.getSize()/2;
	}
	
	public double calcDamage(Player targ, ArrayList<Block> obstacles) {
		int amountExposed = 0;
		//bottom right corner
		int bRightSlope = 0;
		
		/*if(newPos < check.getPosY() + check.getHeight() && 
				newPos + curr.getSize() > check.getPosY() && 
				curr.getPosX() < check.getPosX()+check.getWidth() && 
				curr.getPosX() + curr.getSize() > check.getPosX()){
					curr.setPosY(check.getPosY() + check.getHeight());*/
		
		return 0;
	}

}
