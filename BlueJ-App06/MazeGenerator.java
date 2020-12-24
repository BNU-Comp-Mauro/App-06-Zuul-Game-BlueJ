import java.util.*;
/**
 * Write a description of class MazeGenerator here.
 *
 * @author Haroon Sadiq
 * @version 0.1
 */
public class MazeGenerator
{
    private DatabaseManager db;
    private CoordsGenerator coords;
    private String roomName;
    ArrayList<String> cellStack = new ArrayList<String>();
    public int totalCells = 0;
    public int currentCells = 0;
    public int visitedCells = 1;
    public boolean found = false;
   /**
     * Constructor for objects of class MazeGenerator
     */
    public void MazeGenerator(String filename, int xLocation, int yLocation, int range)
    {
        int coordRange = (range / 2);
        int targetY = coordRange;
        int targetX = -coordRange;
        cellStack.addAll(coords.borderGen(range + 2));
        while(visitedCells < totalCells)
        {
            
        }
    }
}
