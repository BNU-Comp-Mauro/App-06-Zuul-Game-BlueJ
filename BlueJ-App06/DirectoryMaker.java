import java.io.File;
/**
 * Write a description of class DirectoryManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DirectoryMaker
{
    public DirectoryMaker()
    {
        createDirArray();
    } 

    public void createDirArray()
    {
        String[] files = {"ProgramFiles", "SaveData"};
        for (String i : files)
        {
            File f = new File(i); 
            if (f.mkdir()) { 
                System.out.println("Directory is created"); 
            } 
            else { 
                System.out.println("Directory cannot be created"); 
            }
        }
    } 

    public void createDir(String folder)
    {
        File f = new File(folder); 
        if (f.mkdir()) {
            System.out.println("Directory is created"); 
        } 
        else { 
            System.out.println("Directory cannot be created"); 
        }
    } 
}
