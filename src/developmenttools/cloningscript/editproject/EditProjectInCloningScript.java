package developmenttools.cloningscript.editproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import developmenttools.utilities.FileUtilities;

public class EditProjectInCloningScript
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        FileUtilities fileUtilities = new FileUtilities();
        
        try
        {
            InputStream input = fileUtilities.loadFileStream(EditProjectInCloningScript.class, "developmenttools/cloningscript/editproject/EditProjectInCloningScript.prop");
            props.load(input);
            String cloningScriptLocation = props.getProperty("cloning.script.location");
            Properties repositoriesToCloneProps = new Properties();
            File file = new File(cloningScriptLocation);
            InputStream input1 = new FileInputStream(file);
            repositoriesToCloneProps.load(input1);
            String typeOfProjectToEdit = props.getProperty("type.of.project.to.edit");
            
            if("library".equals(typeOfProjectToEdit))
            {
                int numberOfExistingLibraries = Integer.parseInt(repositoriesToCloneProps.getProperty("number_of_orion_libraries_repositories"));
                String repoKey = "orion_libraries_repository_" + numberOfExistingLibraries;
                String projectNameToEdit = props.getProperty("project.name.to.edit");
                String newProjectName = props.getProperty("new.project.name");
                String repo = "https://github.com/orionware-libraries/" + projectNameToEdit + ".git";
                String repoDirKey = "orion_libraries_repository_dir_" + numberOfExistingLibraries;
                String repoDir = "/c/workspaces/orion/libraries/projects/" + projectNameToEdit;
                BufferedReader inputFile = null;
                
                try
                {
                    inputFile = new BufferedReader(new FileReader(cloningScriptLocation));
                    String currentLine = null;
                    StringBuilder fileStringBuilder = new StringBuilder();

                    while((currentLine = inputFile.readLine()) != null)
                    {
                        if(currentLine.indexOf("https://github.com/orionware-libraries/" + projectNameToEdit + ".git") != -1)
                        {
                            currentLine = currentLine.replace("https://github.com/orionware-libraries/" + projectNameToEdit + ".git", "https://github.com/orionware-libraries/" + newProjectName + ".git");
                        }
                        else if(currentLine.indexOf("/c/workspaces/orion/libraries/projects/" + projectNameToEdit) != -1)
                        {
                            currentLine = currentLine.replace("/c/workspaces/orion/libraries/projects/" + projectNameToEdit, "/c/workspaces/orion/libraries/projects/" + newProjectName);
                        }
                        
                        fileStringBuilder.append(currentLine);
                        fileStringBuilder.append(System.lineSeparator());
                    }
                    
                    fileStringBuilder.append(repoKey + "=" + repo);
                    fileStringBuilder.append(System.lineSeparator());
                    fileStringBuilder.append(repoDirKey + "=" + repoDir);
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