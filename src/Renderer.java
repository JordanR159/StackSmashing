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

	private List[] slamArray;

	protected Vector3d mainLightSource;
	private List<Vector4d> shadeList;

	public Renderer(Random random, int playerCount)
	{
		graphicRandom = random;
		players = new Color4d[playerCount];

		slamArray = new ArrayList[2];
		slamArray[0] = new ArrayList<Double[]>();
		slamArray[1] = new ArrayList<Double[]>();

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
		for(int i = (int) Math.min(Math.pow(ticksExisted * 3 / 2, 2.5), 20000); i >= 0; i--)
		{
			int x = graphicRandom.nextInt(1900) + 10;
			int y = graphicRandom.nextInt(944) + 68;
			OpenGL.glVertex(x, y, -99, 0, 0, 0, graphicRandom.nextDouble() * .4d + .1d);
		}
		GL11.glEnd();
	}

	@SuppressWarnings("unchecked")
	public void drawPlayer(Player player, int playerId) 
	{
		double x = player.getPosX() + 8;
		double y = player.getPosY() + 64;
		double size = player.getSize();

		OpenGL.glBegin();

		OpenGL.glColor(players[playerId]);
		drawWithShade(x, y, x + size, y + size);

		GL11.glEnd();

		Beam beam = player.getBeam();
		if(beam != null)
		{
			double startX = beam.getPosVector().x + 8;
			double baseY = beam.getPosVector().y + 64;
			double endX = beam.getPosVector().z + startX;
			double ticks = player.getBeamTicks();

			int total = (int) Math.abs(endX - startX) / 20 + 1;
			for(total = (int) Math.min(total - 1, total * (1 - 4 * ticks)); total >= 0; total --)
			{
				if(!beam.isRight())
				{
					endX -= MathHelper.clamp(-20, 20, endX - startX);
				}
				else
				{
					startX += MathHelper.clamp(-20, 20, endX - startX);
				}
			}

			OpenGL.glColor(0, 0, 0, 1);

			GL11.glLineWidth(2);
			GL11.glBegin(GL11.GL_LINES);
			while(!MathHelper.isEqual(Math.abs(endX - startX), 0))
			{
				startX = startX + MathHelper.clamp(-20, 20, endX - startX);
				GL11.glVertex3d(startX, baseY + graphicRandom.nextDouble() * 20 - 10, 6);
			}
			GL11.glEnd();
		}
		if(player.getExplosion() != null)
		{
			Explosion explosion = player.getExplosion();
			double ticks = player.getExplosionTicks();
			OpenGL.glColor(0, 0, 0, 1);
			GL11.glBegin(GL11.GL_LINES);
			double expX = player.getPosX() + player.getSize() / 2 + 8;
			double expY = player.getPosY() + player.getSize() / 2 + 64;
			double mul1 = Math.max(.8 - 10 * ticks, 0);
			double mul2 = 1 - 10 * ticks;
			for(int i = 0; i < 360; i += 10)
			{
				double rad = Math.toRadians(i);
				double cos = Math.cos(rad) * explosion.getRadii(i);
				double sin = Math.sin(rad) * explosion.getRadii(i);

				GL11.glVertex3d(expX + cos * mul1, expY + sin * mul1, 30);
				GL11.glVertex3d(expX + cos * mul2, expY + sin * mul2, 30);
			}
			GL11.glEnd();
		}
		if(player.checkDash())
		{
			OpenGL.glColor(0, 0, 0, 1);
			GL11.glBegin(GL11.GL_LINES);
			for(int i = 0; i < 3; i++)
			{
				GL11.glVertex3d(x, y + player.getSize() / 5 * (i + 1), -2);
				GL11.glVertex3d(x + player.getSize() / 2 - player.getVelX() * 4d, y - player.getVelY() * 2 + player.getSize() / 5 * (i + 1), -2);
			}
			GL11.glEnd();
		}
		if(player.checkSlam())
		{
			for(int i = 0; i < 3; i++)
			{
				slamArray[playerId].add(new double[] {x + player.getSize() / 5 * (i + 1), y, -2});
				slamArray[playerId].add(new double[] {x + player.getVelX() + player.getSize() / 5 * (i + 1), y + player.getVelY(), -2});
			}
			
			OpenGL.glColor(0, 0, 0, 1);
			GL11.glBegin(GL11.GL_LINES);
			for(int i = 0; i < slamArray[playerId].size(); i += 2)
			{
				double[] first = (double[]) slamArray[playerId].get(i);
				double[] second = (double[]) slamArray[playerId].get(i + 1);
				GL11.glVertex3d(first[0], first[1], first[2]);
				GL11.glVertex3d(second[0], second[1], second[2]);
			}
			GL11.glEnd();
		}
		else if(slamArray[playerId].size() > 0)
		{
			slamArray[playerId].clear();
			
		}
	}

	public void drawBlock(Block block) 
	{
		double x = block.getPosX() + 8;
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
