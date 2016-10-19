import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameSettings
{

    private boolean firstRun = true;

    public GameSettings()
    {
        File optionsFile = new File("game.settings");

        try
        {
            if(!optionsFile.createNewFile())
            {
                //firstRun = false;
                
                BufferedReader reader = new BufferedReader(new FileReader(optionsFile));
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    
                }
                reader.close();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void save()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("game.settings")));
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean isFirstRun()
    {
        return firstRun;
    }

}
