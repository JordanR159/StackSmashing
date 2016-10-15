import java.text.DecimalFormat;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.polaris.engine.Application;
import com.polaris.engine.gui.GuiScreen;
import com.polaris.engine.render.Draw;
import com.polaris.engine.render.FontMap;
import com.polaris.engine.render.OpenGL;
import com.polaris.engine.render.Texture;
import com.polaris.engine.util.Color4d;

public class HUD extends GuiScreen
{
	
	private Random graphicRandom;
	private DecimalFormat sizeFormat;
	private Renderer renderer;
	
	private FontMap defaultFont;

	public HUD(Application app, Random random, Renderer render) 
	{
		super(app);
		graphicRandom = random;
		sizeFormat = new DecimalFormat("#.#######");
		renderer = render;
		defaultFont = Texture.getFontMap("basic");
	}
	
	public void render(double delta)
	{
		double size = defaultFont.getTextWidth(" : ", 48);
		OpenGL.glColor(1d, 1d, 1d, 1d);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Texture.glBindTexture("font:basic");
		defaultFont.drawAlignedString(" : ", 1920 / 2, 1024, 20, 48, 1);
		OpenGL.glColor(renderer.getPlayer(0));
		defaultFont.drawAlignedString(sizeFormat.format(100), (1920 - size) / 2, 1024, 20, 48, 2);
		OpenGL.glColor(renderer.getPlayer(1));
		defaultFont.drawString(sizeFormat.format(100), (1920 + size) / 2, 1024, 20, 48);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		OpenGL.glBegin();
		Color4d color = renderer.getPlayer(0);
		OpenGL.glColor(color.getRed(), color.getGreen(), color.getBlue(), .5);
		Draw.rect(8, 1020, 1920 / 2, 1074, 19);
		color = renderer.getPlayer(1);
		OpenGL.glColor(color.getRed(), color.getGreen(), color.getBlue(), .5);
		Draw.rect(1920 / 2, 1020, 1912, 1074, 19);
		GL11.glEnd();
	}
	
	public void update(double delta)
	{
		
	}

}
