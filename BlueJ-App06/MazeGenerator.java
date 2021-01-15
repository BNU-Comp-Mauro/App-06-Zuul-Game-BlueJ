import java.util.*;
/**
 * Write a description of class CopyOfCopyOfCopyOfMazeGenerator here.
 *
 * @author Haroon Sadiq
 * @version 0.1
 */
public class MazeGenerator
{
    Random rand = new Random();
    public DatabaseManager db;
    public CoordsGenerator coords;
    
    public String roomName;
    
    public ArrayList<String> coordsArray = new ArrayList<String>();
    public ArrayList<String> roomLog = new ArrayList<String>();
    
    public ArrayList<String> line1 = new ArrayList<String>();    
    public ArrayList<String> line2 = new ArrayList<String>();  
    public ArrayList<String> line3 = new ArrayList<String>();
    
    public ArrayList<String> cellStack = new ArrayList<String>();
    public ArrayList<String> roomCoords = new ArrayList<String>();
    public ArrayList<String> mazeCoords = new ArrayList<String>();
    
    public String [] directions = {"n", "e", "s", "w"};
    
    public int totalCells = 0;
    public int currentCells = 0;
    public int visitedCells = 0;
    public boolean found = false;
    
    public int targetY = 0;
    public int targetX = 0;
    
    
    
   /**
     * Constructor for objects of class CopyOfCopyOfMazeGenerator
     */
    public ArrayList<String> MazeGenerator(int range)
    {
        ArrayList<String> potentialCoords = new ArrayList<String>();
        
        Hashtable<String, String> reverseDir = new Hashtable<String, String>();
        reverseDir.put("n", "s");
        reverseDir.put("e", "w");
        reverseDir.put("s", "n");
        reverseDir.put("w", "e");
    
        
        roomLog.removeAll(roomLog);
        cellStack.removeAll(cellStack);
        roomCoords.removeAll(roomCoords);
        mazeCoords.removeAll(mazeCoords);
        
        int totalCells = range * range;
        int coordRange = (range / 2);
        
        targetY = 0;
        targetX = 0;
        listGen(range);
        
        roomCoords.addAll(coordsArray);
        
        visitedCells = 1;
        
        String currentCell = coordsArray.get(rand.nextInt(coordsArray.size() -1));
        System.out.println(currentCell);
        System.out.println(coordsArray);

        while(visitedCells < totalCells)
        {
            String[] xy = currentCell.split(",");
            targetX = Integer.parseInt(xy[0]);
            targetY = Integer.parseInt(xy[1]);
            potentialCoords.removeAll(potentialCoords);
            for(String i : directions)
            {
                String value = directionParser(targetX, targetY, i);
                if(roomCoords.contains(value) & !roomLog.contains(value))
                {
                    potentialCoords.add(value + "," + i);
                }
            }
            if(potentialCoords.size() == 0)
            {
                cellStack.remove(cellStack.size() -1);
                currentCell = cellStack.get(cellStack.size() -1);
            }
            else
            {
                int randTunnel = rand.nextInt(potentialCoords.size());
                
                potentialCoords.get(randTunnel);
                
                String[] xyCoords = potentialCoords.get(randTunnel).split(",");
                int tX = Integer.parseInt(xyCoords[0]);
                int tY = Integer.parseInt(xyCoords[1]);
                String dir = xyCoords[2];
                
                mazeCoords.add(targetX + "," + targetY + "," + dir);
                mazeCoords.add(tX + "," + tY + "," + reverseDir.get(dir));
                
                System.out.println(targetX + "," + targetY + "," + dir);
                System.out.println("NEXT: " + tX + "," + tY + "," + reverseDir.get(dir));
                
                cellStack.add(currentCell);
                cellStack.add(tX + "," + tY);
                
                roomLog.add(currentCell);
                
                roomLog.add(tX + "," + tY);
                
                currentCell = tX + "," + tY;
                
                visitedCells++;
            }
        }
        return mazeCoords;
    }
    
    private String directionParser(int x, int y, String direction)
    {
        if(direction == "n")
        {
            y++;
        }
        else if(direction == "e")
        {
            x++;
        }
        else if(direction == "s")
        {
            y += -1;
        }
        else if(direction == "w")
        {
            x += -1;
        }
        return x + "," + y;
    }
    
    public void mazeDB(String filename, int range, String type, int roomID)
    {
        MazeGenerator(range);
        ArrayList<String> usedCoords = new ArrayList<String>();
        
        
        Hashtable<String, String> tunnelTranslate = new Hashtable<String, String>();
        tunnelTranslate.put("n", "north");
        tunnelTranslate.put("e", "east");
        tunnelTranslate.put("s", "south");
        tunnelTranslate.put("w", "west");
        
        db.manual_connectSaveDataDB(filename, false);
        for(String i : mazeCoords)
        {
            String[] xy = i.split(",");
            int mX = Integer.parseInt(xy[0]);
            int mY = Integer.parseInt(xy[1]);
            String tunnel = xy[2];
            
            System.out.println(tunnelTranslate.get(tunnel));
            

            if(type == "room")
            {
                db.manual_updateMultiDB(tunnelTranslate.get(tunnel) + " = 1", "room", "x = " + mX + " AND y = " + mY);
            }
            else
            {
                if(usedCoords.contains(mX + "," + mY) == false)
                {
                    db.manual_insertDB("roomMaze","roomID, xCell, yCell, " + tunnel, roomID + ", " + mX + ", " + mY + ", 1");
                    usedCoords.add(mX + "," + mY);
                }
                else
                {
                    db.manual_updateMultiDB(tunnel + " = 1", "roomMaze", "xCell = " + mX + " AND yCell = " + mY);
                }
            }
        }
        db.manual_closeDB();
        mazeOutput(filename, range);
    }
    
    
    public void mazeOutput(String filename, int range)
    {
        int coordRange = (range / 2);
        targetY = coordRange;
        targetX = -coordRange;
        int rangeCounter = 0;
        int rangeCounter2 = 0;
        
        int north = 0;
        int east = 0;
        int south = 0;
        int west = 0;
        
        int c = 0;
        
        String l1 = "";
        String l2 = "";
        String l3 = "";
        
        db.manual_connectSaveDataDB(filename, false);
        while(rangeCounter < range)
        {
            rangeCounter2 = 0;
            while(rangeCounter2 < range)
            {
                north = Integer.parseInt(db.manual_getDataDB("n", "roomMaze", "xCell = " + targetX + " AND yCell = " + targetY));
                east = Integer.parseInt(db.manual_getDataDB("e", "roomMaze", "xCell = " + targetX + " AND yCell = " + targetY));
                south = Integer.parseInt(db.manual_getDataDB("s", "roomMaze", "xCell = " + targetX + " AND yCell = " + targetY));
                west = Integer.parseInt(db.manual_getDataDB("w", "roomMaze", "xCell = " + targetX + " AND yCell = " + targetY));
                
                l1 += ("+" + tunnelGen("north", north));
                l2 += (tunnelGen("west", west) + "   ");
                l3 += "+" + tunnelGen("south", south);
                
                if(rangeCounter2 == (range -1))
                {
                    l1 += "+";
                    l2 += tunnelGen("east", east);
                    l3 += "+";
                }
                targetX++;
                rangeCounter2++;
            }
            rangeCounter++;
            System.out.println(l1);
            System.out.println(l2);
            if(rangeCounter == range)
            {
                System.out.println(l3);
            }
            l1 = "";
            l2 = "";
            l3 = "";
            targetY = targetY - 1;
            targetX = -coordRange;
            
        }
        db.manual_closeDB();
    }
    
    public String tunnelGen (String layout, int exist)
    {
        if(layout == "east" || layout == "west")
        {
            if(exist == 1)
            {
                return " ";
            }
            else
            {
                return "|";
            }
        }
        else if(layout == "north" || layout == "south")
        {
            if(exist == 1)
            {
                return "   ";
            }
            else
            {
                return "---";
            }
        }
        else
        {
            return "error";
        }
    }
    
    public ArrayList<String> listGen (int range)
    {
        coordsArray.removeAll(coordsArray);
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
                coordsArray.add(targetX + "," + targetY);
                targetX++;
                rangeCounter2++;
            }
           //
            targetY = targetY - 1;
            targetX = -coordRange;
            rangeCounter++;
        }
        return coordsArray;
    }
}