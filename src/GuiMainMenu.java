import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.polaris.engine.App;
import com.polaris.engine.Application;
import com.polaris.engine.gui.GuiScreen;
import com.polaris.engine.render.Draw;
import com.polaris.engine.render.FontMap;
import com.polaris.engine.render.OpenGL;
import com.polaris.engine.render.Texture;
import com.polaris.engine.render.Window;
import com.polaris.engine.util.Color4d;
import com.polaris.engine.util.MathHelper;

public class GuiMainMenu extends GuiScreen
{

    private static final double SCENE_LENGTH = 4;

    private double ticksExisted = -.25;

    private double alpha = 0;
    
    private double stackSmashingAlpha = 0;
    
    private char[] sequence = "STACK SMASHING".toCharArray();
    private double stackSmashingStartX = 0;
    private double[] ticks = new double[sequence.length];
    private double[] toTicks = new double[sequence.length];

    private FontMap fontMap;

    public GuiMainMenu(Application app) 
    {
        super(app);
        fontMap = Texture.getFontMap("basic");

        if(!((HelloApp) application).getSettings().isFirstRun())
        {
            ticksExisted = SCENE_LENGTH * 3;
        }
        
        stackSmashingStartX = 1920 / 2 - fontMap.getTextWidth(new String(sequence), 128) / 2;
    }

    public void render(double delta)
    {
        Window.gl2d();
        OpenGL.glBlend();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        Texture.glBindTexture("font:basic");

        Color4d orangeShift = new Color4d(1d, 200d / 255d, 0d, 1d);
        
        if(ticksExisted < SCENE_LENGTH)
        {
            OpenGL.glColor(1d, 200d / 255d, 0d, alpha);
            fontMap.drawAlignedString("A", 1920 / 2, 100, 0, 128, 1);
            OpenGL.glColor(1d, 133d / 255d, 0d, alpha);
            fontMap.drawAlignedCString("Jordan", 1920 / 2 + fontMap.getTextWidth("Jordan", 96) / 4, 300, 0, 96, 2, new Color4d(1d, 0d, 0d, alpha));
            fontMap.drawAlignedCString("Killian", 1920 / 2 - fontMap.getTextWidth("Killian", 96) / 4, 500, 0, 96, 0, new Color4d(1d, 66d / 255d, 0d, alpha));
            fontMap.drawAlignedCString("Jordan", 1920 / 2 + fontMap.getTextWidth("Jordan", 96) / 4, 700, 0, 96, 2, new Color4d(1d, 0d, 0d, alpha));
            OpenGL.glColor(1d, 0d, 0d, alpha);
            fontMap.drawAlignedString("Game", 1920 / 2,  850, 0, 128, 1);
        }
        else if(ticksExisted < SCENE_LENGTH * 2)
        {
            OpenGL.glColor(1d, 1d, 1d, alpha);
            fontMap.drawString("POWERED BY", 400, 300, 0, 64);
            
            Texture.glBindTexture("logo", "LWJGL_logo");
            OpenGL.glBegin();
            Draw.rectUV(500, 400, 1500, 900, 0, 0, 0, 1, .99);
            GL11.glEnd();
        }
        else if(ticksExisted < SCENE_LENGTH * 3)
        {
            OpenGL.glColor(1d, 0d, 0d, stackSmashingAlpha);
            
            orangeShift.setAlpha(stackSmashingAlpha);
            
            fontMap.drawAlignedCString("STACK SMASHING", 1920 / 2, 400, 0, 128, 1, orangeShift);
        }
        else
        {
            OpenGL.glColor(1d, 0d, 0d, 1d);
            
            orangeShift.setColor(1d, 0d, 0d, 1d);
            
            double shiftX = stackSmashingStartX;
            double orange = (200 / 255d) / sequence.length;
            
            for(int i = 0; i < sequence.length; i++)
            {
                orangeShift.setColor(1d, orangeShift.getGreen() + orange, 0d, 1d);
                fontMap.drawColorString("" + sequence[i], shiftX, 400 + ticks[i], 0, 128, orangeShift);
                shiftX += fontMap.getTextWidth("" + sequence[i], 128);
            }
            
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
    }

    public void update(double delta)
    {
        ticksExisted += delta;

        if(ticksExisted >= SCENE_LENGTH * 3)
        {
            for(int i = 0; i < ticks.length; i++)
            {
                if(MathHelper.isEqual(ticks[i], toTicks[i]))
                {
                    toTicks[i] = Math.random() * 20 - 10;
                }
                else
                {
                    ticks[i] = MathHelper.getExpValue(ticks[i], toTicks[i], .25, delta);
                }
            }
        }
        else if(ticksExisted >= SCENE_LENGTH * 2)
        {
            stackSmashingAlpha = ticksExisted / SCENE_LENGTH - 2;
        }
        else if(ticksExisted >= 0)
        {
            if(((int) ticksExisted) % ((int) SCENE_LENGTH) < 1)
            {
                alpha = ((ticksExisted - (int) ticksExisted) / SCENE_LENGTH) * 4;
            }
            else if(((int) ticksExisted) % ((int) SCENE_LENGTH) >= 3)
            {
                alpha = 1 - ((ticksExisted - (int) ticksExisted) / SCENE_LENGTH) * 4;
            }
        }
    }

    public boolean mouseClick(int mouseId)
    {
        if((int) (ticksExisted) % ((int) SCENE_LENGTH) + 1 >= 1)
        {
            ticksExisted = ((int) ticksExisted) + SCENE_LENGTH - ((int) ticksExisted) % ((int) SCENE_LENGTH);
        }
        if(App.getMouseX() >= 1920 / 2 - fontMap.getTextWidth("START A GAME", 48) && App.getMouseX() <= 1920 / 2 + fontMap.getTextWidth("START A GAME", 48))
        {
            if(App.getMouseY() >= 600 - 32 && App.getMouseY() <= 600 + 32)
            {
                application.setGui(new GuiGame(application));
            }
        }
        return true;
    }

    public int keyPressed(int keyId, int mods)
    {
        if(keyId == GLFW.GLFW_KEY_ENTER && ticksExisted >= SCENE_LENGTH * 3)
        {
            application.setGui(new GuiGame(application));
        }
        return super.keyPressed(keyId, mods);
    }

}
