import org.lwjgl.opengl.GL11;

import com.polaris.engine.App;
import com.polaris.engine.Application;
import com.polaris.engine.gui.GuiScreen;
import com.polaris.engine.render.FontMap;
import com.polaris.engine.render.OpenGL;
import com.polaris.engine.render.Texture;
import com.polaris.engine.render.Window;
import com.polaris.engine.util.Color4d;
import com.polaris.engine.util.MathHelper;

public class GuiMainMenu extends GuiScreen
{
	
	private double ticksExisted = 0;
	private char[] sequence = "STACK SMASHING".toCharArray();
	private double[] ticks = new double[sequence.length];
	private double[] toTicks = new double[sequence.length];
	private FontMap fontMap;

	public GuiMainMenu(Application app) 
	{
		super(app);
		fontMap = Texture.getFontMap("basic");
	}
	
	public void render(double delta)
	{
		Window.gl2d();
		OpenGL.glBlend();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		OpenGL.glColor(1, 0, 0, 1);
		Texture.glBindTexture("font:basic");
		
		double x = 1920 / 2 - fontMap.getTextWidth(new String(sequence), 128) / 2;
		int orange = 0;
		
		for(int i = 0; i < sequence.length; i++)
		{
			fontMap.drawColorString("" + sequence[i], x, 200 + ticks[i], 0, 128, new Color4d(255, orange += 200 / sequence.length, 0, (int) Math.min(ticksExisted * 60, 255)));
			x += fontMap.getTextWidth("" + sequence[i], 128);
		}
		
		if(ticksExisted * 60 > 265)
		{
			if(App.getMouseX() >= 1920 / 2 - fontMap.getTextWidth("START A GAME", 48) && App.getMouseX() <= 1920 / 2 + fontMap.getTextWidth("START A GAME", 48))
			{
				if(App.getMouseY() >= 600 - 32 && App.getMouseY() <= 600 + 32)
				{
					OpenGL.glColor(1, 1, 1, 1);
				}
			}
			fontMap.drawAlignedString("START A GAME", 1920 / 2, 600, 0, 48, 1);
		}
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		if(ticksExisted * 60 > 255)
		{
			for(int i = 0; i < ticks.length; i++)
			{
				if(MathHelper.isEqual(ticks[i], toTicks[i]))
				{
					toTicks[i] = 200 + Math.random() * 20 - 10;
				}
				else
				{
					ticks[i] = MathHelper.getExpValue(ticks[i], toTicks[i], 1, delta);
				}
			}
		}
	}
	
	public void update(double delta)
	{
		ticksExisted += delta;
	}
	
	public boolean mouseClick(int mouseId)
	{
		if(ticksExisted * 60 < 255)
			ticksExisted = 255 / 60d;
		if(App.getMouseX() >= 1920 / 2 - fontMap.getTextWidth("START A GAME", 48) && App.getMouseX() <= 1920 / 2 + fontMap.getTextWidth("START A GAME", 48))
		{
			if(App.getMouseY() >= 600 - 32 && App.getMouseY() <= 600 + 32)
			{
				application.setGui(new GuiGame(application));
			}
		}
		return true;
	}

}
