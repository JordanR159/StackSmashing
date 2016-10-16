import java.util.ArrayList;

import org.joml.Vector4d;
import org.lwjgl.glfw.GLFW;

public class World 
{
	private int width;
	private int height;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Block> blocks = new ArrayList<Block>();
	private int loser = -1;

	public World(int numPlayers, int numBlocks)
	{
		width = 1904;
		height = 950;
		setPlayers(numPlayers);
		blocks = WorldGenerator.generateBlocks(this, width, height, numBlocks);
	}

	public void setPlayers(int numPlayers)
	{
		Player one = new Player(this, 100.00, height-100.00, 100.00);
		Player two = new Player(this, width-200.00, height-100.00, 100.00);
		players.add(one);
		players.add(two);
		/*for(int i = 0; i < numPlayers; i++)
		{s
			double posX = (width*(i+1))/(numPlayers+1)-50.00;
			double posY = height-100.00;
			Player curr = new Player(posX,posY,100.00);
			players.add(curr);
		}*/
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
		if(loser == -1)
		{
			Player player;
			Player otherPlayer;
			Block playerBlock;
			for(int i = 0; i < players.size(); i++)
			{
				player = players.get(i);
				
				if(player.getSize() <= 25)
				{
					loser = i;
					break;
				}
				
				otherPlayer = players.get(players.size()-i-1);
				player.update(delta);
				
				playerBlock = new Block(this, otherPlayer.getPosX(), otherPlayer.getPosY(), otherPlayer.getSize(), otherPlayer.getSize());
				blocks.add(playerBlock);
				
				double vx = player.getVelX();
				
				if(vx > 0)
					player.setVelX(player.getVelX() - 0.25);
				else if(vx < 0)
					player.setVelX(player.getVelX() + 0.25);

				player.setVelY(player.getVelY()+3);
				double newPos = player.getVelY() + player.getPosY();
				if(newPos > 0 && newPos+player.getSize() < height) {
					boolean collided = false;
					//block collisions	
					for(int j = 0; j < blocks.size(); j++)
					{
						if(checkCollision(player, newPos, false, blocks.get(j))){
							if(player.getVelY() < 0) {
								player.setVelY(0);
								player.nullifyJumps();
								player.setPosY(blocks.get(j).getPosY() + blocks.get(j).getHeight());
							}
							else {
								player.resetJumps();
								player.setPosY(blocks.get(j).getPosY() - player.getSize());
								player.setVelY(0);
							}
							collided = true;
						}
					}
					if(!collided)
						player.setPosY(newPos);
				}
				else if(newPos <= 0)
				{
					player.setPosY(0);
					player.setVelY(0);
				}
				else
				{
					player.setPosY(height-player.getSize());
					player.setVelY(0);
					player.resetJumps();
				}		
				blocks.remove(blocks.indexOf(playerBlock));
			}
		}

	}
	public int keyPressed(int keyId, int mods)
	{
		if(loser == -1)
		{
			Player player = players.get(0);
			Player playerTwo = players.get(1);
			Block pBlock = new Block(this,player.getPosX(),player.getPosY(),player.getSize(),player.getSize());
			Block pBlockTwo = new Block(this,playerTwo.getPosX(),playerTwo.getPosY(),playerTwo.getSize(),playerTwo.getSize());
			blocks.add(pBlockTwo);

			//GLFW.GLFW_KEY_<TYPE THE KEY OUT>
			if(keyId == GLFW.GLFW_KEY_W && player.getNumJumps() > 0) //player 1
			{
				playerJump(player);
				blocks.remove(blocks.indexOf(pBlockTwo));
				return 20;
			}

			if(keyId == GLFW.GLFW_KEY_A) //player 1
			{
				moveLeft(player);
				blocks.remove(blocks.indexOf(pBlockTwo));
				return 1;
			}

			if(keyId == GLFW.GLFW_KEY_S) //player 1
			{
				slam(player);
				blocks.remove(blocks.indexOf(pBlockTwo));
				return 1;
			}

			if(keyId == GLFW.GLFW_KEY_D) //player 1
			{
				moveRight(player);
				blocks.remove(blocks.indexOf(pBlockTwo));
				return 1;
			}

			if(keyId == GLFW.GLFW_KEY_Q) //player 1 beam
			{
				blocks.remove(blocks.indexOf(pBlockTwo));
				Beam shot = new Beam(this, players.get(0), players.get(1));
				players.get(0).setSize(players.get(0).getSize() - 1);
				if(shot.trace()) {
					players.get(1).setSize(players.get(1).getSize() - 3);
				}
				players.get(0).setBeam(shot);
				return 10;
			}

			if(keyId == GLFW.GLFW_KEY_E) //player 1 explosion
			{
				blocks.remove(blocks.indexOf(pBlockTwo));
				Explosion explosion = new Explosion(this, players.get(0), players.get(1), 500d);
				players.get(0).setSize(players.get(0).getSize() - 10);
				if(explosion.trace())
				{
					players.get(1).setSize(players.get(1).getSize() - explosion.getTargetDamage());
				}
				players.get(0).setExplosion(explosion);
				return 60;
			}
			blocks.remove(blocks.indexOf(pBlockTwo));
			blocks.add(pBlock);

			player = players.get(1);
			if(keyId == GLFW.GLFW_KEY_UP && player.getNumJumps() > 0) //player 2
			{	
				playerJump(player);	
				blocks.remove(blocks.indexOf(pBlock));		
				return 20;
			}

			if(keyId == GLFW.GLFW_KEY_DOWN) //player 2
			{
				slam(player);
				blocks.remove(blocks.indexOf(pBlock));
				return 1;
			}

			if(keyId == GLFW.GLFW_KEY_LEFT) //player 2
			{
				moveLeft(player);
				blocks.remove(blocks.indexOf(pBlock));
				return 1;
			}

			if(keyId == GLFW.GLFW_KEY_RIGHT) //player 2
			{
				moveRight(player);
				blocks.remove(blocks.indexOf(pBlock));
				return 1;
			}
			if(keyId == GLFW.GLFW_KEY_SLASH) //player 2 beam
			{
				blocks.remove(blocks.indexOf(pBlock));
				Beam shot = new Beam(this, players.get(1), players.get(0));
				players.get(1).setSize(players.get(1).getSize() - 1);
				if(shot.trace()) {
					players.get(0).setSize(players.get(0).getSize() - 3);
				}
				players.get(1).setBeam(shot);
				return 10;
			}

			if(keyId == GLFW.GLFW_KEY_RIGHT_SHIFT) //player 2 explosion
			{
				blocks.remove(blocks.indexOf(pBlock));
				Explosion explosion = new Explosion(this, players.get(1), players.get(0), 500d);
				players.get(1).setSize(players.get(1).getSize() - 10);
				if(explosion.trace())
				{
					players.get(0).setSize(players.get(0).getSize() - explosion.getTargetDamage());
				}
				players.get(1).setExplosion(explosion);
				return 60;
			}	
			
			if(blocks.indexOf(pBlock) != -1)
				blocks.remove(blocks.indexOf(pBlock));
			if(blocks.indexOf(pBlockTwo) != -1)	
				blocks.remove(blocks.indexOf(pBlockTwo));

		}
		return -1;
	}

	private boolean checkCollision(Player player, double newPos, boolean x, Block check)
	{
		boolean collided = false;
		if(newPos > 0 && newPos + player.getSize() < height)
		{
			Vector4d newVecPos = new Vector4d(player.getPosVector());

			if(x)
				newVecPos.x = newPos;
			else
				newVecPos.y = newPos;

			if(CollisionHelper.colliding(newVecPos, check.getPosVector()))
				collided = true;	
		}
		return collided;
	}

	private void playerJump(Player player) 
	{
		player.useJump();
		player.setVelY(-50);
	}

	public int keyHeld(int keyId, int called, int mods)
	{
		return keyPressed(keyId,mods);
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

	public void moveLeft(Player curr)
	{
		boolean collided = false;
		curr.setVelX(curr.getVelX() - 5);
		double newPos = curr.getVelX() + curr.getPosX();
		if(newPos > 0 && newPos+curr.getSize() < width) {

			//block collisions
			for(int j = 0; j < blocks.size(); j++)
			{
				if(checkCollision(curr, newPos, true, blocks.get(j))){
					curr.setPosX(blocks.get(j).getPosX()+blocks.get(j).getWidth());
					curr.setVelX(0);
					collided = true;
					break;
				}
			}
			if(!collided)
				curr.setPosX(newPos);

		}
		else if(newPos <= 0)
		{
			curr.setPosX(0);
			curr.setVelX(0);
		}
		else
		{
			curr.setPosX(width-curr.getSize());
			curr.setVelX(0);
		}
	}

	public void moveRight(Player curr)
	{
		curr.setVelX(curr.getVelX()+5);
		double newPos = curr.getVelX() + curr.getPosX();
		if(newPos > 0 && newPos+curr.getSize() < width) {
			//block collision
			boolean collided = false;
			for(int j = 0; j < blocks.size(); j++)
			{
				if(checkCollision(curr, newPos, true, blocks.get(j))){
					curr.setPosX(blocks.get(j).getPosX()-curr.getSize());
					curr.setVelX(0);
					collided = true;
					break;
				}
			}
			if(!collided)
				curr.setPosX(newPos);

		}
		else if(newPos <= 0)
		{
			curr.setPosX(0);
			curr.setVelX(0);
		}
		else
		{
			curr.setPosX(width-curr.getSize());
			curr.setVelX(0);
		}
	}

	public void slam(Player curr)
	{
		curr.setVelY(curr.getVelY()+10);
		double newPos = curr.getVelY() + curr.getPosY();
		if(newPos > 0 && newPos+curr.getSize() < height) {

			boolean collided = false;
			//block collision
			int j;
			for(j = 0; j < blocks.size(); j++)
			{
				if(checkCollision(curr, newPos, false, blocks.get(j))){
					curr.setPosY(blocks.get(j).getPosY() - curr.getHeight());
					curr.setVelY(0);
					curr.resetJumps();
					collided = true;
					break;//
				}
			}
			if(collided && j == blocks.size()-1)
			{
				players.get(players.size()-players.indexOf(curr)-1).setPosY(5000);
				players.get(players.size()-players.indexOf(curr)-1).setSize(25);
			}
			if(!collided)
				curr.setPosY(newPos);	
		}
		else if(newPos <= 0)
		{
			curr.setPosY(0);
			curr.setVelY(0);
		}
		else
		{
			curr.setPosY(width-curr.getSize());
			curr.setVelY(0);
			curr.resetJumps();
		}
	}
}
