import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.polaris.engine.Application;
import com.polaris.engine.gui.GuiScreen;
import com.polaris.engine.render.OpenGL;
import com.polaris.engine.render.Window;

public class GuiGame extends GuiScreen
{
	private static Random graphicRandom = new Random();
	
	private HUD hud;
	
	private Renderer renderer;

	public GuiGame(Application app) 
	{
		super(app);
		renderer = new Renderer(graphicRandom, 2);
		hud = new HUD(app, graphicRandom, renderer);
	}
	
	public void render(double delta)
	{
		Window.gl2d();
		OpenGL.glBlend();
		hud.render(delta);
		OpenGL.glBegin();
		renderer.drawBackground();
		//renderer.drawPlayer(null);
		//renderer.drawBlock(null);
		GL11.glEnd();
		renderer.clearShades();
	}
	
	public void update(double delta)
	{
		hud.update(delta);
	}

}
