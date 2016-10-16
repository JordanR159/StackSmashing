import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.polaris.engine.Application;
import com.polaris.engine.gui.GuiScreen;
import com.polaris.engine.render.FontMap;
import com.polaris.engine.render.OpenGL;
import com.polaris.engine.render.Texture;
import com.polaris.engine.render.Window;
import com.polaris.engine.util.Color4d;
import com.polaris.engine.util.MathHelper;

public class GuiEndGame extends GuiScreen
{
	
	private String[] words = new String[] {"AND ", "THE ", "WINNER ", "IS ..." };
	private double ticks = 0;
	private double toTicks = 400;
	private int current = 1;
	private int loser = 1;
	private Color4d color;
	
	private FontMap font;

	public GuiEndGame(Application app, int l, Color4d player)
	{
		super(app);
		loser = l;
		color = player;
		font = Texture.getFontMap("basic");
	}

	public void render(double delta)
	{
		Window.gl2d();
		OpenGL.glBlend();
		double x = 64;
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Texture.glBindTexture("font:basic");
		OpenGL.glColor(1, .6, 0, 1);
		for(int i = 0; i < current - 1; i++)
		{
			font.drawString(words[i], x, 400, 0, 96);
			x += font.getTextWidth(words[i], 96);
		}
		font.drawString(words[current - 1], x, ticks, 0, 96);
	}
	
	public void update(double delta)//
	{
		ticks = MathHelper.getExpValue(ticks, toTicks, .1, delta);
		ticks = MathHelper.getLinearValue(ticks, toTicks, 10, delta);
		if(MathHelper.isEqual(ticks, toTicks) && current != words.length)
		{
			ticks = 0;
			current ++;
		}
	}
	
	public int keyPressed(int keyId, int mods)
	{
		if(keyId == GLFW.GLFW_KEY_ENTER)
		{
			application.setGui(new GuiGame(application));
		}
		return -1;
	}

}
