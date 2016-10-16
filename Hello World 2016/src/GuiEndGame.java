import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.polaris.engine.Application;
import com.polaris.engine.gui.GuiScreen;
import com.polaris.engine.render.Draw;
import com.polaris.engine.render.FontMap;
import com.polaris.engine.render.OpenGL;
import com.polaris.engine.render.Texture;
import com.polaris.engine.render.Window;
import com.polaris.engine.util.Color4d;
import com.polaris.engine.util.MathHelper;

public class GuiEndGame extends GuiScreen
{
	
	private String[] words = new String[] {"AND ", "THE ", "WINNER ", "IS ..." };
	private double ticks = 0;
	private double toTicks = 400;
	private int current = 1;
	private Color4d color;
	
	private FontMap font;

	public GuiEndGame(Application app, int l, Color4d player)
	{
		super(app);
		color = player;
		font = Texture.getFontMap("basic");
	}

	public void render(double delta)
	{
		Window.gl2d();
		OpenGL.glBlend();
		double x = 64;
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Texture.glBindTexture("font:basic");
		OpenGL.glColor(1, .6, 0, 1);
		for(int i = 0; i < current - 1; i++)
		{
			font.drawString(words[i], x, 400, 0, 96);
			x += font.getTextWidth(words[i], 96);
		}
		font.drawString(words[current - 1], x, ticks, 0, 96);
		if(ticksExisted > 0)
		{
			
			font.drawString("Press Any Key to play Again ...", 300, 600, 4, 48);
			font.drawString("Press Escape to Quit ...", 300, 650, 4, 48);
			
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPushMatrix();
			GL11.glTranslated(1200, 700, 1);
			GL11.glPushMatrix();
			GL11.glRotated(ticksExisted * 60, 0, 0, 1);
			OpenGL.glColor(color);
			OpenGL.glBegin();
			Draw.rect(-50, -50, 50, 50, 3);
			Color4d inner = new Color4d(OpenGL.getColor());
			OpenGL.glColor(0d, 0d, 0d, 1d);
			Draw.rect(-50, -50, 50, 50, 4, 5, inner);
			GL11.glEnd();
			GL11.glPopMatrix();
			for(int i = 0; i < 4; i++)
			{
				GL11.glPushMatrix();
				OpenGL.glColor(1, 1, 1, .3);
				GL11.glRotated(ticksExisted * (i + 1) * 40, 0, 0, 1);
				OpenGL.glBegin();
				GL11.glVertex3d(0, 0, -2);
				GL11.glVertex3d(50, -50, -2);
				GL11.glVertex3d(-200, -200, -2);
				GL11.glVertex3d(-50, 50, -2);
				
				GL11.glVertex3d(0, 0, -2);
				GL11.glVertex3d(-50, -50, -2);
				GL11.glVertex3d(-200, 200, -2);
				GL11.glVertex3d(50, 50, -2);
				
				GL11.glVertex3d(0, 0, -2);
				GL11.glVertex3d(-50, 50, -2);
				GL11.glVertex3d(200, 200, -2);
				GL11.glVertex3d(50, -50, -2);
				
				GL11.glVertex3d(0, 0, -2);
				GL11.glVertex3d(50, 50, -2);
				GL11.glVertex3d(200, -200, -2);
				GL11.glVertex3d(-50, -50, -2);
				GL11.glEnd();
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();
		}
	}
	
	public void update(double delta)
	{
		ticks = MathHelper.getExpValue(ticks, toTicks, .05, delta);
		ticks = MathHelper.getLinearValue(ticks, toTicks, 3, delta);
		if(MathHelper.isEqual(ticks, toTicks))
		{
			if(current != words.length)
			{
				ticks = 0;
				current ++;
			}
			else
			{
				ticksExisted += delta;
			}
		}
	}
	
	public int keyPressed(int keyId, int mods)
	{
		if(keyId == GLFW.GLFW_KEY_ESCAPE)
		{
			return super.keyPressed(keyId, mods);
		}
		application.setGui(new GuiGame(application));
		return -1;
	}

}
