package developmenttools.workspacecleaner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import developmenttools.utilities.FileUtilities;

public class WorkspaceCleaner
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        FileUtilities fileUtilities = new FileUtilities();
        
        try
        {
            InputStream input = fileUtilities.loadFileStream(WorkspaceCleaner.class, "developmenttools/workspacecleaner/WorkspaceCleaner.prop");
            props.load(input);
            int dirNumber = 1;
            String dirToClean = "";
            
            do
            {
                dirToClean = props.getProperty("dir.to.clean." + dirNumber);
                fileUtilities.emptyDirectory(dirToClean);
                ++dirNumber;
            }
            while(dirToClean != null && !dirToClean.isEmpty());
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }
}