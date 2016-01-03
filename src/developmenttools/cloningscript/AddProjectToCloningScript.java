package developmenttools.cloningscript;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AddProjectToCloningScript
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        
        try
        {
            ClassLoader classLoader = Class.forName(AddProjectToCloningScript.class.getName()).getClassLoader();
            InputStream input = classLoader.getResourceAsStream("developmenttools/cloningscript/AddProjectToCloningScript.prop");
            props.load(input);
            String cloningScriptDir = props.getProperty("cloning.script.dir");
        }
        catch(ClassNotFoundException exception)
        {
            exception.printStackTrace();
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }
}