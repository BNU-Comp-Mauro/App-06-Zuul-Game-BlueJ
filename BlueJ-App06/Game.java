import java.util.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * 
 * Modified and extended by Your name
 */

public class Game 
{
    private Items item;
    private Parser parser;
    private Player player;
    private Room currentRoom;
    private DatabaseManager db;
    private RoomGenerator roomGen;
    private static InputReader reader = new InputReader();
    public ArrayList<String> playerData = new ArrayList<String>();
    public ArrayList<String> playerDataInv = new ArrayList<String>();
  
    public String name;
    public int hp;
    public int coins;
    public int xp;
    public int energy;
    public String armorSlot;
    public String itemSlot1;
    public String itemSlot2;
    public String location;
    /**
     * Create the game and initialise its internal map.
     */
    public Game(boolean newGame, String name, int range) 
    {
        parser = new Parser();
        player = new Player(name, newGame);
        playerData = player.getPlayerData(name);
        
        this.name = name;
        updatePlayerData();
        System.out.println(playerData);
        if(newGame)
        {
            roomGen = new RoomGenerator (name, range);
        }
        play();
    }
    
    public void updatePlayerData()
    {
        playerData.removeAll(playerData);
        playerDataInv.removeAll(playerDataInv);
        playerData = player.getPlayerData(name);
        hp = Integer.parseInt(playerData.get(1));
        coins = Integer.parseInt(playerData.get(2));
        xp = Integer.parseInt(playerData.get(3));
        energy = Integer.parseInt(playerData.get(4));
        armorSlot = playerData.get(5);
        itemSlot1 = playerData.get(6);
        itemSlot2 = playerData.get(7);
        location = playerData.get(8);
        playerDataInv = player.getPlayerInventory(name);
    }

    public void updatePlayerDB(String column, String newData)
    {   
        db.manual_updateMultiDB("north = " + north + ", east = " + east + ", south = " + south + ", west = " + west, "room", "x = " + targetX + " AND y = " + targetY);
    } 
    
    public void updateInventoryDB()
    {   
        while(true)
        {
        }
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;

        while (! finished) 
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) 
        {
            case UNKNOWN:
            System.out.println("I don't know what you mean...");
            break;

            case HELP:
            printHelp();
            break;

            case GO:
            goRoom(command);
            break;

            case GRAB:
            player.grabItem(item, command);
            break;

            case DROP:
            player.dropItem(item, command);
            break;

            case INVENTORY:
            player.getItemList();
            break;

            case STATS:
            player.printStats();
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}