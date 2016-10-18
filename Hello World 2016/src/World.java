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
	private long timeA = 0;
	private long timeD = 0;
	private long timeLeft = 0;
	private long timeRight = 0;

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
		//gravity for both players
		if(loser == -1)
		{
			Player player;
			Player otherPlayer;
			
			Block playerBlock;
			Block block;
			
			double newPosX, newPosY;
			boolean collided;
			
			for(int i = 0; i < players.size(); i++)
			{
				player = players.get(i);
				otherPlayer = players.get(players.size()-i-1);
				
				if(player.getSize() <= 25 && otherPlayer.getSize() <= 25) { //tiebreaker
					if(player.getSize() < otherPlayer.getSize()){
						loser = players.indexOf(player);
					} else if(player.getSize() > otherPlayer.getSize()) {
						loser = players.indexOf(otherPlayer);
					}
					else
						loser = i;
				} else if(player.getSize() <= 25)
				{
					loser = i;
					break;
				}
				
				
				player.update(delta);
				
				playerBlock = new Block(this, otherPlayer.getPosX(), otherPlayer.getPosY(), otherPlayer.getSize(), otherPlayer.getSize());
				blocks.add(playerBlock);
				
				player.setVelX(player.getVelX() / 1.1);
				player.setVelY(player.getVelY() + 3);
				
				newPosX = player.getVelX() + player.getPosX();
				
				if(newPosX > 0 && newPosX + player.getSize() <= width)
				{
					collided = false;
					for(int j = 0; j < blocks.size(); j++)
					{
						block = blocks.get(j);
						if(checkCollision(player, newPosX, true, block))
						{
							if(player.getVelX() > 0)
							{
								player.setPosX(block.getPosX() - player.getSize());
							}
							else if(player.getVelX() < 0)
							{
								player.setPosX(block.getPosX() + block.getWidth());
							}
							player.setVelX(0);
							
							collided = true;
							j = blocks.size();
						}
					}
					
					if(!collided)
					{
						player.setPosX(newPosX);
					}
				}
				else if(newPosX <= 0)
				{
					player.setPosX(0);
					player.setVelX(0);
				}
				else
				{
					player.setPosX(width - player.getSize());
					player.setVelX(0);
				}
				
				newPosY = player.getVelY() + player.getPosY();
				
				if(newPosY > 0 && newPosY + player.getSize() <= height)
				{
					collided = false;
					
					for(int j = 0; j < blocks.size(); j++)
					{
						block = blocks.get(j);
						if(checkCollision(player, newPosY, false, block))
						{
							if(player.getVelY() < 0)
							{
								player.nullifyJumps();
								player.setPosY(block.getPosY() + block.getHeight());
								player.setVelY(0);
							}
							else
							{
								player.resetJumps();
								player.setPosY(block.getPosY() - player.getSize());
								player.setVelY(0);
							}
							collided = true;
							j = blocks.size();
						}
					}
					
					if(!collided)
					{
						player.setPosY(newPosY);
					}
				}
				else if(newPosY <= 0)
				{
					player.setPosY(0);
					player.setVelY(0);
				}
				else
				{
					player.setPosY(height - player.getSize());
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
				createBeam(player, playerTwo);
				return 10;
			}

			if(keyId == GLFW.GLFW_KEY_E) //player 1 explosion
			{
				blocks.remove(blocks.indexOf(pBlockTwo));
				createExplosion(player, playerTwo);
				return 60;
			}
			blocks.remove(blocks.indexOf(pBlockTwo));
			blocks.add(pBlock);

			player = playerTwo;
			playerTwo = players.get(0);
			
			if((keyId == GLFW.GLFW_KEY_I || keyId == GLFW.GLFW_KEY_UP) && player.getNumJumps() > 0) //player 2
			{	
				playerJump(player);	
				blocks.remove(blocks.indexOf(pBlock));		
				return 20;
			}
			
			if(keyId == GLFW.GLFW_KEY_J || keyId == GLFW.GLFW_KEY_LEFT) //player 2
			{
				moveLeft(player);
				blocks.remove(blocks.indexOf(pBlock));
				return 1;
			}

			if(keyId == GLFW.GLFW_KEY_K || keyId == GLFW.GLFW_KEY_DOWN) //player 2
			{
				slam(player);
				blocks.remove(blocks.indexOf(pBlock));
				return 1;
			}

			if(keyId == GLFW.GLFW_KEY_L || keyId == GLFW.GLFW_KEY_RIGHT) //player 2
			{
				moveRight(player);
				blocks.remove(blocks.indexOf(pBlock));
				return 1;
			}
			if(keyId == GLFW.GLFW_KEY_U || keyId == GLFW.GLFW_KEY_SLASH) //player 2 beam
			{
				blocks.remove(blocks.indexOf(pBlock));
				createBeam(player, playerTwo);
				return 10;
			}
			if(keyId == GLFW.GLFW_KEY_O || keyId == GLFW.GLFW_KEY_PERIOD) //player 2 explosion

			{
				blocks.remove(blocks.indexOf(pBlock));
				createExplosion(player, playerTwo);
				return 60;
			}	
			
			if(blocks.indexOf(pBlock) != -1)
				blocks.remove(blocks.indexOf(pBlock));
			if(blocks.indexOf(pBlockTwo) != -1)	
				blocks.remove(blocks.indexOf(pBlockTwo));

		}
		return -1;
	}
	
	public void keyRelease(int keyId, int mods)
	{
		Player player = players.get(0);
		Player playerTwo = players.get(1);
		Block pBlock = new Block(this,player.getPosX(),player.getPosY(),player.getSize(),player.getSize());
		Block pBlockTwo = new Block(this,playerTwo.getPosX(),playerTwo.getPosY(),playerTwo.getSize(),playerTwo.getSize());
		blocks.add(pBlockTwo);
		
		if(keyId == GLFW.GLFW_KEY_A){
			if(System.currentTimeMillis() - timeA <= 200){ //dash
				dashLeft(players.get(0));
			}
			timeA = System.currentTimeMillis();
		}
		if(keyId == GLFW.GLFW_KEY_D){
			if(System.currentTimeMillis() - timeD <= 200){ //dash
				dashRight(players.get(0));//
			}
			timeD = System.currentTimeMillis();
		}
		
		blocks.remove(blocks.indexOf(pBlockTwo));
		blocks.add(pBlock);
		
		if(keyId == GLFW.GLFW_KEY_J || keyId == GLFW.GLFW_KEY_LEFT){
			if(System.currentTimeMillis() - timeLeft <= 200){ //dash
				dashLeft(players.get(1));
			}
			timeLeft = System.currentTimeMillis();
		}
		if(keyId == GLFW.GLFW_KEY_L || keyId == GLFW.GLFW_KEY_RIGHT){
			if(System.currentTimeMillis() - timeLeft <= 200){ //dash
				dashRight(players.get(1));
			}
			timeLeft = System.currentTimeMillis();
		}
		blocks.remove(blocks.indexOf(pBlock));
	}



	private boolean checkCollision(Player player, double newPos, boolean x, Block check)
	{
		boolean collided = false;
		if(newPos > 0 && newPos + player.getSize() < (x ? width : height))
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
		curr.setVelX(curr.getVelX() - 25);
	}

	public void moveRight(Player curr)
	{
		curr.setVelX(curr.getVelX() + 25);
	}
	
	public void dashLeft(Player curr)
	{
		curr.startDash();
		curr.setVelX(curr.getVelX() - 50);
		double newPos = curr.getVelX() + curr.getPosX();
		if(newPos > 0 && newPos+curr.getSize() < width) {

			boolean collided = false;
			//block collision
			int j;
			for(j = 0; j < blocks.size(); j++)
			{
				if(checkCollision(curr, newPos, true, blocks.get(j))){
					curr.setPosX(blocks.get(j).getPosX() + blocks.get(j).getWidth());
					curr.setVelX(0);
					collided = true;
					break;
				}
			}
			if(collided && j == blocks.size()-1){
				//damage other player 15 and knockbacks
				players.get(players.size()-players.indexOf(curr)-1).setSize(players.get(players.size()-players.indexOf(curr)-1).getSize() - 5);
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
	
	public void dashRight(Player curr)
	{
		curr.startDash();
		curr.setVelX(curr.getVelX() + 50);
		double newPos = curr.getVelX() + curr.getPosX();
		if(newPos > 0 && newPos+curr.getSize() < width) {

			boolean collided = false;
			//block collision
			int j;
			for(j = 0; j < blocks.size(); j++)
			{
				if(checkCollision(curr, newPos, true, blocks.get(j))){
					curr.setPosX(blocks.get(j).getPosX() - curr.getSize());
					curr.setVelX(0);
					collided = true;
					break;
				}
			}
			if(collided && j == blocks.size()-1){
				//damage other player 15 and knockback
				players.get(players.size()-players.indexOf(curr)-1).setSize(players.get(players.size()-players.indexOf(curr)-1).getSize() - 5);
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
		curr.startSlam();
		if(curr.getVelY() < 0)
			curr.setVelY(0);
		else
			curr.setVelY(curr.getVelY()+6);
		double newPos = curr.getVelY() + curr.getPosY();
		if(newPos > 0 && newPos+curr.getSize() < height) {

			boolean collided = false;
			//block collision
			int j;
			for(j = 0; j < blocks.size(); j++)
			{
				if(checkCollision(curr, newPos, false, blocks.get(j))){
					//if(blocks.get(j).getPosY() < curr.getPosY()){
						curr.setPosY(blocks.get(j).getPosY() - curr.getHeight());
						curr.setVelY(0);
						curr.resetJumps();
						collided = true;
						break;
					//}
				}
			}
			if(collided && j == blocks.size()-1){
				players.get(players.size()-players.indexOf(curr)-1).setSize(players.get(players.size()-players.indexOf(curr)-1).getSize() - .5);
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
			curr.setPosY(height-curr.getSize());
			curr.setVelY(0);
			curr.resetJumps();
		}
	}
	
	public void createBeam(Player player, Player playerTwo)
	{
		Beam shot = new Beam(this, player, playerTwo);
		player.setSize(player.getSize() - .25);
		if(shot.trace()) 
		{
			playerTwo.setSize(playerTwo.getSize() - 3);
		}
		player.setBeam(shot);
	}
	
	public void createExplosion(Player player, Player playerTwo)
	{
		Explosion explosion = new Explosion(this, player, playerTwo, 330d);
		player.setSize(player.getSize() - 8);
		if(explosion.trace())
		{
			playerTwo.setSize(playerTwo.getSize() - (explosion.getTargetDamage()*2)/3);
		}
		player.setExplosion(explosion);
	}
	
	public int getLoser()
	{
		return loser;
	}

}
