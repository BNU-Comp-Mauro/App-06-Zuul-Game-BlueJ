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
    private DatabaseManager db;

    public int hp;
    public int energy;
    public ArrayList itemList;
    public int attackValue;
    public int defenseValue;
    public String name;
    public int score;
    public String location;

    public Player(String name, boolean newPlayer)
    {
        db.manual_connectSaveDataDB(name, false);
        if(newPlayer)
        {
            db.manual_insertDB("player", "name", "'" + name + "'");
        }
        this.name = name;
        hp = Integer.parseInt(db.manual_getDataDB("hp", "player", "name = '" + name + "'"));
        energy = Integer.parseInt(db.manual_getDataDB("energy", "player", "name = '" + name + "'"));
        location = db.manual_getDataDB("energy", "player", "name = '" + name + "'");
        db.manual_closeDB();
        attackValue = 0;
        defenseValue = 0;
        score = 0;
        itemList = new ArrayList();
    }
    
    public String getPlayerDB(String column)
    {
        return db.manual_getDataDB(column, "player", "name = '" + name + "'");
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
            if(hp > 51 && hp <= 100)
            {
                hp = 100;
            }
            else
            {
                hp += 50;
            }
        }
    }

    public void printStats()
    {
        System.out.println("Attack: " + attackValue);
        System.out.println("Defense: " + defenseValue);
        System.out.println("HP: " + hp);
    }
    
    public boolean dropItem(Items item, Command command)
    {
        return itemList.remove(item);
    }

    public int gethp(){
        return this.hp;
    }

    public void changehp(int amount){
        this.hp = this.hp + amount;
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
