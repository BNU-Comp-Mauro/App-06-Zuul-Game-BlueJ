import java.util.ArrayList;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    public Items item;
    public DatabaseManager db;

    public int hp;
    public int energy;
    public ArrayList itemList;
    public int attackValue;
    public int defenseValue;
    public String name;
    public int score;
    public String location;
    
    public ArrayList<String> playerData = new ArrayList<String>();
    
    public Player(String name, boolean newPlayer)
    {
        playerData.removeAll(playerData);
        db.manual_connectSaveDataDB(name, false);
        if(newPlayer)
        {
            db.manual_insertDB("player", "name", "'" + name + "'");
        }
        db.manual_closeDB();
        getPlayerData(name);
    }
    
    public ArrayList<String> getPlayerData(String name)
    {
        playerData.removeAll(playerData);
        db.manual_connectSaveDataDB(name, false);
        playerData.add(name); //name 0
        playerData.add(db.manual_getDataDB("hp", "player", "name = '" + name + "'")); //hp 1
        playerData.add(db.manual_getDataDB("coins", "player", "name = '" + name + "'")); //coins 2
        playerData.add(db.manual_getDataDB("xp", "player", "name = '" + name + "'")); //xp 3
        playerData.add(db.manual_getDataDB("energy", "player", "name = '" + name + "'")); //energy 4
        playerData.add(db.manual_getDataDB("armorSlot", "player", "name = '" + name + "'")); //armorSlot 5
        playerData.add(db.manual_getDataDB("itemSlot1", "player", "name = '" + name + "'")); //itemSlot1 6
        playerData.add(db.manual_getDataDB("itemSlot2", "player", "name = '" + name + "'")); //itemSlot2 7
        playerData.add(db.manual_getDataDB("location", "player", "name = '" + name + "'")); //location 8
        db.manual_closeDB();
        return playerData;
    }
    
    public ArrayList<String> getPlayerInventory(String name)
    {
        playerData.removeAll(playerData);
        db.manual_connectSaveDataDB(name, false);
        int id = 0;
        while(true)
        {
            try
            {
                playerData.add((db.manual_getDataDB("invID", "playerInventory", "ID = '" + id + "'")) + "-" + (db.manual_getDataDB("quantity", "playerInventory", "ID = '" + id + "'")));
            }
            catch(Exception e)
            {
                break;
            }
            id++;
        }
        db.manual_closeDB();
        return playerData;
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
        else if(item == Items.HEALTH_POTION)
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
