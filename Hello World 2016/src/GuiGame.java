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
	private World world;

	public GuiGame(Application app) 
	{
		super(app);
		
		renderer = new Renderer(graphicRandom, 2);
		
		hud = new HUD(app, graphicRandom, renderer);
		
		world = new World(2, 10);
	}
	
	public void render(double delta)
	{
		Window.gl2d();
		OpenGL.glBlend();
		
		OpenGL.glBegin();
		renderer.drawBackground();
		
		for(int i = 0; i < world.getPlayers().size(); i++)
		{
			renderer.drawPlayer(world.getPlayers().get(i), i);
		}
		
		for(Block block : world.getBlocks())
			renderer.drawBlock(block);
		
		GL11.glEnd();
		
		renderer.render();
		hud.render(delta);
	}
	
	public void update(double delta)
	{
		hud.update(delta);
		world.update(delta);
	}
	
	public int keyPressed(int keyId, int mods)
	{
		return world.keyPressed(keyId, mods);
	}

}
