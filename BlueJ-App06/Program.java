
/**
 * This class creates an instance of the Game
 * class and then calls on its run method.
 *
 * @author Derek Peacock
 * @version 0.1
 */
public class Program
{
    private static Menu menu;

    /**
     * This class creates and runs an instance of
     * the Game class
     */
    public static void main()
    {
        menu = new Menu();
        menu.run();
    }
}
