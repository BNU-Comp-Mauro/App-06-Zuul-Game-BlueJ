
/**
 * Holds Menu functions
 *
 * @author Haroon Sadiq
 * @version 0.1
 */
public class Menu
{
    // Allows reader to be called directly against the class Menu
    private static InputReader reader = new InputReader();
    
    /**
     * Scans user input and outputs programmed choices 
     */
    public static String getMenuChoice(String [] choices)
    {
        boolean finished = false;
        String choice = null;
        
        while(!finished)
        {
            printChoices(choices);
            choice = reader.getInput().toLowerCase();
            
            
            finished = checkIfValid(choices, choice);
            
            if(!finished)
            {
                System.out.println("|✘| Invalid choice");
            }    
        }
        
        return choice;
    }
    
    /**
     * Checks if inputted data matches choices array
     */
    private static boolean checkIfValid(String [] choices, String choice)
    { 
        for(String validChoice : choices)
        {
            validChoice = validChoice.toLowerCase();
            if(validChoice.startsWith(choice))
                return true;
        }
        return false;
    }
    
    
    /**
     * Prints array of choices
     */
    private static void printChoices(String [] choices)
    {
        System.out.println("Enter the first word of the choice to run: \n");
        for(String choice: choices)
        {
            System.out.println("   " + choice);
        }
        System.out.println("==============================================");
    }
}

