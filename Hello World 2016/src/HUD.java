import java.text.DecimalFormat;

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
	
	private World world;
	private DecimalFormat sizeFormat;
	private Renderer renderer;
	
	private FontMap defaultFont;

	public HUD(Application app, World w, Renderer render) 
	{
		super(app);
		world = w;
		sizeFormat = new DecimalFormat("#.##");
		renderer = render;
		defaultFont = Texture.getFontMap("basic");
	}
	
	public void render(double delta)
	{
		OpenGL.glColor(0d, 0d, 0d, 1);
		OpenGL.glBegin();
		Draw.rect(0, 60, 1920, 1020, -100, 8);
		Draw.rect(0, 0, 1920, 64, 1);
		Draw.rect(0, 1016, 1920, 1080, 1);
		GL11.glEnd();
		
		double player1 = world.getPlayers().get(0).getSize() - 25;
		double player2 = world.getPlayers().get(1).getSize() - 25;
		
		double breakerX = (defaultFont.getTextWidth(" :   ", 64)) / 2 - 12.5;
		double rightX = defaultFont.getTextWidth(sizeFormat.format(Math.max(player1 / .75, 0)), 64);
		
		OpenGL.glColor(1d, 1d, 1d, 1d);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		Texture.glBindTexture("font:basic");
		
		defaultFont.drawString("" + (int)Math.round(1 / delta / 10) * 10, 10, 10, 10, 64);
		defaultFont.drawUpString(" :  ", 1920 / 2 - breakerX, 1067, 20, 48);
		
		OpenGL.glColor(renderer.getPlayer(0));
		defaultFont.drawUpString(sizeFormat.format(Math.max(player1 / .75, 0)), 1920 / 2 - breakerX - rightX, 1071, 20, 64);
		
		OpenGL.glColor(renderer.getPlayer(1));
		defaultFont.drawUpString(sizeFormat.format(Math.max(player2 / .75, 0)), (1920 + breakerX) / 2, 1071, 20, 64);
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		OpenGL.glBegin();
		
		Color4d color = renderer.getPlayer(0);
		
		OpenGL.glColor(color.getRed(), color.getGreen(), color.getBlue(), .5);
		Draw.rect(8, 1020, 8 + (1920 / 2 - 8) * player1 / 75d, 1074, 19);
		
		color = renderer.getPlayer(1);
		
		OpenGL.glColor(color.getRed(), color.getGreen(), color.getBlue(), .5);
		Draw.rect(1920 - (1920 / 2 - 8) * player2 / 75d, 1020, 1912, 1074, 19);
		
		GL11.glEnd();
	}

}
