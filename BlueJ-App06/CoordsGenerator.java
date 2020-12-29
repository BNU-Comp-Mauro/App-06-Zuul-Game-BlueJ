import java.util.*;
/**
 * Write a description of class CoordsGenerator here.
 *
 * @author Haroon Sadiq
 * @version 0.1
 */
public class CoordsGenerator
{
    public ArrayList<String> coordsArray = new ArrayList<String>();
    
    public ArrayList<String> spiralGen(int xLocation, int yLocation, int range)
    {
        coordsArray.removeAll(coordsArray);
        int length = 1;
        int amountdone = 0;
        int counter = 0;
        coordsArray.add(xLocation + "," + yLocation);
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
            coordsArray.add(xLocation + "," + yLocation);
            counter += +1;
        }
        return coordsArray;
    }
    
    public ArrayList<String> oldlistGen (int range)
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
                targetX += +1;
                rangeCounter2 += +1;
            }
           //
            targetY = targetY - 1;
            targetX = -coordRange;
            rangeCounter += +1;
        }
        return coordsArray;
    }
    
    public ArrayList<String> listGen (int range)
    {
        ArrayList<String> eee = new ArrayList<String>();
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
                eee.add(targetX + "," + targetY);
                targetX += +1;
                rangeCounter2 += +1;
            }
           //
            targetY = targetY - 1;
            targetX = -coordRange;
            rangeCounter += +1;
        }
        return eee;
    }
    
    public ArrayList<String> borderGen(int range)
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
            while(rangeCounter2 < range)
            {
                if(rangeStatus == 1)
                {
                    if(targetX == -coordRange)
                    {
                        coordsArray.add(targetX + "," + targetY); // NW
                    }
                    else if(targetX == coordRange)
                    {
                        
                        coordsArray.add(targetX + "," + targetY); // NE
                    }
                    else
                    {
                        coordsArray.add(targetX + "," + targetY ); // N
                    }
                }
                else if(rangeStatus == 0)
                {
                    if(targetX == -coordRange)
                    {
                        coordsArray.add(targetX + "," + targetY); // W
                    }
                    else if(targetX == coordRange)
                    {
                        coordsArray.add(targetX + "," + targetY); // E
                    }
                }
                else if(rangeStatus == 2)
                {
                    if(targetX == -coordRange)
                    {
                        coordsArray.add(targetX + "," + targetY); //SW
                    }
                    else if(targetX == coordRange)
                    {
                        coordsArray.add(targetX + "," + targetY); // SE
                    }
                    else
                    {
                        coordsArray.add(targetX + "," + targetY); // S
                    }
                }
                targetX += +1;
                rangeCounter2 += +1;
            }
            targetY = targetY - 1;
            targetX = -coordRange;
            rangeCounter += +1;
        }
        return coordsArray;
    }
}
