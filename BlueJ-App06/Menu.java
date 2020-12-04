
/**
 * Write a description of class Menu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Menu
{
    private static Game game;
    private String folder;
    private String filename;
    private String table;
    private String columns;
    private String data;
    
    private static DatabaseManager db;
    // Allows reader to be called directly against the class Menu
    private static InputReader reader;
    
    //Sets the array for menu choice
    private String [] menuChoices;
    
    /**
     * Scans user input and outputs programmed choices 
     */
    public Menu()
    {
        db = new DatabaseManager();
        reader = new InputReader();
    }
    
    /**
     * Contains a string array for the menu text
     */
    private void menuSetup()
    {
        menuChoices = new String []
        {
            "New Game",
            "Load Game",
            "Quit"  
        };
    }
    
    public void newGame()
    {
        String name = reader.getString("Character Name:");
        db.initialiseSaveData(name);
        db.insertDB(folder, filename, table, columns, data );
    }
    
    /**
     * A function which initialises the menu and starts the game
     */
    public void run()
    {
        menuSetup();
    }
}
