import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3d;
import org.joml.Vector4d;
import org.lwjgl.opengl.GL11;

import com.polaris.engine.render.Draw;
import com.polaris.engine.render.OpenGL;
import com.polaris.engine.util.Color4d;
import com.polaris.engine.util.MathHelper;

public class Renderer
{
	
	private Random graphicRandom;
	
	private Color4d background;
	private int backgroundId;
	private Color4d blocks;
	private Color4d[] players;
	
	protected Vector3d mainLightSource;
	private List<Vector4d> shadeList;
	
	public Renderer(Random random, int playerCount)
	{
		graphicRandom = random;
		players = new Color4d[playerCount];
		
		for(int i = 0; i < players.length; i++)
		{
			players[i] = genColor(i);
		}
		background = genColor(players.length);
		//backgroundPixels = new int[1904 * 952];
		blocks = genColor(players.length);
		mainLightSource = new Vector3d(random.nextDouble() * 1904, random.nextDouble() * 952, random.nextInt(4531520 / 4) + 4531520 / 4);
		shadeList = new ArrayList<Vector4d>();
		genBackground();
	}
	
	private Color4d genColor(int iteration)
	{
		boolean foundColor = false;
		int red = 0, green = 0, blue = 0, maxDiff = 0;
		
		while(!foundColor)
		{
			foundColor = true;
			red = graphicRandom.nextInt(256);
			green = graphicRandom.nextInt(256);
			blue = graphicRandom.nextInt(256);
			if(iteration != players.length)
			{
				maxDiff = Math.max(Math.abs(red - green), Math.abs(red - blue));
				maxDiff = Math.max(maxDiff, Math.abs(green - blue));
				if(maxDiff < 128)
				{
					foundColor = false;
				}
			}
			else if(background != null)
			{
				maxDiff = (int) Math.abs(background.getRed() * 255 - red);
				maxDiff += (int) Math.abs(background.getGreen() * 255 - green);
				maxDiff += (int) Math.abs(background.getBlue() * 255 - blue);
				if(maxDiff < 275 - players.length * 15)
				{
					foundColor = false;
				}
			}
			else
			{
				maxDiff = Math.max(Math.abs(red - green), Math.abs(red - blue));
				maxDiff = Math.max(maxDiff, Math.abs(green - blue));
				if(maxDiff < 30)
				{
					foundColor = false;
				}
			}
			if(foundColor)
			{
				List<Integer> differences = new ArrayList<Integer>();
				for(int j = 0; j < iteration; j++)
				{
					Color4d playerColor = players[j];
					maxDiff = (int) Math.abs(playerColor.getRed() * 255 - red);
					maxDiff += (int) Math.abs(playerColor.getGreen() * 255 - green);
					maxDiff += (int) Math.abs(playerColor.getBlue() * 255 - blue);
					differences.add(new Integer(maxDiff));
					if(maxDiff < 275 - players.length * 15)
					{
						differences.clear();
						foundColor = false;
						j = iteration;
					}
				}
			}
		}
		return new Color4d(red, green, blue, 255);
	}
	
	private void genBackground()
	{
		backgroundId = GL11.glGenLists(1);

		// compile the display list, store a triangle in it
		GL11.glNewList(backgroundId, GL11.GL_COMPILE);
		double light, redShift, greenShift, blueShift;
		
		light = (background.getRed() + background.getGreen() + background.getBlue() - .75d) / 1.5d;
		light = MathHelper.clamp(0, 1, light);
		light = .9;
		
		redShift = light - background.getRed();
		greenShift = light - background.getGreen();
		blueShift = light - background.getBlue();
		
		double multiplier;
		int red, green, blue;
		double value;
		for(int i = 0; i < 1904; i++)
		{
			for(int j = 0; j < 952; j++)
			{
				value = i - mainLightSource.x;
				multiplier = value * value;
				value = j - mainLightSource.y;
				multiplier += value * value;
				multiplier = (4531520d - multiplier) / 4531520d;
				
				red = (int) Math.round((multiplier * redShift + background.getRed()) * 255d);
				green = (int) Math.round((multiplier * greenShift + background.getGreen()) * 255d);
				blue = (int) Math.round((multiplier * blueShift + background.getBlue()) * 255d);
				
				OpenGL.glColor(new Color4d(red, green, blue, 255));
				Draw.rect(8 + i, 64 + j, 9 + i, 65 + j, -100);
			}
		}
		
		GL11.glEndList();
	}

	public void drawBackground() 
	{
		GL11.glCallList(backgroundId);
		/*OpenGL.glColor(background);
		Draw.rect(0, 0, 1920, 1080, -100);
		double shifter = (background.getRed() + background.getGreen() + background.getBlue() + .5d) / 2.25d;
		shifter = Math.min(shifter, 1d);
		
		OpenGL.glColor(shifter, shifter, shifter, 1);
		Draw.colorVRect(0, 64, 1920, 1080, -100, background);*/
		
		OpenGL.glColor(0d, 0d, 0d, 1);
		Draw.rect(0, 60, 1920, 1020, -100, 8);
		Draw.rect(0, 0, 1920, 64, 1);
		Draw.rect(0, 1016, 1920, 1080, 1);
	}

	public void drawPlayer(Player player, int i) 
	{
		double x = player.getPosX() + 64;
		double y = player.getPosY() + 64;
		double size = player.getSize();
		OpenGL.glColor(0, 0, 0, 1d);
		Draw.rect(x, y, x + size, y + size, 2);
		OpenGL.glColor(players[i]);
		Draw.rect(x + 2, y + 2, x + size - 2, y + size - 2, 2);
	}

	public void drawBlock(Block block) 
	{
		double x = block.getPosX() + 64;
		double y = block.getPosY() + 64;
		double width = block.getWidth();
		double height = block.getHeight();
		OpenGL.glColor(0, 0, 0, 1d);
		Draw.rect(x, y, x + width, y + height, 1);
		OpenGL.glColor(blocks);
		Draw.rect(x + 2, y + 2, x + width - 2, y + height - 2, 1);
	}
	
	public void render()
	{
		
	}
	
	public void drawWithShade(double x, double y, double x1, double y1, double z)
	{
		Draw.rect(x, y, x1, y1, z);
		shadeList.add(new Vector4d(x, y, (x1 - x), y1 - y));
	}
	
	public void clearShades()
	{
		shadeList.clear();
	}
	
	public Color4d getPlayer(int color)
	{
		return players[color];
	}
}
