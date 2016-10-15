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
		double breakerX = (defaultFont.getTextWidth(" :  ", 64)) / 2 - 12.5;
		double rightX = defaultFont.getTextWidth(sizeFormat.format(100), 64);
		OpenGL.glColor(1d, 1d, 1d, 1d);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Texture.glBindTexture("font:basic");
		defaultFont.drawUpString(" :  ", 1920 / 2 - breakerX, 1071, 20, 48);
		OpenGL.glColor(renderer.getPlayer(0));
		defaultFont.drawUpString(sizeFormat.format(100), 1920 / 2 - breakerX - rightX, 1071, 20, 64);
		OpenGL.glColor(renderer.getPlayer(1));
		defaultFont.drawUpString(sizeFormat.format(100), (1920 + breakerX) / 2, 1071, 20, 64);
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
