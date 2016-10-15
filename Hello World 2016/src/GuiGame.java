import com.polaris.engine.Application;
import com.polaris.engine.gui.GuiScreen;

public class GuiGame extends GuiScreen
{
	
	private HUD hud;

	public GuiGame(Application app) 
	{
		super(app);
		hud = new HUD(app);
	}
	
	public void render(double delta)
	{
		hud.render(delta);
	}
	
	public void update(double delta)
	{
		hud.update(delta);
	}

}
