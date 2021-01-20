import java.io.*;
import java.util.*;
/**
 * Write a description of class Menu here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ZuulGame
{
    public final String NEW = "new";
    public final String LOAD = "load";
    public final String QUIT = "quit";
    public final String EASY = "easy";
    public final String MEDIUM = "medium";
    public final String HARD = "hard";
    public final String CUSTOM = "custom";
    
    File dir = new File("SaveData");
    
    
    private Menu menu;
    
    private Game game;
    
    private RoomGenerator roomgen;
    
    public String name;
    
    private DatabaseManager db;
    // Allows reader to be called directly against the class Menu
    private InputReader reader;

    //Sets the array for menu choice
    private String [] menuChoices;
    private String [] gameChoices;
    public ArrayList<String> tunnelList = new ArrayList<String>();
    
    /**
     * Scans user input and outputs programmed choices 
     */
    public ZuulGame()
    {
        db = new DatabaseManager();
        reader = new InputReader();
        menuSetup();
    }
    
    /**
     * A function which initialises the menu and starts the program
     */
    public void run()
    {
        getMenuChoice();
    }
    
    public void syntaxSpacer()
    {
        int c = 0;
        while(c < 50)
        {
            System.out.println("");
            c++;
        }
    }
    
    /**
     * Interprets user input at menu to execute a given task
     */
    public void getMenuChoice()
    {
        boolean finished = false;
        while(!finished)
        {
            syntaxSpacer();
            printHeading();
            String choice = Menu.getMenuChoice(menuChoices);
            executeMenuChoice(choice);
            if(choice.equals(QUIT))
            {
                System.exit(0);
            }
            while(!finished)
            {
                game.play();
            }
        }
    }
    
    /**
     * Interprets user input at menu to execute a given task
     */
    public void getGameChoice()
    {
        boolean finished = false;
        while(!finished)
        {
            syntaxSpacer();
            String choice = Menu.getMenuChoice(gameChoices);
            executeGameChoice(choice);
            if(choice.equals(QUIT))
            {
                System.exit(0);
            }
        }
    }
    
    /**
     * Print the title of the program and the authors name
     */
    private void printHeading()
    {
        System.out.println("==============================================");
        System.out.println("                   Zuul Game                  ");
        System.out.println("==============================================");
        System.out.println("GAME allows individuals to indulge in the crazy");
        System.out.println("world of monsters, where one needs to either kill");
        System.out.println("or be killed to survive. The player must travel");
        System.out.println("through different areas of the game battling through");
        System.out.println("foes and enemies to reach the end, while scavenging");
        System.out.println("for hidden treasures spread amongst the grounds of");
        System.out.println("the game. Beware once in the land of GAME the only way out is to win.");
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
        gameChoices = new String []
        {
            "Easy",
            "Medium",
            "Hard",
            "Custom"
        };
    }
    
    /**
     * Uses given choice to execute task linking set variables
     */
    private void executeMenuChoice(String choice)
    {
        if(choice.equals(NEW))
        {
            name = reader.getString("Character Name:");
            saveDataFinder(false);
            if(!tunnelList.contains(name))
            {
                executeGameChoice(name);
            }
            else
            {
                System.out.println("Name taken");
            }
        }
        else if(choice.equals(LOAD))
        {
            System.out.println("==============================================");
            System.out.println(" Game Saves");
            System.out.println("==============================================");
            System.out.println("");
            saveDataFinder(true);
            System.out.println("");
            System.out.println("==============================================");
            name = reader.getString("Character Name:");
            if(tunnelList.contains(name))
            {
                db.initialiseSaveData(name);
                game = new Game(false, name, 0);
            }
            else
            {
                System.out.println("Invalid Name");
            }
        }
        else if(choice.equals(QUIT))
        {
            System.exit(0);
        }
        else
        {
        }
    }
    
    public void saveDataFinder(boolean output)
    {
        tunnelList.removeAll(tunnelList);
        for (String pathname : dir.list())
        {
            pathname = pathname.replace(".zuul", "");
            tunnelList.add(pathname);
            if(output)
            {
                System.out.println(pathname);
            }
        }
    }
    
    /**
     * Uses given choice to execute task linking set variables
     */
    private void executeGameChoice(String name)
    {
        String choice = reader.getString("Difficulty:");
        if(choice.equals(EASY))
        {
            db.initialiseSaveData(name);
            game = new Game(true, name, 5);
        }
        else if(choice.equals(MEDIUM))
        {
            db.initialiseSaveData(name);
            game = new Game(true, name, 9);
        }
        else if(choice.equals(HARD))
        {
            db.initialiseSaveData(name);
            game = new Game(true, name, 13);
        }
        else if(choice.equals(CUSTOM))
        {
            int range = reader.getInt("Range:");
            db.initialiseSaveData(name);
            game = new Game(true, name,range);
        }
        else if(choice.equals(QUIT))
        {
            System.exit(0);
        }
    }
}