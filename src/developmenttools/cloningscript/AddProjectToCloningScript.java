package developmenttools.cloningscript;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import developmenttools.utilities.FileUtilities;

public class AddProjectToCloningScript
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        FileUtilities fileUtilities = new FileUtilities();
        
        try
        {
            InputStream input = fileUtilities.loadFileStream(AddProjectToCloningScript.class, "developmenttools/cloningscript/AddProjectToCloningScript.prop");
            props.load(input);
            String cloningScriptLocation = props.getProperty("cloning.script.location");
            Properties repositoriesToCloneProps = new Properties();
            File file = new File(cloningScriptLocation);
            InputStream input1 = new FileInputStream(file);
            repositoriesToCloneProps.load(input1);
            String typeOfProjectToAdd = props.getProperty("type.of.project.to.add");
            
            if("library".equals(typeOfProjectToAdd))
            {
                int numberOfExistingLibraries = Integer.parseInt(repositoriesToCloneProps.getProperty("number_of_orion_libraries_repositories"));
                ++numberOfExistingLibraries;
                String newRepoKey = "orion_libraries_repository_" + numberOfExistingLibraries;
                String newProjectName = props.getProperty("new.project.name");
                String newRepo = "https://github.com/orionware-libraries/" + newProjectName + ".git";
                String newRepoDirKey = "orion_libraries_repository_dir_" + numberOfExistingLibraries;
                String newRepoDir = "/c/workspaces/orion/libraries/projects/" + newProjectName;
                BufferedReader inputFile = null;
                
                try
                {
                    inputFile = new BufferedReader(new FileReader(cloningScriptLocation));
                    String currentLine = null;
                    StringBuilder fileStringBuilder = new StringBuilder();

                    while((currentLine = inputFile.readLine()) != null)
                    {
                        if(currentLine.indexOf("number_of_orion_libraries_repositories=") != -1)
                        {
                            currentLine = currentLine.replace(currentLine, "number_of_orion_libraries_repositories=" + numberOfExistingLibraries);
                        }
                        
                        fileStringBuilder.append(currentLine);
                        fileStringBuilder.append(System.lineSeparator());
                    }
                    
                    fileStringBuilder.append(newRepoKey + "=" + newRepo);
                    fileStringBuilder.append(System.lineSeparator());
                    fileStringBuilder.append(newRepoDirKey + "=" + newRepoDir);
                    fileStringBuilder.append(System.lineSeparator());
                    inputFile.close();
                    new FileUtilities().saveStringToFile(cloningScriptLocation, fileStringBuilder.toString());
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
            }
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }
}