import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3d;
import org.joml.Vector4d;

import com.polaris.engine.render.Draw;
import com.polaris.engine.render.OpenGL;
import com.polaris.engine.util.Color4d;

public class Renderer
{
	
	private Random graphicRandom;
	
	private Color4d background;
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
		blocks = genColor(players.length);
		mainLightSource = new Vector3d(random.nextDouble() * 1920, random.nextDouble() * 1080, random.nextDouble() * 2202.90717008);
		shadeList = new ArrayList<Vector4d>();
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

	public void drawBackground() 
	{
		OpenGL.glColor(background);
		Draw.rect(0, 0, 1920, 1080, -100);
		double shifter = (background.getRed() + background.getGreen() + background.getBlue() + .5d) / 2.25d;
		shifter = Math.min(shifter, 1d);
		
		OpenGL.glColor(shifter, shifter, shifter, 1);
		Draw.colorVRect(0, 64, 1920, 1080, -100, background);
		
		OpenGL.glColor(0d, 0d, 0d, 1);
		Draw.rect(0, 60, 1920, 1020, -100, 8);
		Draw.rect(0, 0, 1920, 64, 1);
		Draw.rect(0, 1016, 1920, 1080, 1);
	}

	public void drawPlayer(Player player) 
	{
		OpenGL.glColor(0, 0, 0, 1);
		for(int i = 0; i < players.length; i++)
		{
			Draw.rect(i * 200 - 2, i * 100 - 2, i * 200 + 102, i * 100 + 102, 2);
		}
		for(int i = 0; i < players.length; i++)
		{
			OpenGL.glColor(players[i]);
			drawWithShade(i * 200, i * 100, i * 200 + 100, i * 100 + 100, 2);
		}
	}

	public void drawBlock(Block block) 
	{
		OpenGL.glColor(0, 0, 0, 1);
		Draw.rect(300 - 4, 300 - 4, 400 + 4, 500 + 4, 1);
		Draw.rect(600 - 4, 100 - 4, 800 + 4, 200 + 4, 1);
		OpenGL.glColor(blocks);
		drawWithShade(300, 300, 400, 500, 1);
		drawWithShade(600, 100, 800, 200, 1);
	}
	
	public void drawSquare(Square square)
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
