/**
 * Enumeration class Items - write a description of the enum class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public enum Items
{
    STICK("stick"), STONE("stone"), BRICK("brick"), SPATULA("spatula"),
    BAT ("bat"), SPEAR("spear"), SWORD("sword"), TRIDENT("trident"),
    MONEY("money"), HEALTH_POTION("health"),
    ENERGY_POTION("energy"), SHIELD("shield"), ARMOR("armor");
    
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