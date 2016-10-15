import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

public class World 
{
	private double width;
	private double height;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Block> blocks = new ArrayList<Block>();

	public World(int numPlayers, int numBlocks)
	{
		width = 1904;
		height = 952;
		setPlayers(numPlayers);
		blocks = WorldGenerator.generateBlocks(numBlocks);
	}

	public void setPlayers(int numPlayers)
	{
		for(int i = 0; i < numPlayers; i++)
		{
			double posX = (width*(i+1))/(numPlayers+1)-50.00;
			double posY = height-100.00;
			Player curr = new Player(posX,posY,100.00);
			players.add(curr);
		}
	}

	public double getWidth()
	{
		return width;
	}

	public double getHeight()
	{
		return height;
	}
	
	public void update(double delta)
	{
		
	}

	public int keyPressed(int keyId, int mods)
	{
		//GLFW.GLFW_KEY_<TYPE THE KEY OUT>
		if(keyId == GLFW.GLFW_KEY_W)
		{
			Player curr = players.get(0);
			if(curr.getNumJumps() > 0)
			{
				curr.useJump();
				curr.setVelocityY(0);
				double newPos = 50.00 + curr.getPosY();
				if(newPos > 0 && newPos+curr.getSize() < height){
					
					boolean collided = false;
					//block collision on jumps
					for(int j = 0; j < blocks.size(); j++)
					{
						Block check = blocks.get(j);
						if(newPos < check.getPosY() + check.getHeight() && 
							newPos + curr.getSize() > check.getPosY() && 
							curr.getPosX() < check.getPosX()+check.getWidth() && 
							curr.getPosX() + curr.getSize() > check.getPosX()){
								curr.setPosY(check.getPosY() + check.getHeight());
								//can't jump anymore if collide with bottom of block
								curr.nullifyJumps();
								collided = true;
						}
					}
					if(!collided)
						curr.setPosY(newPos);

				}
				else if(newPos <= 0)
					curr.setPosY(0);
				else
				{
					curr.setPosY(height-curr.getSize());
					curr.resetJumps();
				}
			}
			players.set(0,curr);
		}

		if(keyId == GLFW.GLFW_KEY_A)
		{
			Player curr = players.get(0);
			curr.setVelocityX(curr.getVelocityX()-1);
			double newPos = curr.getVelocityX() + curr.getPosX();
			if(newPos > 0 && newPos+curr.getSize() < width) {
				
				//block collision
				boolean collided = false;
				for(int j = 0; j < blocks.size(); j++)
				{
					Block check = blocks.get(j);
					if(newPos < check.getPosY() + check.getHeight() && 
						newPos + curr.getSize() > check.getPosY() && 
						curr.getPosX() < check.getPosX()+check.getWidth() && 
						curr.getPosX() + curr.getSize() > check.getPosX()){
							//curr.setPosX(check.getPosX);
							//can't jump anymore if collide with bottom of block
							collided = true;
					}
				}
				if(!collided)
					curr.setPosX(newPos);
				
			}
			else if(newPos <= 0)
			{
				curr.setPosX(0);
				curr.setVelocityX(0);
			}
			else
			{
				curr.setPosX(width-curr.getSize());
				curr.setVelocityX(0);
			}
			players.set(0,curr);
		}

		if(keyId == GLFW.GLFW_KEY_S)
		{
			Player curr = players.get(0);
			curr.setVelocityY(curr.getVelocityY()+10);
			double newPos = curr.getVelocityY() + curr.getPosY();
			if(newPos > 0 && newPos+curr.getSize() < height)
				curr.setPosY(newPos);
			else if(newPos <= 0)
			{
				curr.setPosY(0);
				curr.setVelocityY(0);
			}
			else
			{
				curr.setPosY(width-curr.getSize());
				curr.setVelocityY(0);
				curr.resetJumps();
			}
			players.set(0,curr);
		}

		if(keyId == GLFW.GLFW_KEY_D)
		{
			Player curr = players.get(0);
			curr.setVelocityX(curr.getVelocityX()+1);
			double newPos = curr.getVelocityX() + curr.getPosX();
			if(newPos > 0 && newPos+curr.getSize() < width)
				curr.setPosX(newPos);
			else if(newPos <= 0)
				curr.setPosX(0);
			else
				curr.setPosX(width-curr.getSize());
			players.set(0,curr);
		}

		if(keyId == GLFW.GLFW_KEY_UP)
		{
			Player curr = players.get(1);
			if(curr.getNumJumps() > 0)
			{
				curr.useJump();
				curr.setVelocityY(0);
				double newPos = 50.00 + curr.getPosY();
				if(newPos > 0 && newPos+curr.getSize() < height)
					curr.setPosY(newPos);
				else if(newPos <= 0)
					curr.setPosY(0);
				else
				{
					curr.setPosY(height-curr.getSize());
					curr.resetJumps();
				}					
			}
			players.set(1,curr);
		}

		if(keyId == GLFW.GLFW_KEY_DOWN)
		{
			Player curr = players.get(1);
			curr.setVelocityY(curr.getVelocityY()+10);
			double newPos = curr.getVelocityY() + curr.getPosY();
			if(newPos > 0 && newPos+curr.getSize() < height)
				curr.setPosY(newPos);
			else if(newPos <= 0)
			{
				curr.setPosY(0);
				curr.setVelocityY(0);
			}
			else
			{
				curr.setPosY(width-curr.getSize());
				curr.setVelocityY(0);
				curr.resetJumps();
			}
			players.set(1,curr);
		}

		if(keyId == GLFW.GLFW_KEY_LEFT)
		{
			Player curr = players.get(1);
			curr.setVelocityX(curr.getVelocityX()-1);
			double newPos = curr.getVelocityX() + curr.getPosX();
			if(newPos > 0 && newPos+curr.getSize() < width)
				curr.setPosX(newPos);
			else if(newPos <= 0)
				curr.setPosX(0);
			else
				curr.setPosX(width-curr.getSize());
			players.set(1,curr);
		}

		if(keyId == GLFW.GLFW_KEY_RIGHT)
		{
			Player curr = players.get(1);
			curr.setVelocityX(curr.getVelocityX()+1);
			double newPos = curr.getVelocityX() + curr.getPosX();
			if(newPos > 0 && newPos+curr.getSize() < width)
				curr.setPosX(newPos);
			else if(newPos <= 0)
				curr.setPosX(0);
			else
				curr.setPosX(width-curr.getSize());
			players.set(1,curr);
		}
		for(int i = 0; i < players.size(); i++)
		{
			Player fall = players.get(i);
			fall.setVelocityY(fall.getVelocityY()+10);
			double newPos = fall.getVelocityY() + fall.getPosY();
			if(newPos > 0 && newPos+fall.getSize() < height)
				fall.setPosY(newPos);
			else if(newPos <= 0)
			{
				fall.setPosY(0);
				fall.setVelocityY(0);
			}
			else
			{
				fall.setPosY(width-fall.getSize());
				fall.setVelocityY(0);
				fall.resetJumps();
			}
			players.set(i,fall);

		}
		return 1;
	}

	public int keyHeld(int keyId, int called, int mods)
	{
		return -1;
	}

	public void keyRelease(int keyId, int mods)
	{

	}


	public ArrayList<Player> getPlayers()
	{
		return players;
	}

	public ArrayList<Block> getBlocks()
	{
		return blocks;
	}

}
