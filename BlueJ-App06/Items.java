/**
 * Enumeration class Items - write a description of the enum class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Items
{
    KEY("key"), POTION("potion"), SWORD("sword"), SHIELD("shield"),
    TRAP("trap");
    
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