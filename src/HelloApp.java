import com.polaris.engine.Application;
import com.polaris.engine.render.Window;

public class HelloApp extends Application
{

    private GameSettings settings;

    public static void main(String[] args)
    {
        HelloApp app = new HelloApp();
        app.run();
    }

    @Override
    protected void init() 
    {
        settings = new GameSettings();
        setGui(new GuiMainMenu(this));
    }

    @Override
    public long createWindow() 
    {
        return Window.createAndCenter(1080, 640, "STACK SMASHING", 0);
    }

    public GameSettings getSettings()
    {
        return settings;
    }

}
