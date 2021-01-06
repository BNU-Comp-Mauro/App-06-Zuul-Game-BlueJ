import java.util.*;
/**
 * Write a description of class RankSystem here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class RankSystem
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class RankSystem
     */
    public RankSystem()
    {
        Hashtable<Integer, String> ranking = new Hashtable<Integer, String>();
        ranking.put(1, "Common");
        ranking.put(2, "Uncommon");
        ranking.put(3, "Rare");
        ranking.put(4, "Epic");
        ranking.put(5, "Ultra");
        ranking.put(6, "Legendary");
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public int sampleMethod(int y)
    {
        // put your code here
        return x + y;
    }
}
