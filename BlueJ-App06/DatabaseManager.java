import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.*;
/**
 * Write a description of class s here.
 *
 * @author Haroon Sadiq
 * @version 0.1
 */
public class DatabaseManager
{
    private Menu menu;
    public DirectoryMaker dir;
    public SQLconnect con;
    public static Connection c = null;
    public static Statement stmt = null;
    public final static String sqlSaveData1 = "CREATE TABLE IF NOT EXISTS room" +
        "(ID INTEGER PRIMARY KEY," +
        " roomName STRING NOT NULL," + 
        " roomType STRING NOT NULL," +
        " items INTEGER DEFAULT 0," + 
        " visitCounter INTEGER," + 
        " north BOOLEAN DEFAULT 0," +
        " east BOOLEAN DEFAULT 0," +
        " south BOOLEAN DEFAULT 0," +
        " west BOOLEAN DEFAULT 0," +
        " rank INTEGER DEFAULT 0," +
        " x INTEGER DEFAULT 0," +
        " y INTEGER DEFAULT 0," +
        " found BOOLEAN DEFAULT 0)";
        

    public final static String sqlSaveData2 = "CREATE TABLE IF NOT EXISTS droppedItems" +
        "(roomID INTEGER," + 
        " invID STRING," + 
        " quantity INTEGER DEFAULT 1," + 
        " FOREIGN KEY(roomID) REFERENCES room(ID))";

    public final static String sqlSaveData3 = "CREATE TABLE IF NOT EXISTS player" +
        "(name STRING NOT NULL," +
        " hp INTEGER DEFAULT 100," + 
        " coins INTEGER DEFAULT 0," + 
        " xp INTEGER DEFAULT 0," + 
        " energy INTEGER DEFAULT 100," +
        " armorSlot STRING DEFAULT 'n/a'," +
        " itemSlot1 STRING DEFAULT 'n/a'," +
        " itemSlot2 STRING DEFAULT 'n/a'," +
        " location STRING DEFAULT '0,0')";

    public final static String sqlSaveData4 = "CREATE TABLE IF NOT EXISTS playerInventory" +
        "(invID STRING," + 
        " quantity INTEGER)";

    public final static String[] sqlSaveData = {sqlSaveData1, sqlSaveData2, sqlSaveData3, sqlSaveData4};

        
    public final static String sqlProgramFiles1 = "CREATE TABLE IF NOT EXISTS itemData" +
        "(ID STRING," +
        " name STRING," +
        " damage INTEGER," +
        " health INTEGER," +
        " protection INTEGER," +
        " energy INTEGER," +
        " coins INTEGER)";
        
    public final static String sqlProgramFiles2 = "CREATE TABLE IF NOT EXISTS enemyData" +
        "(ID STRING," +
        " name STRING," +
        " damage INTEGER," +
        " health INTEGER," +
        " coins INTEGER)";
        
    public final static String[] sqlProgramData = {sqlProgramFiles1, sqlProgramFiles2};

    /**
     * Constructor for class DatabaseManager.
     */    
    public DatabaseManager() 
    {
        dir = new DirectoryMaker();
    }

    public static void connectDB(String folder, String filename, boolean autoCommit)
    {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + folder + "/" + filename + ".zuul");
            stmt = c.createStatement();
            c.setAutoCommit(autoCommit);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void initialiseSaveData(String filename)
    {
        for(String i : sqlSaveData)
        {
            try {
                connectDB("SaveData", filename, true);
                stmt.executeUpdate(i);
                stmt.close();
                c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Table created successfully");
        }
    }    

    public static void initialiseProgramData()
    {
        for(String i : sqlProgramData)
        {
            try {
                connectDB("ProgramFiles", "data", true);
                stmt.executeUpdate(i);
                stmt.close();
                c.close();
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Table created successfully");
        }
        
        try {
            connectDB("ProgramFiles", "data", true);
            stmt.executeUpdate(sqlProgramFiles1);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }  

    public static void insertDB(String folder, String filename, String table, String columns, String data, boolean manualConnect)
    {
        try {
                
            stmt = c.createStatement();
            String sql = "INSERT INTO " + table + " (" + columns + ") " +
                "VALUES (" + data + ");"; 
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public static void findDB(String folder, String filename, String table, String column)
    {
        try {
            connectDB(folder, filename, false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT " + column + " FROM " + table + ";" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                String  message = rs.getString("message");
                String date_added = rs.getString("date_added");
                System.out.println( "ID : " + id );
                System.out.println( "Name : " + name );
                System.out.println( "Message : " + message );
                System.out.println( "Date Added : " + date_added );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");  
    }
    
    
    
    /**
     * select all rows 
     */
    public void selectAll(String filename){
        String sql = "SELECT id, roomName FROM room";
        
        try{
            connectDB("SaveData", filename, false);
            stmt = c.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" + 
                                   rs.getString("roomName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * 
     * @param capacity 
     */
    public static String getData(String filename, String sqlSelect, String sqlFrom, String sqlWhere)
    {
        String sql = "SELECT " + sqlSelect +" "
                  + "FROM " + sqlFrom +" WHERE " + sqlWhere;
        String data = null;
        try{
            connectDB("SaveData", filename, false);
            stmt = c.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            data = rs.getString(sqlSelect);
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    public static void getmaxID(String folder, String filename, String table, String column)
    {
        try {
            connectDB(folder, filename, false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT " + column + " FROM " + table + ";" );
            while ( rs.next() ) {
                int id = rs.getInt("ID");
                String  name = rs.getString("roomName");
                System.out.println( "ID : " + id );
                System.out.println( "Name : " + name );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");  
    }
    
    public static void getColumns(String folder, String filename, String table)
    {
        try {
            connectDB(folder, filename, false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "PRAGMA table_info(" + table + ");" );
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public static void updateDB(String filename, String sqlSelect, String sqlFrom, String sqlWhere,String sqlNewData, boolean manualConnect)
    {
        try {

            connectDB("SaveData", filename, false);
            stmt = c.createStatement();
            String sql = "UPDATE " + sqlFrom +" SET " + sqlSelect +" = " + sqlNewData + " WHERE " + sqlWhere +";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
            System.out.println("Operation done successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
    
    public static void updateMultiDB(String filename, String sqlSet, String sqlFrom, String sqlWhere)
    {
        try {
            connectDB("SaveData", filename, false);
            stmt = c.createStatement();
            String sql = "UPDATE " + sqlFrom +" SET " + sqlSet +" WHERE " + sqlWhere +";";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
            System.out.println("Operation done successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
    
    public static void manual_connectProgramFilesDB(boolean autoCommit)
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ProgramFiles/data.zuul");
            stmt = c.createStatement();
            c.setAutoCommit(autoCommit);
        } catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void manual_connectSaveDataDB(String filename, boolean autoCommit)
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:SaveData/" + filename + ".zuul");
            stmt = c.createStatement();
            stmt.close();
            c.setAutoCommit(autoCommit);
        } catch ( Exception e )
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public static ArrayList<String> manual_getAllPlayerInventory(String filename)
    {
        manual_connectSaveDataDB(filename, false);
        String sql = "SELECT invID, quantity FROM playerInventory";
        ArrayList<String> playerDataInv = new ArrayList<String>();
        int c = 0;
        try 
        {
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next())
            {
                playerDataInv.add(rs.getString("invID") + "," + rs.getInt("quantity"));
            }
        }
        catch (SQLException e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        while(c < 17)
        {
            playerDataInv.add("");
            c++;
        }
        manual_closeDB();
        return playerDataInv;
    }
    
    public static ArrayList<String> manual_getAllDroppedItems(String filename)
    {
        manual_connectSaveDataDB(filename, false);
        String sql = "SELECT invID, quantity FROM playerInventory";
        ArrayList<String> playerDataInv = new ArrayList<String>();
        int c = 0;
        try 
        {
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next())
            {
                playerDataInv.add(rs.getString("invID") + "," + rs.getInt("quantity"));
            }
        }
        catch (SQLException e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        while(c < 17)
        {
            playerDataInv.add("");
            c++;
        }
        manual_closeDB();
        return playerDataInv;
    }
    
    
    public static ArrayList<String> manual_getAllPlayerData(String filename)
    {
        manual_connectSaveDataDB(filename, false);
        String sql = "SELECT hp, coins, xp, energy, armorSlot, itemSlot1, itemSlot2, location FROM player";
        ArrayList<String> playerDataInv = new ArrayList<String>();
        try 
        {
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next())
            {
                playerDataInv.add(Integer.toString(rs.getInt("hp")));
                playerDataInv.add(Integer.toString(rs.getInt("coins")));
                playerDataInv.add(Integer.toString(rs.getInt("xp")));
                playerDataInv.add(Integer.toString(rs.getInt("energy")));
                playerDataInv.add(rs.getString("armorSlot"));
                playerDataInv.add(rs.getString("itemSlot1"));
                playerDataInv.add(rs.getString("itemSlot2"));
                playerDataInv.add(rs.getString("location"));
            }
        }
        catch (SQLException e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        manual_closeDB();
        return playerDataInv;
    }
    
    public static void manual_insertDB(String table, String columns, String data)
    {
        try
        {
            String sql = "INSERT INTO " + table + " (" + columns + ") " + "VALUES (" + data + ");"; 
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public static void manual_updateMultiDB(String sqlSet, String sqlFrom, String sqlWhere)
    {
        try
        {
            
            String sql = "UPDATE " + sqlFrom +" SET " + sqlSet +" WHERE " + sqlWhere +";";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    public static void manual_deleteStringRowDB(String table, String row, String value)
    {
        String sql = "DELETE FROM " + table + " WHERE " + row + " = " + value;
        manual_connectSaveDataDB("new", false);
        try (PreparedStatement pstmt = c.prepareStatement(sql))
        {

            // set the corresponding param
            pstmt.setString(0, value);
            // execute the delete statement
            pstmt.executeUpdate();

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        manual_closeDB();
    }
    
    public static void manual_deleteRowDB(String table, String column, String value)
    {
        String sql = "DELETE FROM " + table + " WHERE " + column + " = " + value;
        try
        {
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            // execute the delete statement
            c.commit();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static String manual_getDataDB(String sqlSelect, String sqlFrom, String sqlWhere)
    {
        String sql = "SELECT " + sqlSelect + " " + "FROM " + sqlFrom +" WHERE " + sqlWhere;
        String data = "";
        try
        {
            ResultSet rs    = stmt.executeQuery(sql);
            data = rs.getString(sqlSelect);
            rs.close();
        } 
        catch (SQLException e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return data;
    }
    
    public static void manual_closeDB()
    {
        try
        {
            stmt.close();
            c.close();
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public static void deleteDB()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:myBlog.sqlite");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "DELETE from web_blog where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM web_blog;" );
            while ( rs.next() )
            {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                String  message = rs.getString("message");
                String date_added = rs.getString("date_added");
                System.out.println( "ID : " + id );
                System.out.println( "Name : " + name );
                System.out.println( "Message : " + message );
                System.out.println( "Date Added : " + date_added );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}