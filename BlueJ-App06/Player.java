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
    private int score;

    public Player(String name)
    {
        this.name = name;
        health = 100;
        energy = 100;
        attackValue = 0;
        defenseValue = 0;
        score = 0;

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
