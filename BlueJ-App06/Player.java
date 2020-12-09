import java.util.ArrayList;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private int health;
    private int energy;
    private ArrayList itemList;
    private int attackValue;
    private int defenseValue;
    private String name;
    
    public Player(String name)
    {
        this.name = name;
        health = 100;
        energy = 100;
        attackValue = 0;
        defenseValue = 0;
        
        itemList = new ArrayList();
    }

    public void pickUp(Items item)
    {
        itemList.add(item);
    }
    
    public boolean dropItem(Items item)
    {
        return itemList.remove(item);
    }
    
    /**GET Method Propertie health*/
    public int getHealth(){
        return this.health;
    }//end method getHealth

    /**SET Method Propertie health*/
    public void changeHealth(int amount){
        this.health = this.health + amount;
    }

    /**GET Method Propertie energy*/
    public int getEnergy(){
        return this.energy;
    }

    /**SET Method Propertie energy*/
    public void changeEnergy(int amount){
        this.energy = this.energy + amount;
    }//end method setEnergy

    /**GET Method Propertie itemList*/
    public ArrayList getItemList(){
        return this.itemList;
    }//end method getItemList

    /**GET Method Propertie attackValue*/
    public int getAttackValue(){
        return this.attackValue;
    }//end method getAttackValue

    /**SET Method Propertie attackValue*/
    public void setAttackValue(int attackValue){
        this.attackValue = attackValue;
    }//end method setAttackValue

    /**GET Method Propertie defenseValue*/
    public int getDefenseValue(){
        return this.defenseValue;
    }//end method getDefenseValue

    /**SET Method Propertie defenseValue*/
    public void setDefenseValue(int defenseValue){
        this.defenseValue = defenseValue;
    }//end method setDefenseValue

    //End GetterSetterExtension Source Code
    //!
}
