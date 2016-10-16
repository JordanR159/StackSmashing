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
	private Color4d blocks;
	private Color4d[] players;
	private double ticksExisted = 0;

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
		mainLightSource = new Vector3d(random.nextDouble() * 1904, random.nextDouble() * 952, .9);
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
		OpenGL.glBegin();
		Draw.rect(0, 0, 1920, 1080, -100);
		GL11.glEnd();
		Draw.circle(mainLightSource.x, mainLightSource.y, -100, Math.sqrt(4531520), 200, new Color4d(.9, .9, .9, 1));
		
		GL11.glBegin(GL11.GL_POINTS);
		for(int i = (int) Math.min(Math.pow(ticksExisted, 2.5), 40000); i >= 0; i--)
		{
			int x = graphicRandom.nextInt(1900) + 10;
			int y = graphicRandom.nextInt(944) + 68;
			OpenGL.glVertex(x, y, 30, 0, 0, 0, graphicRandom.nextDouble() * .4d + .1d);
		}
		GL11.glEnd();
	}

	public void drawPlayer(Player player, int playerId) 
	{
		double x = player.getPosX() + 64;
		double y = player.getPosY() + 64;
		double size = player.getSize();
		
		OpenGL.glBegin();
		
		OpenGL.glColor(players[playerId]);
		drawWithShade(x, y, x + size, y + size);
		
		GL11.glEnd();
		
		Beam beam = player.getBeam();
		if(beam != null)
		{
			double startX = beam.getPosVector().x;
			double baseY = beam.getPosVector().y;
			double endX = beam.getPosVector().z; 
			
			double startY = 0;
			double nextX = 0;
			double nextY = 0;
			
			OpenGL.glColor(0, 0, 0, 1);
			GL11.glLineWidth(1);
			for(int i = 0; i < 5; i++)
			{
				GL11.glBegin(GL11.GL_LINES);
				startY = baseY + graphicRandom.nextDouble() * 20 - 10;
				GL11.glVertex3d(startX, startY, 6);
				while(!MathHelper.isEqual(Math.abs(endX - startX), 0))
				{
					nextX = MathHelper.clamp(-20, 20, endX - startX);
					nextY = baseY + graphicRandom.nextDouble() * 20 - 10;
					GL11.glVertex3d(nextX, nextY, 6);
					startX += nextX;
					startY = nextY;
				}
				GL11.glEnd();
			}
		}
	}

	public void drawBlock(Block block) 
	{
		double x = block.getPosX() + 64;
		double y = block.getPosY() + 64;
		double width = block.getWidth();
		double height = block.getHeight();
		
		OpenGL.glColor(blocks);
		drawWithShade(x, y, x + width, y + height);
	}
	
	public void drawWithShade(double x, double y, double x1, double y1)
	{
		Draw.rect(x, y, x1, y1, 3);
		Color4d inner = new Color4d(OpenGL.getColor());
		OpenGL.glColor(0d, 0d, 0d, 1d);
		Draw.rect(x, y, x1, y1, 4, 5, inner);
		shadeList.add(new Vector4d(x, y, x1, y1));
	}

	public void render(double delta)
	{
		ticksExisted += delta;
		shadeList.clear();
	}

	public Color4d getPlayer(int color)
	{
		return players[color];
	}
}
