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
		world = new World(2, 15);
		
		hud = new HUD(app, world, renderer);
		
	}
	
	public void render(double delta)
	{
		Window.gl2d();
		OpenGL.glBlend();
		
		renderer.drawBackground();
		
		
		for(int i = 0; i < world.getPlayers().size(); i++)
		{
			renderer.drawPlayer(world.getPlayers().get(i), i);
		}
		
		OpenGL.glBegin();
		
		for(Block block : world.getBlocks())
			renderer.drawBlock(block);
		
		GL11.glEnd();
		
		renderer.render(delta);
		hud.render(delta);
	}
	
	public void update(double delta)
	{
		world.update(delta);
		if(world.getLoser() > -1)
		{
			application.setGui(new GuiEndGame(application, world.getLoser(), renderer.getPlayer((world.getLoser() + 1) % 2)));
		}
	}
	
	public int keyPressed(int keyId, int mods)
	{
		if(keyId >= 'a' && keyId <= 'z')
			keyId -= ('a' - 'A');
		if(keyId == GLFW.GLFW_KEY_ESCAPE)
		{
			return super.keyPressed(keyId, mods);
		}
		return world.keyPressed(keyId, mods);
	}
	
	public int keyHeld(int keyId, int called, int mods)
	{
		return keyPressed(keyId, mods);
	}
	
	public void keyRelease(int keyId, int mods)
	{
		if(keyId >= 'a' && keyId <= 'z')
			keyId -= ('a' - 'A');
		world.keyRelease(keyId, mods);
	}

}
