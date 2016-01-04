package developmenttools.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.io.FileUtils;

public class FileUtilities
{
    public InputStream loadFileStream(Class<?> aClass, String fileLocation)
    {
        
        ClassLoader classLoader = null;
        
        try
        {
            classLoader = Class.forName(aClass.getName()).getClassLoader();
        }
        catch(ClassNotFoundException exception)
        {
            exception.printStackTrace();
        }
        
        return classLoader.getResourceAsStream(fileLocation);
    }
    
    
    public String convertFileToString(String filePath)
    {
        String fileString = "";
        BufferedReader input = null;
        
        try
        {
            input = new BufferedReader(new FileReader(filePath));
            StringBuilder fileStringBuilder = new StringBuilder();
            String currentLine = input.readLine();

            while(currentLine != null)
            {
                fileStringBuilder.append(currentLine);
                fileStringBuilder.append(System.lineSeparator());
                currentLine = input.readLine();
            }
            
            fileString = fileStringBuilder.toString();
        }
        catch(FileNotFoundException e)
        {
            
        }
        catch(IOException e)
        {
            
        }
        finally
        {
            if(input != null)
            {
                try
                {
                    input.close();
                }
                catch(IOException e)
                {
                    
                }
            }
        }

        return fileString;
    }


    public void saveStringToFile(String filePath, String fileString)
    {
        BufferedWriter output = null;
        String lineSeparator = System.lineSeparator();
        
        try
        {
            output = new BufferedWriter(new FileWriter(filePath));
            String[] lines = fileString.split(lineSeparator);
            
            for(String line : lines)
            {
                output.write(line);
                output.write(lineSeparator);
            }
        }
        catch(FileNotFoundException e)
        {
            
        }
        catch(IOException e)
        {
            
        }
        finally
        {
            if(output != null)
            {
                try
                {
                    output.close();
                }
                catch(IOException e)
                {
                    
                }
            }
        }
    }
    
    
    public void emptyDirectory(String directory)
    {
        try
        {
            System.out.println(directory);
            FileUtils.cleanDirectory(new File(directory)); 
        }
        catch(Exception e)
        {
            
        }
    }
}