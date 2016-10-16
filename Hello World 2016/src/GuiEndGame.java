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
	
	private String[] words = new String[] {"And ", "the ", "winner ", "is..."};
	FontMap font;
	double ticks = 0;
	double toTicks = 400;
	int current = 1;

	public GuiEndGame(Application app, int loser, Color4d color)
	{
		super(app);
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
		OpenGL.glBegin();
		for(int i = 0; i < current - 1; i++)
		{
			font.drawString(words[i], x, 400, 0, 96);
			x += font.getTextWidth(words[i], 96);
		}
		font.drawString(words[current - 1], x, ticks, 0, 96);
		GL11.glEnd();
	}
	
	public void update(double delta)//
	{
		ticks = MathHelper.getExpValue(ticks, toTicks, 2, delta);
		if(MathHelper.isEqual(ticks, toTicks) && current != words.length)
		{
			ticks = 0;
			current ++;
		}
	}

}
