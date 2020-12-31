
import java.util.*;
import java.util.concurrent.TimeUnit;
/**
 * Write a description of class RoomGenerator here.
 *
 * @author Haroon Sadiq
 * @version 0.1
 */
public class RoomGenerator
{
    Random rand = new Random();
    private Game game;
    private DatabaseManager db;
    private MazeGenerator mg;
    private String roomName;
    public String position = null;
    public int x = 0;
    public int y = 0;
    public String [] genericEnding = {"posa", "hill", "ston", "ley", "reed", "green", "vagh", "nagh", "kinge", "forth", "dow", "deen", "stown"};
    public String [] villageEnding = {"ville", "topia"};
    public String [] hostileEnding = {"ghast", "roth", "nthor", "mancer", "comb", "rok"};
    public String [] vowel = {"a", "e", "i", "o", "u"};
    ArrayList<String> xyRoom = new ArrayList<String>();
    ArrayList<Integer> listOrder = new ArrayList<Integer>();
    ArrayList<String> tunnelList = new ArrayList<String>();
    /**
     * Constructor for objects of class RoomGenerator
     */
    public RoomGenerator(String filename, int range)
    {
        int counter = 0;
        int c = 0;
        int rank = 1;
        int boundCheck = 3;
        if(range % 2 == 0)
        {
            System.out.println("Converted range to odd number");
            range += +1;
        }
        else if(range < 3)
        {
            System.out.println("Range is now 3 (min)");
            range = 3;
        }
        int rangemax = (range - 3) / 2;
        System.out.println(rangemax);
        x = 0;
        y = 0;
        String roomName = null;
        String roomType = null;
        test2(0, 0, range);
        int items = 0;
        db.manual_connectSaveDataDB(filename, false);
        for(String i : xyRoom)
        {
            String[] xy = i.split(",");
            x = Integer.parseInt(xy[0]);
            y = Integer.parseInt(xy[1]);
            System.out.println(c);
            if(counter == (boundCheck*boundCheck))
            {
                rank += +1;
                boundCheck += +2;
            }
            if(counter == 0)
            {
                db.manual_insertDB("room", "roomName, roomType, items, visitCounter, north, east, south, west, rank, x, y, found", "'Stronghold', 'start', 1, 1, true, true, true, true, " + rank +", 0, 0, 'true'");
            }
            else
            {
                if(rank == 1)
                {
                    int village = rand.nextInt(6);//1 in 5 chance for a village
                    if(village == 5)
                    {
                        roomType = "village";
                        roomName = villageNameGen();
                    }
                    else
                    {
                        roomType = "general";
                        roomName = genericNameGen();
                    }
                    items = rand.nextInt(3);
                }
                else if(rank == 2)
                {
                    int village = rand.nextInt(5);//1 in 4 chance for a village
                    int hostile = rand.nextInt(5);//1 in 4 chance for a hostile
                    if(village == 4)
                    {
                        roomType = "village";
                        roomName = villageNameGen();
                    }
                    else if(hostile == 4)
                    {
                        roomType = "hostile";
                        roomName = hostileNameGen();
                    }
                    else
                    {
                        roomType = "general";
                        roomName = genericNameGen();
                    }
                    items = rand.nextInt(4);
                }
                else if(rank == 3)
                {
                    int village = rand.nextInt(11);//1 in 10 chance for a village
                    int hostile = rand.nextInt(4);//1 in 3 chance for a hostile
                    if(village == 4)
                    {
                        roomType = "village";
                        roomName = villageNameGen();
                    }
                    else if(hostile == 3)
                    {
                        roomType = "hostile";
                        roomName = hostileNameGen();
                    }
                    else
                    {
                        roomType = "general";
                        roomName = genericNameGen();
                    }
                    items = rand.nextInt(5);
                }
                else if(rank >= 4)
                {
                    int village = rand.nextInt(16);//1 in 15 chance for a village
                    int hostile = rand.nextInt(2);//1 in 3 chance for a hostile
                    if(village == 15)
                    {
                        roomType = "village";
                        roomName = villageNameGen();
                    }
                    else if(hostile == 1)
                    {
                        roomType = "hostile";
                        roomName = hostileNameGen();
                    }
                    else
                    {
                        roomType = "general";
                        roomName = genericNameGen();
                    }
                    items = rand.nextInt(6);
                }
                tunnelRand();
                db.manual_insertDB("room", "roomName, roomType, items, visitCounter, rank, x, y, found", "'" + roomName +"', '" + roomType +"', " + items +", 0, " + rank +", " + x +", " + y +", 'false'");
            }
            counter += +1;
            c += +1;
            
        }
        db.manual_closeDB();
        mg = new MazeGenerator();
        mg.mazeDB(filename, range, "room", 0);
    }
    
    public void tunnelRand()
    {
        ArrayList<Integer> intList = new ArrayList<Integer>();
        tunnelList.removeAll(tunnelList);
        intList.removeAll(intList);
        int random = 0;
        for(int i = 1; i < 5;i++)
        {
            intList.add(i);
            tunnelList.add("0");
        }
        for(int i = 1; i < 5;i++)
        {
            if(i == 1)
            {
                random = rand.nextInt(tunnelList.size());
                tunnelList.set(random, "1");
                intList.remove(random);
            }
            else
            {
                random = rand.nextInt(i);
                if(random == 0)
                {
                    tunnelList.set(rand.nextInt(tunnelList.size()), "1");
                }
            }
        }
        System.out.println(tunnelList);
    }
    
    public void tunnelLinker(String filename, int range)
    {
        int id = 0;
        int coordRange = (range / 2);
        int targetY = coordRange;
        int targetX = -coordRange;
        int rangeCounter = 0;
        int rangeCounter2 = 0;
        String north = "0";
        String east = "0";
        String south = "0";
        String west = "0";
        String n = "0";
        String ea = "0";
        String s = "0";
        String w = "0";
        while(rangeCounter < range)
        {
            rangeCounter2 = 0;
            db.manual_connectSaveDataDB(filename, false);
            while(rangeCounter2 < range)
            {
                id = Integer.parseInt(db.manual_getDataDB("ID", "room", "x = " + targetX + " AND y = " + (targetY)));
                try
                {  
                    db.manual_updateMultiDB("south = " + db.manual_getDataDB("north", "room", "ID = " + id), "room", "x = " + targetX + " AND y = " + (targetY + 1));
                    north = db.manual_getDataDB("south", "room", "x = " + targetX + " AND y = " + (targetY + 1)); //north
                }
                catch (Exception e)
                {
                    north = "0";
                }
                
                try
                {  
                    db.manual_updateMultiDB("west = " + db.manual_getDataDB("east", "room", "ID = " + id), "room", "x = " + (targetX +1) + " AND y = " + targetY);
                    east = db.manual_getDataDB("west", "room", "x = " + (targetX +1) + " AND y = " + targetY); //east
                }
                catch (Exception e)
                {
                    east = "0";
                }
                
                try
                {
                    db.manual_updateMultiDB("east = " + db.manual_getDataDB("west", "room", "ID = " + id), "room", "x = " + (targetX -1) + " AND y = " + targetY);
                    west = db.manual_getDataDB("east", "room", "x = " + (targetX -1) + " AND y = " + targetY); //west
                }
                catch (Exception e)
                {
                    west = "0";
                }
                
                try
                {
                    db.manual_updateMultiDB("north = " + db.manual_getDataDB("south", "room", "ID = " + id), "room", "x = " + targetX + " AND y = " + (targetY - 1));
                    south = db.manual_getDataDB("north", "room", "x = " + targetX + " AND y = " + (targetY - 1)); //south
                }
                catch (Exception e)
                {
                    south = "0";
                }
                db.manual_updateMultiDB("north = " + north + ", east = " + east + ", south = " + south + ", west = " + west, "room", "x = " + targetX + " AND y = " + targetY);
                targetX += +1;
                rangeCounter2 += +1;
            }
            targetY = targetY - 1;
            targetX = coordRange;
            rangeCounter += +1;
        }
        db.manual_closeDB();
    }
    
    
    public void test2(int xLocation, int yLocation, int range)
    {
        xyRoom.removeAll(xyRoom);
        int length = 1;
        int amountdone = 0;
        int counter = 0;
        xyRoom.add(xLocation + "," + yLocation);
        int gen = range * range;
        for (int n = 1; n < gen; n++)
        {
            if(amountdone == length * 2)
            {
                amountdone = 0;
                length += +1;
            }
            if(amountdone >= length)
            {
                if(length % 2 == 0)
                {
                    yLocation += -1;
                }
                else
                {
                    yLocation += +1;
                }
            }
            else
            {
                if(length % 2 == 0)
                {
                    xLocation += -1;
                }
                else
                {
                    xLocation += +1;
                }
            }
            amountdone += +1;
            xyRoom.add(xLocation + "," + yLocation);
            counter += +1;
        }
        System.out.println(xyRoom);
    }
    
    private String wordGen()
    {
        char a = (char) (rand.nextInt(26) + 'A');
        String b = vowel[rand.nextInt(5)];
        String c = vowel[rand.nextInt(5)];
        return a + b + c;
    }
    
    public String villageNameGen()
    {
        int size = villageEnding.length;
        return wordGen() + villageEnding[rand.nextInt(size)];
    }
    
    public String hostileNameGen()
    {
        int size = hostileEnding.length;
        return wordGen() + hostileEnding[rand.nextInt(size)];
    }
    
    public String genericNameGen()
    {
        int size = genericEnding.length;
        return wordGen() + genericEnding[rand.nextInt(size)];
    }
    
    
    // optimise
    public void outerRangeGen(String filename, int range)
    {
        boolean gen = rand.nextBoolean();
        int bx = 0;
        int by = 0;
        //x axis
        if(gen == true)
        {
            if(rand.nextBoolean() == true)
            {
                bx = range;
            }
            else
            {
                bx = -range;
            }
            by = (rand.nextInt(7) - 3);
        }
        //y axis
        else
        {
            if(rand.nextBoolean() == true)
            {
                by = range;
            }
            else
            {
                by = -range;
            }
            bx = (rand.nextInt((range*2)+1) - range);
        }
        
      
    }
    
    public void eee(String filename, int range)
    {
        int coordRange = (range / 2);
        int targetY = coordRange;
        int targetX = -coordRange;
        int rangeCounter = 0;
        int rangeCounter2 = 0;
        String idPosition = "";
        int rangeStatus = 0;
        while(rangeCounter < range)
        {
            rangeCounter2 = 0;
            if(rangeCounter == 0)
            {
                rangeStatus = 1;
            }
            else if(rangeCounter > range -2)
            {
                rangeStatus = 2;
            }
            else
            {
                rangeStatus = 0;
            }
            db.connectDB("SaveData", filename, false);
            while(rangeCounter2 < range)
            {
                if(rangeStatus == 1)
                {
                    if(targetX == -coordRange)
                    {
                        db.updateDB(filename, "north", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        db.updateDB(filename, "west", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        System.out.println(targetX + "," + targetY+ " NW");
                    }
                    else if(targetX == coordRange)
                    {
                        db.updateDB(filename, "north", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        db.updateDB(filename, "east", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        System.out.println(targetX + "," + targetY + " NE");
                    }
                    else
                    {
                        db.updateDB(filename, "north", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        System.out.println(targetX + "," + targetY + " N");
                    }
                }
                else if(rangeStatus == 0)
                {
                    if(targetX == -coordRange)
                    {
                        db.updateDB(filename, "west", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        System.out.println(targetX + "," + targetY+ " W");
                    }
                    else if(targetX == coordRange)
                    {
                        db.updateDB(filename, "east", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        System.out.println(targetX + "," + targetY+ " E");
                    }
                }
                else if(rangeStatus == 2)
                {
                    if(targetX == -coordRange)
                    {
                        db.updateDB(filename, "south", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        db.updateDB(filename, "west", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        System.out.println(targetX + "," + targetY+ " SW");
                    }
                    else if(targetX == coordRange)
                    {
                        db.updateDB(filename, "south", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        db.updateDB(filename, "east", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        System.out.println(targetX + "," + targetY+ " SE");
                    }
                    else
                    {
                        db.updateDB(filename, "south", "room", "x = " + targetX + " AND y = " + targetY, "0", true);
                        System.out.println(targetX + "," + targetY+ " S");
                    }
                }
                //idPosition = (targetX + "," + targetY);
                //listOrder.add(idPosition);
                targetX += +1;
                rangeCounter2 += +1;
            }
            targetY = targetY - 1;
            targetX = -coordRange;
            rangeCounter += +1;
        }
    }
    
    public void mazeGen(String filename, int range)
    {
        test2(0, 0, range);
        ArrayList<String> xyRoomBorderMax = new ArrayList<String>(xyRoom);
        test2(0, 0, range -2);
        ArrayList<String> xyRoomBorderMin = new ArrayList<String>(xyRoom);
        System.out.println("min" + xyRoomBorderMin);
        System.out.println("max" + xyRoomBorderMax);
        xyRoomBorderMax.removeAll(xyRoomBorderMin);
        System.out.println(xyRoomBorderMax);
        int random = rand.nextInt(4) + 1;
        x = 0;
        y = 0;
        int north = 0;
        int east = 0;
        int south = 0;
        int west = 0;
        for(String i : xyRoomBorderMax)
        {
            String[] xy = i.split(",");
            x = Integer.parseInt(xy[0]);
            y = Integer.parseInt(xy[1]);
            //top left
            if(-x == y)
            {
                north = 0;
                west = 0;
                db.updateDB(filename, "north", "room", "x = " + x + " AND y = " + y, Integer.toString(north), true);
                db.updateDB(filename, "west", "room", "x = " + x + " AND y = " + y, Integer.toString(west), true);
            }
            //top right
            else if(x == y)
            {
                north = 0;
                east = 0;
                db.updateDB(filename, "north", "room", "x = " + x + " AND y = " + y, Integer.toString(north), true);
                db.updateDB(filename, "east", "room", "x = " + x + " AND y = " + y, Integer.toString(east), true);
            }
            //bottom right
            else if(-x == -y)
            {
                south = 0;
                west = 0;
                db.updateDB(filename, "south", "room", "x = " + x + " AND y = " + y, Integer.toString(south), true);
                db.updateDB(filename, "west", "room", "x = " + x + " AND y = " + y, Integer.toString(west), true);
            }
            //bottom left
            else if(x == -y)
            {
                south = 0;
                east = 0;
                db.updateDB(filename, "south", "room", "x = " + x + " AND y = " + y, Integer.toString(south), true);
                db.updateDB(filename, "east", "room", "x = " + x + " AND y = " + y, Integer.toString(east), true);
            }
            else if(x == 3)
            {
                east = 0;
                db.updateDB(filename, "east", "room", "x = " + x + " AND y = " + y, Integer.toString(east), true);
            }
            else if(x == -3)
            {
                west = 0;
                db.updateDB(filename, "west", "room", "x = " + x + " AND y = " + y, Integer.toString(west), true);
            }
            else if(y == 3)
            {
                north = 0;
                db.updateDB(filename, "north", "room", "x = " + x + " AND y = " + y, Integer.toString(north), true);
            }
            else if(y == -3)
            {
                south = 0;
                db.updateDB(filename, "south", "room", "x = " + x + " AND y = " + y, Integer.toString(south), true);
            }
        }
        //north
        if(random == 1)
        {
            y += +1;
            north =1;
        }
        //east
        else if(random == 2)
        {
            x += +1;
            east = 1;
        }
        //south
        else if(random == 3)
        {
            y += -1;
            south = 1;
        }
        //west
        else if(random == 4)
        {
            x += -1;
            west = 1;
        }
        
    }
}
