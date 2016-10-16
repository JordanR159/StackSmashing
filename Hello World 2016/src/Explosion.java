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
		
		//get slopes
		double bRightSlope = ((targ.getPosY() + targ.getSize()) - midY) /
							 ((targ.getPosX() + targ.getSize()) - midX);
		double cBR = midY - bRightSlope*midX;
		
		double bLeftSlope = ((targ.getPosY() + targ.getSize()) - midY) /
							(targ.getPosX() - midX);
		double cBL = midY - bLeftSlope*midX;
		
		double tRightSlope = ((targ.getPosY() - midY) /
				 			 ((targ.getPosX() + targ.getSize()) - midX));
		double cTR = midY - tRightSlope*midX;
		
		double tLeftSlope = (targ.getPosY() - midY) /
							(targ.getPosX() - midX);
		double cTL = midY - tLeftSlope*midX;
		
		boolean hitBR = true;
		boolean hitBL = true;
		boolean hitTR = true;
		boolean hitTL = true;
		//bottom right
		
		if(midX > targ.getPosX()) {
			//bottom right
			for(double x = midX; x > targ.getPosX(); x--) {
				double y = bRightSlope*x + cBR;
				for(Block check : obstacles) {
					if(y < check.getPosY() + check.getHeight() && 
					   y  > check.getPosY() && 
					   x < check.getPosX()+check.getWidth() && 
						x > check.getPosX()){
							hitBR = false;	
							break;
					}
				
				}
				if(!hitBR){
					break;
				}
			}
			
			//bottom left
			for(double x = midX; x > targ.getPosX(); x--) {
				double y = bLeftSlope*x + cBL;
				for(Block check : obstacles) {
					if(y < check.getPosY() + check.getHeight() && 
					   y  > check.getPosY() && 
					   x < check.getPosX()+check.getWidth() && 
						x > check.getPosX()){
							hitBL = false;	
							break;
					}
				
				}
				if(!hitBL){
					break;
				}
			}
			
			//top right
			for(double x = midX; x > targ.getPosX(); x--) {
				double y = tRightSlope*x + cTR;
				for(Block check : obstacles) {
					if(y < check.getPosY() + check.getHeight() && 
					   y  > check.getPosY() && 
					   x < check.getPosX()+check.getWidth() && 
						x > check.getPosX()){
							hitTR = false;	
							break;
					}
				
				}
				if(!hitTR){
					break;
				}
			}
			
			//top left
			for(double x = midX; x > targ.getPosX(); x--) {
				double y = tLeftSlope*x + cTL;
				for(Block check : obstacles) {
					if(y < check.getPosY() + check.getHeight() && 
					   y  > check.getPosY() && 
					   x < check.getPosX()+check.getWidth() && 
						x > check.getPosX()){
							hitTL = false;	
							break;
					}
				}
				if(!hitTL){
					break;
				}
			}
		}
		else {
			//bottom right
			for(double x = midX; x < targ.getPosX(); x++) {
				double y = bRightSlope*x + cBR;
				for(Block check : obstacles) {
					if(y < check.getPosY() + check.getHeight() && 
					   y  > check.getPosY() && 
					   x < check.getPosX()+check.getWidth() && 
						x > check.getPosX()){
							hitBR = false;	
							break;
					}
				
				}
				if(!hitBR){
					break;
				}
			}
			
			//bottom left
			for(double x = midX; x < targ.getPosX(); x++) {
				double y = bLeftSlope*x + cBL;
				for(Block check : obstacles) {
					if(y < check.getPosY() + check.getHeight() && 
					   y  > check.getPosY() && 
					   x < check.getPosX()+check.getWidth() && 
						x > check.getPosX()){
							hitBL = false;	
							break;
					}
				}
				if(!hitBL){
					break;
				}
			}
			
			//top right
			for(double x = midX; x < targ.getPosX(); x++) {
				double y = tRightSlope*x + cTR;
				for(Block check : obstacles) {
					if(y < check.getPosY() + check.getHeight() && 
					   y  > check.getPosY() && 
					   x < check.getPosX()+check.getWidth() && 
						x > check.getPosX()){
							hitTR = false;	
							break;
					}
				
				}
				if(!hitTR){
					break;
				}
			}
			
			//top lefts
			for(double x = midX; x < targ.getPosX(); x++) {
				double y = tLeftSlope*x + cTL;
				for(Block check : obstacles) {
					if(y < check.getPosY() + check.getHeight() && 
					   y  > check.getPosY() && 
					   x < check.getPosX()+check.getWidth() && 
						x > check.getPosX()){
							hitTL = false;	
							break;
					}
				}
				if(!hitTL){
					break;
				}
			}
		}
		if(hitBR){
			amountExposed++;
		}
		if(hitBL){
			amountExposed++;
		}
		if(hitTR){
			amountExposed++;
		}
		if(hitTL){
			amountExposed++;
		}
		
		
		return 0;
	}

}
