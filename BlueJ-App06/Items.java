/**
 * Enumeration class Items - write a description of the enum class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Items
{
    W1("stick"), W2("stone"), W3("brick"), W4("spatula"),
    W5("bat"), W6("spear"), W7("sword"), W8("trident"),
    C1("money"), P1("health"),
    P2("energy"), S1("shield"), S2("armor");
    
    private String itemString;
    
    Items(String itemString)
    {
        this.itemString = itemString;
    }
    
    public String toString()
    {
        return itemString;
    }
}