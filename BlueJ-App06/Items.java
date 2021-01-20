/**
 * Enumeration class Items - write a description of the enum class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Items
{
    W1("stick", 2), W2("stone", 3), W3("brick", 5), W4("spatula", 7),
    W5("bat", 11), W6("spear", 13), W7("sword", 16), W8("trident", 25),
    C1("money", 2), P1("health", 0),
    P2("energy", 0), S1("shield", 0), S2("armor", 0);
    
    private String itemString;
    private int value;
    
    public int getValue()
    {
        return value;
    }
    
    Items(String itemString, int value)
    {
        this.itemString = itemString;
        this.value = value;
    }
    
    public String toString()
    {
        return itemString;
    }
}