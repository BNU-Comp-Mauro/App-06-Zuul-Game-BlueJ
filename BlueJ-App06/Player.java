import java.util.ArrayList;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    private Items item;

    private int health;
    private int energy;
    private ArrayList itemList;
    private int attackValue;
    private int defenseValue;
    private String name;
    private int score;

    public Player(String name)
    {
        this.item = item;
        this.name = name;
        health = 100;
        energy = 100;
        attackValue = 0;
        defenseValue = 0;
        score = 0;

        itemList = new ArrayList();
    }

    public void grabItem(Items item, Command command)
    {
        CommandWord commandWord = command.getCommandWord();

        if(!command.hasSecondWord()) 
        {
            System.out.println("Grab what?");
            return;
        }

        if(item == null) 
        {
            System.out.println("That item isn't in this room!");
        }
        else 
        {
            itemList.add(item);
        }
        
        if(item == Items.SWORD)
        {
            attackValue = 100;
        }
        else if(item == Items.SHIELD)
        {
            defenseValue = 100;
        }
        else if(item == Items.POTION)
        {
            if(health > 51 && health <= 100)
            {
                health = 100;
            }
            else
            {
                health += 50;
            }
        }
    }

    public void printStats()
    {
        System.out.println("Attack: " + attackValue);
        System.out.println("Defense: " + defenseValue);
        System.out.println("HP: " + health);
    }
    
    public boolean dropItem(Items item, Command command)
    {
        return itemList.remove(item);
    }

    public int getHealth(){
        return this.health;
    }

    public void changeHealth(int amount){
        this.health = this.health + amount;
    }

    public int getEnergy(){
        return this.energy;
    }

    public void changeEnergy(int amount){
        this.energy = this.energy + amount;
    }

    public ArrayList getItemList(){
        if(itemList.isEmpty())
        {
            System.out.println("Your inventory is empty!");
        }
        return this.itemList;
    }

    public int getAttackValue(){
        return this.attackValue;
    }

    public void setAttackValue(int attackValue){
        this.attackValue = attackValue;
    }

    public int getDefenseValue(){
        return this.defenseValue;
    }

    public void setDefenseValue(int defenseValue){
        this.defenseValue = defenseValue;
    }
}
