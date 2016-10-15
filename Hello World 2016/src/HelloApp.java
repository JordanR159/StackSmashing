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
		setGui(new GuiGame(this));
	}

	@Override
	public long createWindow() 
	{
		return Window.createAndCenter(1280, 720, "Hello World 2016", 0);
	}

}
