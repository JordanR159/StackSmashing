
public class Explosion
{
	private double xPos;
	private double yPos;
	private double size;
	
	public Explosion(Player user) {
		xPos = user.getPosX() - (2 * user.getSize());
		yPos = user.getPosY() - (2 * user.getSize());
		size = 5 * user.getSize();
		
	}
	
	public boolean isOnTarget(Player targ) {
		
		
		return true;
	}

}
