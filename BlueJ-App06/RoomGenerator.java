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
    private Menu menu;
    private String roomName;
    public String position = null;
    public int n = 0;
    public int e = 0;
    public int s = 0;
    public int w = 0;
    public int x = 0;
    public int y = 0;
    public String [] genericEnding = {"posa", "hill", "ston", "ley", "reed", "green", "vagh", "nagh", "kinge", "forth", "dow", "deen", "stown"};
    public String [] villageEnding = {"ville", "topia"};
    public String [] hostileEnding = {"ghast", "roth", "nthor", "mancer", "comb", "rok"};
    public String [] vowel = {"a", "e", "i", "o", "u"};
    ArrayList<String> xyRoom = new ArrayList<String>();
    
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
            else if(counter == 0)
            {
                db.insertDB("SaveData", filename, "room", "roomName, roomType, items, visitCounter, north, east, south, west, rank, x, y", "'Stronghold', 'start', 1, 'map', true, true, true, true, " + rank +", 0, 0");
            }
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
            db.insertDB("SaveData", filename, "room", "roomName, roomType, items, visitCounter, north, east, south, west, rank, x, y", "'" + roomName +"', '" + roomType +"', " + items +", 0, true, true, true, true, " + rank +", " + x +", " + y +"");
            counter += +1;
            c += +1;
        }
    }
    
    public void test2(int xLocation, int yLocation, int range)
    {
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
    public void bossRoomGenerator(int range)
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
        System.out.println("x" + bx);
        System.out.println("y" + by);
    }
    
}
