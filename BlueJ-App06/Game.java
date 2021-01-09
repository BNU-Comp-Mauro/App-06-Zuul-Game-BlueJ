
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
    public Player player;
    private Room currentRoom;
    Random rand = new Random();
    private DatabaseManager db;
    private RoomGenerator roomGen;
    private static InputReader reader = new InputReader();
    public ArrayList<String> playerData = new ArrayList<String>();
    public ArrayList<String> playerDataInv = new ArrayList<String>();
    Hashtable<Integer, String> multiplierRanking = new Hashtable<Integer, String>();
    
    Hashtable<Integer, String> ranking = new Hashtable<Integer, String>();
    public String name;
    public int hp;
    public int coins;
    public int xp;
    public int energy;
    public String armorSlot;
    public String itemSlot1;
    public String itemSlot2;
    public String location;
    
    ArrayList<String> printInv = new ArrayList<String>();
    
    public ArrayList<String> idArray = new ArrayList<String>();
    
    public int attackRating = 0;
    public int defenseRating = 0;
    /**
     * Create the game and initialise its internal map.
     */
    public void Game(boolean newGame, String name, int range) 
    {
        parser = new Parser();
        player = new Player(name, newGame);
        playerData = player.getPlayerData(name);
        multiplierRanking.put(1, "Common");
        multiplierRanking.put(2, "Uncommon");
        multiplierRanking.put(3, "Rare");
        multiplierRanking.put(4, "Epic");
        multiplierRanking.put(5, "Ultra");
        multiplierRanking.put(6, "Legendary");
        

        
        ranking.put(1, "1,2,3");
        ranking.put(2, "2,3");
        ranking.put(3, "2,3,4");
        ranking.put(4, "1,2,3,4,5,6");
        this.name = name;
        if(newGame)
        {
            roomGen = new RoomGenerator (name, range);
        }
        getPlayerData(name);
        printInventory(name);
    }
    
    
    public void randItemDropper(String name, int roomID, int rank, boolean visited, int xp)
    {
        String sqlData = "";
        Hashtable<Integer, String> itemRanking = new Hashtable<Integer, String>();
        itemRanking.put(1, "C1,W1,W2,W3");
        itemRanking.put(2, "W4,P1,P2");
        itemRanking.put(3, "W5,W6,A1");
        itemRanking.put(4, "W7,A2,W8");
        int xpInfluence = 0;
        if(xp >= 5000)
        {
            xpInfluence = 5;
        }
        else if(xp >= 3500)
        {
            xpInfluence = 4;
        }
        else if(xp >= 2500)
        {
            xpInfluence = 3;
        }
        else if(xp >= 1500)
        {
            xpInfluence = 2;
        }
        else if(xp >= 750)
        {
            xpInfluence = 1;
        }
        
        if(visited)
        {
            if(rand.nextInt(4) == 1) // 1 in 4 chance
            {
                sqlData = rand.nextInt(6)+1 + "-C1";
            }
        }
        else
        {
            if(rank != 4 && (rand.nextInt(6) - xpInfluence) == 1) // 1 in 3 chance
            {
                rank++;
                if(rank != 4 && (rand.nextInt(8) - xpInfluence) == 1) // 1 in 8 chance
                {
                    rank++;
                    if(rank != 4 && (rand.nextInt(10) - xpInfluence) == 1) // 1 in 16 chance
                    {
                        rank++;
                    }
                }
            }
            if(rank == 1)
            {
                String[] itemID = itemRanking.get(1).split(",");
                String itemIDResult = itemID[rand.nextInt(itemID.length)];
                sqlData = rand.nextInt(3)+1 + "-" + itemIDResult;
            }
            else if(rank == 2)
            {
                String[] itemID = itemRanking.get(rand.nextInt(rank)+1).split(",");
                String itemIDResult = itemID[rand.nextInt(itemID.length)];
                sqlData = rand.nextInt(4)+1 + "-" + itemIDResult;
            }
            else if(rank == 3)
            {
                String[] itemID = itemRanking.get(rand.nextInt(rank)+1).split(",");
                String itemIDResult = itemID[rand.nextInt(itemID.length)];
                sqlData = rand.nextInt(5)+1 + "-" + itemIDResult;
            }
            else if(rank == 4)
            {
                String[] itemID = itemRanking.get(rand.nextInt(rank)+1).split(",");
                String itemIDResult = itemID[rand.nextInt(itemID.length)];
                sqlData = rand.nextInt(6)+1 + "-" + itemIDResult;
            }
        }
        
        if(!sqlData.equals(""))
        {
            
            ArrayList<String> checkInv = db.manual_getDroppedItems(name);
            db.manual_connectSaveDataDB(name, false);
            boolean check = true;
            for(String i : checkInv)
            {
                System.out.println(i);
                String[] splitArray = i.split(",");
                String invID = splitArray[0];
                int quantity = Integer.parseInt(splitArray[1]);
                if(invID == sqlData)
                {
                    db.manual_updateMultiDB("quantity =" + (quantity + 1), "droppedItems", "invID ='" + sqlData + "' AND roomID =" + roomID);
                    check = false;
                }
            }
            if(check)
            {
                db.manual_insertDB("droppedItems", "roomID, invID", roomID + ", '" + sqlData + "'");
            }
            db.manual_closeDB();
        }
    }

    
    public void getPlayerData(String name)
    {
        db.manual_connectSaveDataDB(name, false);
        hp = Integer.parseInt(db.manual_getDataDB("hp", "player", "name = '" + name + "'")); //hp 1
        coins = Integer.parseInt(db.manual_getDataDB("coins", "player", "name = '" + name + "'")); //coins 2
        xp = Integer.parseInt(db.manual_getDataDB("xp", "player", "name = '" + name + "'")); //xp 3
        energy = Integer.parseInt(db.manual_getDataDB("energy", "player", "name = '" + name + "'")); //energy 4
        armorSlot = db.manual_getDataDB("armorSlot", "player", "name = '" + name + "'"); //armorSlot 5
        itemSlot1 = db.manual_getDataDB("itemSlot1", "player", "name = '" + name + "'"); //itemSlot1 6
        itemSlot2 = db.manual_getDataDB("itemSlot2", "player", "name = '" + name + "'"); //itemSlot2 7
        location = db.manual_getDataDB("location", "player", "name = '" + name + "'"); //location 8
        db.manual_closeDB();
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
    
    public String idNameParser(String id)
    {
        String[] values = id.split("-");
        String rank= "";
        String itemName= "";
        if(id == null || id == "")
        {
        }
        else
        {
            rank = multiplierRanking.get(Integer.parseInt(values[0]));
            db.manual_connectProgramFilesDB(false);
            itemName = db.manual_getDataDB("name", "itemData", "ID = '" + values[1] + "'");
        }
        db.manual_closeDB();
        return rank + " " + itemName;
    }
    
    public void updateEquiptRatings(String name)
    {
        attackRating = 0;
        defenseRating = 0;
        db.manual_connectSaveDataDB(name, false);
        idArray.removeAll(idArray);
        idArray.add(db.manual_getDataDB("itemSlot1", "player", "name = '" + name + "'"));
        idArray.add(db.manual_getDataDB("itemSlot2", "player", "name = '" + name + "'"));
        idArray.add(db.manual_getDataDB("armorSlot", "player", "name = '" + name + "'"));
        db.manual_closeDB();
        db.manual_connectProgramFilesDB(false);
        for(String i : idArray)
        {
            if(!i.equals("n/a"))
            {
                String[] values = i.split("-");
                int multiplier = Integer.parseInt(values[0]);
                String itemID = values[1];
                attackRating += +(multiplier * Integer.parseInt(db.manual_getDataDB("damage", "itemData", "ID = '" + itemID + "'")));
                defenseRating += +(multiplier * Integer.parseInt(db.manual_getDataDB("protection", "itemData", "ID = '" + itemID + "'")));
            }
        }
        db.manual_closeDB();
    }
    
    public void dataParser(ArrayList<String> data)
    {
        System.out.println(data);
        hp = Integer.parseInt(data.get(0)); //hp 1
        coins = Integer.parseInt(data.get(1)); //coins 2
        xp = Integer.parseInt(data.get(2)); //xp 3
        energy = Integer.parseInt(data.get(3)); //energy 4
        armorSlot = data.get(4); //armorSlot 5
        itemSlot1 = data.get(5); //itemSlot1 6
        itemSlot2 = data.get(6); //itemSlot2 7
        location = data.get(7); //location 8
    }
    
    public void dataInvParser(ArrayList<String> data)
    {
        int c = 0;
        String arrayVal = "";
        printInv.removeAll(printInv);
        while(c < 17)
        {
            try
            {
                arrayVal = data.get(c);
                String[] values = data.get(c).split(",");
                printInv.add(idNameParser(values[0]) + "| x" + values[1]);
            }
            catch(Exception e)
            {
                printInv.add("");
            }
            c++;
        }
        
    }
    
    public void printInventory(String name)
    {
        dataParser(db.manual_getAllPlayerData(name));
        System.out.println("eee");
        multiplierRanking.put(1, "Common");
        multiplierRanking.put(2, "Uncommon");
        multiplierRanking.put(3, "Rare");
        multiplierRanking.put(4, "Epic");
        multiplierRanking.put(5, "Ultra");
        multiplierRanking.put(6, "Legendary");
        
        int c = 0;
        printInv.removeAll(printInv);
        printInv.addAll(db.manual_getAllPlayerInventory(name));
        
        System.out.println("######################################################################################");
        System.out.println("#                                                  #                                 #");
        System.out.println("# Inventory                                        # Stats                           #");
        System.out.println("#                                                  #                                 #");
        System.out.println("#  ##############################################  # ############################### #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(0)), 42) + " #  # # Energy: " + spacingGen(Integer.toString(energy), 5) + "# HP: " + spacingGen(Integer.toString(hp), 9) + "# #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(1)), 42) + " #  # # Armor : " + spacingGen(Integer.toString(energy), 5) + "# XP: " + spacingGen(Integer.toString(xp), 9) + "# #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(2)), 42) + " #  # ############################### #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(3)), 42) + " #  #                                 #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(4)), 42) + " #  ###################################");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(5)), 42) + " #  #                                 #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(6)), 42) + " #  #  #############################  #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(7)), 42) + " #  #  # " + spacingGen(armorSlot, 26) + "#  #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(8)), 42) + " #  #  #############################  #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(9)), 42) + " #  #              Armor              #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(10)), 42) + " #  #                                 #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(11)), 42) + " #  #  #############################  #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(12)), 42) + " #  #  # " + spacingGen(itemSlot1, 26) + "#  #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(13)), 42) + " #  #  #############################  #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(14)), 42) + " #  #             Left Arm            #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(15)), 42) + " #  #                                 #");
        System.out.println("#  # " + spacingGen(outputName(printInv.get(16)), 42) + " #  #  #############################  #");
        System.out.println("#  ##############################################  #  # " + spacingGen(itemSlot2, 26) + "#  #");
        System.out.println("#  # Coins: " + spacingGen(Integer.toString(coins), 36) + "#  #  #############################  #");
        System.out.println("#  ##############################################  #            Right  Arm           #");
        System.out.println("#                                                  #                                 #");
        System.out.println("######################################################################################");
    }
    
    public String outputName (String data)
    {
        try
        {
            String[] values = data.split(",");
            return idNameParser(values[0]) + " | X" + values[1];
        }
        catch(ArrayIndexOutOfBoundsException exception)
        {
            return "";
        }
    }
    
    public String spacingGen (String word, int length)
    {
        String output = word;
        int c = 0;
        try
        {
            c = word.length();
        }
        catch(Exception e)
        {
            output = "";
        }
        while(c != length)
        {
            output += " ";
            c++;
        }
        return output;
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