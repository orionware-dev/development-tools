package developmenttools.injector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import developmenttools.utilities.FileUtilities;

public class ReplaceClassReferencesForInjectors
{
    private static FileUtilities fileUtilities;
    private static String oldClassPath;
    private static String newClassPath;
    
    
    public static void main(String[] args)
    {
        Properties props = new Properties();
        fileUtilities = new FileUtilities();

        try
        {
            InputStream input = fileUtilities.loadFileStream(ReplaceClassReferencesForInjectors.class, "developmenttools/injector/ReplaceClassReferencesForInjectors.prop");
            props.load(input);
            String OrionLibrariesRepoDir = props.getProperty("orion.libraries.repo.dir");
            oldClassPath = props.getProperty("old.class.path");
            newClassPath = props.getProperty("new.class.path");
            int sourceDirCounter = 1;
            String currentSourceDir = null;

            while(props.getProperty("source.dir.to.scan." + sourceDirCounter) != null)
            {
                currentSourceDir = OrionLibrariesRepoDir + props.getProperty("source.dir.to.scan." + sourceDirCounter);
                traverseDirectory(currentSourceDir);
                ++sourceDirCounter;
            }
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }


    private static void traverseDirectory(String currentFileOrDir)
    {
        File temp = new File(currentFileOrDir);
        
        if(temp.isDirectory())
        {
            String[] childNodes = temp.list();
            
            for(String childNode : childNodes)
            {
                traverseDirectory(currentFileOrDir + "/" + childNode);
            }
        }
        else if(temp.isFile() && temp.getName().endsWith(".java"))
        {
            String JavaFileContent = fileUtilities.convertFileToString(temp.getAbsolutePath());
            String newJavaFileContent = JavaFileContent.replace("@Injector(ID = \"" + oldClassPath, "@Injector(ID = \"" + newClassPath);
            
            if(!newJavaFileContent.equals(JavaFileContent))
            {
                fileUtilities.saveStringToFile(temp.getAbsolutePath(), newJavaFileContent);
            }
        }
    }
}