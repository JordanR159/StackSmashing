import java.util.Random;

import org.lwjgl.glfw.GLFW;
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
		
		world = new World(2, 15);
	}
	
	public void render(double delta)
	{
		Window.gl2d();
		OpenGL.glBlend();
		
		renderer.drawBackground();
		
		OpenGL.glBegin();
		
		for(int i = 0; i < world.getPlayers().size(); i++)
		{
			renderer.drawPlayer(world.getPlayers().get(i), i);
		}
		
		for(Block block : world.getBlocks())
			renderer.drawBlock(block);
		
		GL11.glEnd();
		
		renderer.render(delta);
		hud.render(delta);
	}
	
	public void update(double delta)
	{
		hud.update(delta);
		world.update(delta);
	}
	
	public int keyPressed(int keyId, int mods)
	{
		if(keyId >= 'A' && keyId <= 'Z')
			keyId -= 32;
		if(keyId == GLFW.GLFW_KEY_ESCAPE)
		{
			return super.keyPressed(keyId, mods);
		}
		return world.keyPressed(keyId, mods);
	}
	
	public int keyHeld(int keyId, int called, int mods)
	{
		if(keyId >= 'A' && keyId <= 'Z')
			keyId -= 32;
		return world.keyHeld(keyId, called, mods);
	}

}
