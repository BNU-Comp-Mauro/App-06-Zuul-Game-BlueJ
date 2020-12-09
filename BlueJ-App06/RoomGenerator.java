import java.util.Random;
/**
 * Write a description of class RoomGenerator here.
 *
 * @author Haroon Sadiq
 * @version (a version number or a date)
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
    
    /**
     * Constructor for objects of class RoomGenerator
     */
    //public RoomGenerator()
    {
        //String filename = menu.getName();
        int counter = 0;
        n = 0;
        e = 0;
        s = 0;
        w = 0;
        x = 0;
        y = 0;
        //db.insertDB("SaveData", filename, "room", "roomName, level, items, position", "'Stronghold', 1, 'map', '0,0'");
        
        while(counter == 24)
        {
            counter +=1;
            
        }
    }
    
    private void northGenerator()
    {
        int movePosition = 0;
        movePosition = (rand.nextInt(3)) + 1;
        n =+ movePosition;
    }
    
    // optimise
    public void bossRoomGenerator()
    {
        boolean gen = rand.nextBoolean();
        int bx = 0;
        int by = 0;
        //x axis
        if(gen == true)
        {
            if(rand.nextBoolean() == true)
            {
                bx = 3;
            }
            else
            {
                bx = -3;
            }
            by = (rand.nextInt(7) - 3);
        }
        //y axis
        else
        {
            if(rand.nextBoolean() == true)
            {
                by = 3;
            }
            else
            {
                by = -3;
            }
            bx = (rand.nextInt(7) - 3);
        }
        System.out.println("x" + bx);
        System.out.println("y" + by);
    }
    
    private void Rooms()
    {
        
    }
    
    private void firstRoom()
    {
        
    }
}
