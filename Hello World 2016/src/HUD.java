import com.polaris.engine.Application;
import com.polaris.engine.gui.GuiScreen;
import com.polaris.engine.render.FontMap;
import com.polaris.engine.render.Texture;

public class HUD extends GuiScreen
{
	
	private FontMap defaultFont;

	public HUD(Application app) 
	{
		super(app);
		defaultFont = Texture.getFontMap("basic");
	}
	
	public void render(double delta)
	{
		
	}
	
	public void update(double delta)
	{
		
	}

}
