import java.util.*;
/**
 * Write a description of class MapGenerator here.
 *
 * @author Haroon Sadiq
 * @version 0.1
 */
public class MapGenerator
{
    Random rand = new Random();
    private Game game;
    private DatabaseManager db;
    private String roomName;
    public String name = "";
    public String position = null;
    
    public int [] order = {4, 3, 2, 5, 0, 1, 6 ,7 ,8};
    ArrayList<String> map = new ArrayList<String>();
    ArrayList<String> xyRoom = new ArrayList<String>();
    ArrayList<String> line1 = new ArrayList<String>();
    ArrayList<String> line2 = new ArrayList<String>();
    ArrayList<String> line3 = new ArrayList<String>();
    ArrayList<String> line4 = new ArrayList<String>();
    ArrayList<String> line5 = new ArrayList<String>();
    ArrayList<String> line6 = new ArrayList<String>();
    ArrayList<String> line7 = new ArrayList<String>();
    ArrayList<String> line8 = new ArrayList<String>();
    ArrayList<String> line9 = new ArrayList<String>();
    ArrayList<Integer> listOrder = new ArrayList<Integer>();

   /**
     * Constructor for objects of class MapGenerator
     */
    public void MapGenerator(String filename, int xLocation, int yLocation, int range)
    {
        map.removeAll(map);
        test2(xLocation, yLocation, range);
        int counter = 0;
        int coordRange = (range / 2);
        int targetY = coordRange;
        int targetX = -coordRange;
        int rangeCounter = 0;
        int rangeCounter2 = 0;
        String name = "";
        
        String linePrint1 = "";
        String linePrint2 = "";
        String linePrint3 = "";
        String linePrint4 = "";
        String linePrint5 = "";
        String linePrint6 = "";
        String linePrint7 = "";
        String linePrint8 = "";
        String linePrint9 = "";
        db.manual_connectSaveDataDB(filename, false);
        while(rangeCounter < range)
        {
            rangeCounter2 = 0;
            
            while(rangeCounter2 < range)
            {
                name = db.manual_getDataDB("roomName", "room", "x = " + targetX + " AND y = " + targetY);
                line1.add(syntaxGen(1, checkDirection(filename, targetX , targetY, "north"), 0, name, targetX , targetY));
                line2.add(syntaxGen(2, 0, 0, name, targetX , targetY));
                line3.add(syntaxGen(3, 0, 0, name, targetX , targetY));
                line4.add(syntaxGen(4, 0, 0, name, targetX , targetY));
                line5.add(syntaxGen(5, checkDirection(filename, targetX , targetY, "west"), checkDirection(filename, targetX , targetY, "east"), name, targetX , targetY));
                line6.add(syntaxGen(6, 0, 0, name, targetX , targetY));
                line7.add(syntaxGen(7, 0, 0, name, targetX , targetY));
                line8.add(syntaxGen(8, 0, 0, name, targetX , targetY));
                line9.add(syntaxGen(9, checkDirection(filename, targetX , targetY, "south"), 0, name, targetX , targetY));
                linePrint1 += line1.get(rangeCounter2);
                linePrint2 += line2.get(rangeCounter2);
                linePrint3 += line3.get(rangeCounter2);
                linePrint4 += line4.get(rangeCounter2);
                linePrint5 += line5.get(rangeCounter2);
                linePrint6 += line6.get(rangeCounter2);
                linePrint7 += line7.get(rangeCounter2);
                linePrint8 += line8.get(rangeCounter2);
                linePrint9 += line9.get(rangeCounter2);
                targetX += +1;
                rangeCounter2 += +1;
            }
            System.out.println(linePrint1);
            System.out.println(linePrint2);
            System.out.println(linePrint3);
            System.out.println(linePrint4);
            System.out.println(linePrint5);
            System.out.println(linePrint6);
            System.out.println(linePrint7);
            System.out.println(linePrint8);
            System.out.println(linePrint9);
            linePrint1 = "";
            linePrint2 = "";
            linePrint3 = "";
            linePrint4 = "";
            linePrint5 = "";
            linePrint6 = "";
            linePrint7 = "";
            linePrint8 = "";
            linePrint9 = "";
            line1.removeAll(line1);
            line2.removeAll(line2);
            line3.removeAll(line3);
            line4.removeAll(line4);
            line5.removeAll(line5);
            line6.removeAll(line6);
            line7.removeAll(line7);
            line8.removeAll(line8);
            line9.removeAll(line9);
            targetY = targetY - 1;
            targetX = -coordRange;
            rangeCounter += +1;
        }
        
        
        
        //while(rangeCounter < range)
        {
            //rangeCounter2 = 0;
            //while(rangeCounter2 < range)
            {
                //idPosition = Integer.parseInt(db.getData(filename, "ID", "room", "x = " + targetX + " AND y = " + targetY));
                //listOrder.add(idPosition - 1);
                //targetX += +1;
                //rangeCounter2 += +1;
            }
            //targetY = targetY - 1;
            //targetX = coordRange;
            //rangeCounter += +1;
        }
        System.out.println(listOrder);
        for(String i : xyRoom)
        {
            String[] xy = i.split(",");
            int mx = Integer.parseInt(xy[0]);
            int my = Integer.parseInt(xy[1]);
            System.out.println(mx + "," + my);
            name = db.getData(filename, "roomName", "room", "x = " + mx + " AND y = " + my);
            int visitCounter = Integer.parseInt(db.getData(filename, "visitCounter", "room", "x = " + mx + " AND y = " + my));
            System.out.println(visitCounter);
            if(visitCounter == 5)
            {
                line1.add(syntaxGen(10, 0, 0, name, mx , my));
                line2.add(syntaxGen(10, 0, 0, name, mx , my));
                line3.add(syntaxGen(10, 0, 0, name, mx , my));
                line4.add(syntaxGen(10, 0, 0, name, mx , my));
                line5.add(syntaxGen(10, 0, 0, name, mx , my));
                line6.add(syntaxGen(10, 0, 0, name, mx , my));
                line7.add(syntaxGen(10, 0, 0, name, mx , my));
                line8.add(syntaxGen(10, 0, 0, name, mx , my));
                line9.add(syntaxGen(10, 0, 0, name, mx , my));
            }
            else
            {
                line1.add(syntaxGen(1, checkDirection(filename, mx , my, "north"), 0, name, mx , my));
                line2.add(syntaxGen(2, 0, 0, name, mx , my));
                line3.add(syntaxGen(3, 0, 0, name, mx , my));
                line4.add(syntaxGen(4, 0, 0, name, mx , my));
                line5.add(syntaxGen(5, checkDirection(filename, mx , my, "west"), checkDirection(filename, mx , my, "east"), name, mx , my));
                line6.add(syntaxGen(6, 0, 0, name, mx , my));
                line7.add(syntaxGen(7, 0, 0, name, mx , my));
                line8.add(syntaxGen(8, 0, 0, name, mx , my));
                line9.add(syntaxGen(9, checkDirection(filename, mx , my, "south"), 0, name, mx , my));
            }
            name = "";
        }
        System.out.println(xyRoom);

        counter = 0;
        for(int p : listOrder)
        {
            if(counter == range)
            {
                counter = 0;
                System.out.println(linePrint1);
                System.out.println(linePrint2);
                System.out.println(linePrint3);
                System.out.println(linePrint4);
                System.out.println(linePrint5);
                System.out.println(linePrint6);
                System.out.println(linePrint7);
                System.out.println(linePrint8);
                System.out.println(linePrint9);
                linePrint1 = "";
                linePrint2 = "";
                linePrint3 = "";
                linePrint4 = "";
                linePrint5 = "";
                linePrint6 = "";
                linePrint7 = "";
                linePrint8 = "";
                linePrint9 = "";
            }
            try
            {
                linePrint1 += line1.get(p);
                linePrint2 += line2.get(p);
                linePrint3 += line3.get(p);
                linePrint4 += line4.get(p);
                linePrint5 += line5.get(p);
                linePrint6 += line6.get(p);
                linePrint7 += line7.get(p);
                linePrint8 += line8.get(p);
                linePrint9 += line9.get(p);
            }
            catch(Exception e)
            {
            }
            counter += +1;
        }
        System.out.println(linePrint1);
        System.out.println(linePrint2);
        System.out.println(linePrint3);
        System.out.println(linePrint4);
        System.out.println(linePrint5);
        System.out.println(linePrint6);
        System.out.println(linePrint7);
        System.out.println(linePrint8);
        System.out.println(linePrint9);
        db.manual_closeDB();
    }
    
    public void mazeGen(int range)
    {
        //coordsArray.removeAll(coordsArray);
        int coordRange = (range / 2);
        int targetY = coordRange;
        int targetX = -coordRange;
        int rangeCounter = 0;
        int rangeCounter2 = 0;
        int rangeStatus = 0;
        while(rangeCounter < range)
        {
            rangeCounter2 = 0;
            while(rangeCounter2 < range)
            {
                //coordsArray.add(targetX + "," + targetY);
                targetX += +1;
                rangeCounter2 += +1;
            }
           //
            targetY = targetY - 1;
            targetX = -coordRange;
            rangeCounter += +1;
        }
        //return coordsArray;

    }    
    public int checkDirection(String filename, int x , int y, String direction)
    {
        int check = 0;
        try
        {
            check = Integer.parseInt(db.manual_getDataDB(direction, "room", "x = " + x + " AND y = " + y));
        }
        catch(Exception e)
        {
            check = 0;
        }
        return check;
    }
    
    public String syntaxGen(int lineNumber, int tunnel, int tunnel2, String name, int genX, int genY)
    {
        int len = 12;
        int c = 0;
        String genStringX = Integer.toString(genX);
        String genStringY = Integer.toString(genY);
        String fill = "";
        if(lineNumber == 1)
        {
            return "        " + syntaxGen1(tunnel) + "        ";
        }
        else if(lineNumber == 2)
        {
            return " ############### ";
        }
        else if(lineNumber == 3)
        {
            try
            {
                len = genStringX.length();
                while(true)
                {
                    if(c == 10 - len)
                    {
                        break;
                    }
                    fill += " ";
                    c += 1;
                }
            }
            catch(Exception e)
            {
                name = name;
            }
            return " # x:" + genStringX + "" + fill + "# ";
        }
        else if(lineNumber == 4)
        {
            try
            {
                len = genStringY.length();
                while(true)
                {
                    if(c == 10 - len)
                    {
                        break;
                    }
                    fill += " ";
                    c += 1;
                }
            }
            catch(Exception e)
            {
                name = name;
            }
            return " # y:" + genStringY + "" + fill + "# ";
        }
        else if(lineNumber == 5)
        {
            return syntaxGen1(tunnel) + "#             #" + syntaxGen1(tunnel);
        }
        else if(lineNumber == 6)
        {
            return " #             # ";
        }
        else if(lineNumber == 7)
        {
            try
            {
                len = name.length();
                while(true)
                {
                    if(c == 11 - len)
                    {
                        break;
                    }
                    fill += " ";
                    c += 1;
                }
            }
            catch(Exception e)
            {
                name = name;
            }
            return " # ●" + name + "" + fill + "# ";
        }
        else if(lineNumber == 8)
        {
            return " ############### ";
        }
        else if(lineNumber == 9)
        {
            return "        " + syntaxGen1(tunnel) + "        ";
        }
        else
        {
            return "#################";
        }
    }
    public String syntaxGen1(int tunnel)
    {
        if(tunnel == 1)
        {
            return "#";
        }
        else
        {
            return " ";
        }
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
}
