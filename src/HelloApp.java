import com.polaris.engine.Application;
import com.polaris.engine.render.Window;

public class HelloApp extends Application
{
	
	public static void main(String[] args)
	{
		HelloApp app = new HelloApp();
		app.run();
	}

	@Override
	protected void init() 
	{
		setGui(new GuiMainMenu(this));
	}

	@Override
	public long createWindow() 
	{
		return Window.createAndCenter(1080, 640, "STACK SMASHING", 0);
	}

}
