package developmenttools.cloningscript.deleteproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import developmenttools.utilities.FileUtilities;

public class DeleteProjectFromCloningScript
{
    public static void main(String[] args)
    {
        Properties props = new Properties();
        FileUtilities fileUtilities = new FileUtilities();
        
        try
        {
            InputStream input = fileUtilities.loadFileStream(DeleteProjectFromCloningScript.class, "developmenttools/cloningscript/deleteproject/DeleteProjectFromCloningScript.prop");
            props.load(input);
            String cloningScriptLocation = props.getProperty("cloning.script.location");
            Properties repositoriesToCloneProps = new Properties();
            File file = new File(cloningScriptLocation);
            InputStream input1 = new FileInputStream(file);
            repositoriesToCloneProps.load(input1);
            String typeOfProjectToDelete = props.getProperty("type.of.project.to.delete");
            
            if("library".equals(typeOfProjectToDelete))
            {
                int numberOfExistingLibraries = Integer.parseInt(repositoriesToCloneProps.getProperty("number_of_orion_libraries_repositories"));
                --numberOfExistingLibraries;
                String projectNameToDelete = props.getProperty("project.name.to.delete");
                BufferedReader inputFile = null;
                
                try
                {
                    inputFile = new BufferedReader(new FileReader(cloningScriptLocation));
                    String currentLine = null;
                    String repoKey = "";
                    String newRepoKey = "";
                    String repoDirKey = "";
                    String newRepoDir = "";
                    int repoNumber = 0;
                    StringBuilder fileStringBuilder = new StringBuilder();

                    while((currentLine = inputFile.readLine()) != null)
                    {
                        if(currentLine.indexOf("number_of_orion_libraries_repositories=") != -1)
                        {
                            currentLine = currentLine.replace(currentLine, "number_of_orion_libraries_repositories=" + numberOfExistingLibraries);
                            fileStringBuilder.append(currentLine);
                            fileStringBuilder.append(System.lineSeparator());
                        }
                        else if(currentLine.indexOf("https://github.com/orionware-libraries/" + projectNameToDelete + ".git") == -1 && currentLine.indexOf("/c/workspaces/orion/libraries/projects/" + projectNameToDelete) == -1)
                        {
                            if(currentLine.indexOf("https://github.com/orionware-libraries/") != -1)
                            {
                                repoKey = currentLine.substring(0, currentLine.indexOf("="));
                                repoNumber = Integer.parseInt(repoKey.substring(repoKey.lastIndexOf("_") + 1));
                                --repoNumber;
                                newRepoKey = repoKey.substring(0, repoKey.lastIndexOf("_") + 1) + repoNumber;
                                currentLine = currentLine.replace(repoKey, newRepoKey);
                            }
                            else if(currentLine.indexOf("/c/workspaces/orion/libraries/projects/") != -1)
                            {
                                repoDirKey = currentLine.substring(0, currentLine.indexOf("="));
                                repoNumber = Integer.parseInt(repoDirKey.substring(repoDirKey.lastIndexOf("_") + 1));
                                --repoNumber;
                                newRepoDir = repoDirKey.substring(0, repoDirKey.lastIndexOf("_") + 1) + repoNumber;
                                currentLine = currentLine.replace(repoDirKey, newRepoDir);
                            }
                            
                            //orion_libraries_repository_dir_7=/c/workspaces/orion/libraries/projects/libraries-builder
                        }
                    }
                    
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