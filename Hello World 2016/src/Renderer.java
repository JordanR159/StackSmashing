import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3d;
import org.joml.Vector4d;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import com.polaris.engine.render.Draw;
import com.polaris.engine.render.OpenGL;
import com.polaris.engine.util.Color4d;

public class Renderer
{
	
	private Random graphicRandom;
	
	private Color4d background;
	private Color4d blocks;
	private Color4d[] players;
	FloatBuffer backgroundBuffer = BufferUtils.createFloatBuffer(3 * 4);
	int vao;
	IntBuffer vbo = BufferUtils.createIntBuffer(2);

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
		mainLightSource = new Vector3d(random.nextDouble() * 1904, random.nextDouble() * 952, .9);
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
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		GL15.glGenBuffers(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo.get(0));
		
		backgroundBuffer.put(new float[] {8, 64, -99});
		backgroundBuffer.put(new float[] {8, 1016, -99});
		backgroundBuffer.put(new float[] {1972, 1016, -99});
		backgroundBuffer.put(new float[] {1972, 64, -99});
		
		/*double light, redShift, greenShift, blueShift;
		
		light = (background.getRed() + background.getGreen() + background.getBlue() - .75d) / 1.5d;
		light = MathHelper.clamp(0, 1, light);
		light = .9;
		
		redShift = light - background.getRed();
		greenShift = light - background.getGreen();
		blueShift = light - background.getBlue();
		
		double multiplier;
		byte[] rgba = new byte[]{(byte) 255, (byte) 255, (byte) 255, (byte) 255};
		double value;
		int mulValue;
		for(int i = 0; i < 1904; i++)
		{
			for(int j = 0; j < 952; j++)
			{
				value = i - mainLightSource.x;
				mulValue = (int) (value * value);
				value = j - mainLightSource.y;
				mulValue += (int) (value * value);
				multiplier = (4531520 - mulValue) / 4531520d;
				
				rgba[0] = (byte) Math.round((multiplier * redShift + background.getRed()) * 255d);
				rgba[1] = (byte) Math.round((multiplier * greenShift + background.getGreen()) * 255d);
				rgba[2] = (byte) Math.round((multiplier * blueShift + background.getBlue()) * 255d);
				
				backgroundBuffer.put(rgba);
			}
		}*/
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, backgroundBuffer, GL15.GL_STATIC_DRAW);
	}

	public void drawBackground() 
	{
		OpenGL.glColor(background);
		OpenGL.glBegin();
		Draw.rect(0, 0, 1920, 1080, -100);
		GL11.glEnd();
		Draw.circle(mainLightSource.x, mainLightSource.y, -100, Math.sqrt(4531520), 200, new Color4d(.9, .9, .9, 1));
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
