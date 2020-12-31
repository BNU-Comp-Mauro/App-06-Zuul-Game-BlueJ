import java.util.*;
/**
 * Write a description of class e here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class e
{
    private DatabaseManager db;
    public MazeGenerator mg;
    
    public void mazeOutput(String filename, int range)
    {
        int coordRange = (range / 2);
        int targetY = coordRange;
        int targetX = -coordRange;
        int rangeCounter = 0;
        int rangeCounter2 = 0;
        String id = "";
        
        String north = "";
        String east = "";
        String south = "";
        String west = "";
        String name = "";
        
        int c = 0;
        
        
        String l1 = "";
        String l2 = "";
        String l3 = "";
        String l4 = "";
        String l5 = "";
        String l6 = "";
        String l7 = "";
        String l8 = "";

        ArrayList<String> outputArray = new ArrayList<String>();
        
        Hashtable<String, String> tunnelTranslater = new Hashtable<String, String>();
        tunnelTranslater.put("1", "#");
        tunnelTranslater.put("0", " ");
        
        db.manual_connectSaveDataDB(filename, false);
        while(rangeCounter < range)
        {
            rangeCounter2 = 0;
            while(rangeCounter2 < range)
            {
                id = db.manual_getDataDB("ID", "room", "x = " + targetX + " AND y = " + targetY);
                north = tunnelTranslater.get(db.manual_getDataDB("north", "room", "ID = " + id));
                east = tunnelTranslater.get(db.manual_getDataDB("east", "room", "ID = " + id));
                south = tunnelTranslater.get(db.manual_getDataDB("south", "room", "ID = " + id));
                west = tunnelTranslater.get(db.manual_getDataDB("west", "room", "ID = " + id));
                name = db.manual_getDataDB("roomName", "room", "ID = " + id);
                
                l1 += "      " + north + north + north + north + "      ";
                l2 += " ############## ";
                l3 += " # x:" + spacingGen(Integer.toString(targetX), 9) + "# ";
                l4 += west + "# y:" + spacingGen(Integer.toString(targetY), 9) + "#" + east;
                l5 += west + "# __________ #" + east;
                l6 += " # " + spacingGen(name, 11) + "# ";
                l7 += " ############## ";
                l8 += "      " + south + south + south + south + "      ";
                targetX++;
                rangeCounter2++;
            }
            System.out.println(l1);
            System.out.println(l2);
            System.out.println(l3);
            System.out.println(l4);
            System.out.println(l5);
            System.out.println(l6);
            System.out.println(l7);
            System.out.println(l8);
            l1 = "";
            l2 = "";
            l3 = "";
            l4 = "";
            l5 = "";
            l6 = "";
            l7 = "";
            l8 = "";
            targetY = targetY - 1;
            targetX = -coordRange;
            rangeCounter++;
        }
        db.manual_closeDB();
    }
    
    public String spacingGen (String word, int length)
    {
        String output = word;
        int len = word.length();
        int c = len;
        while(c != length)
        {
            output += " ";
            c++;
        }
        return output;
    }

    public void mazeOutput1(String filename, int range)
    {
        Hashtable<String, String> tunnelTranslater = new Hashtable<String, String>();
        tunnelTranslater.put("h1", "####");
        tunnelTranslater.put("v1", "#");
        tunnelTranslater.put("h0", "    ");
        tunnelTranslater.put("v0", " ");
        int coordRange = (range / 2);
        int targetY = coordRange;
        int targetX = -coordRange;
        int rangeCounter = 0;
        int rangeCounter2 = 0;
        String name = "";
        String north = "";
        String east = "";
        String south = "";
        String west = "";;
        
        int c = 0;
        int id = 0;
        
        String l1 = "";
        String l2 = "";
        String l3 = "";
        String l4 = "";
        String l5 = "";
        String l6 = "";
        String l7 = "";
        String l8 = "";
        
        db.manual_connectSaveDataDB(filename, false);
        while(rangeCounter < range)
        {
            rangeCounter2 = 0;
            while(rangeCounter2 < range)
            {
                id = Integer.parseInt(db.manual_getDataDB("ID", "room", "x = " + targetX + " AND y = " + targetY));
                north = "h" + tunnelTranslater.get(db.manual_getDataDB("north", "room", "ID = " + id));
                east = "v" + tunnelTranslater.get(db.manual_getDataDB("east", "room", "ID = " + id));
                south = "h" + tunnelTranslater.get(db.manual_getDataDB("south", "room", "ID = " + id));
                west = "v" + tunnelTranslater.get(db.manual_getDataDB("west", "room", "ID = " + id));
                name = db.manual_getDataDB("roomName", "room", "ID = " + id);
                
                l1 += "      " + north + "     ";
                l2 += " ##############";
                l3 += " # x:" + spacingGen(Integer.toString(targetX), 9) + "#";
                l4 += west + "# y:" + spacingGen(Integer.toString(targetY), 9) + "#";
                l5 += west + "# __________ #";
                l6 += " # " + spacingGen(name, 11) + "#";
                l7 += " ##############";
                l8 += "      " + south + "     ";
                
                if(rangeCounter2 == (range -1))
                {
                    l1 += " ";
                    l2 += " ";
                    l3 += " ";
                    l4 += east;
                    l5 += east;
                    l6 += " ";
                    l7 += " ";
                    l8 += " ";
                }
                targetX++;
                rangeCounter2++;
            }
            rangeCounter++;
            System.out.println(l1);
            System.out.println(l2);
            if(rangeCounter == range)
            {
                System.out.println(l7);
                System.out.println(l8);
            }
            l1 = "";
            l2 = "";
            l3 = "";
            l4 = "";
            l5 = "";
            l6 = "";
            l7 = "";
            l8 = "";
            targetY = targetY - 1;
            targetX = -coordRange;
            
        }
        db.manual_closeDB();
    }
    
}
