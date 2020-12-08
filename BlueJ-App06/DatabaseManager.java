import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.*;
/**
 * Write a description of class s here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DatabaseManager
{
    public static DirectoryMaker dir;
    public static SQLconnect con;
    public static Connection c = null;
    public static Statement stmt = null;
    public final static String sqlSaveData1 = "CREATE TABLE IF NOT EXISTS room" +
        "(ID INTEGER PRIMARY KEY NOT NULL," +
        " roomName STRING NOT NULL," + 
        " level INTEGER NOT NULL," + 
        " items INTEGER NOT NULL," + 
        " position INTEGER NOT NULL)";

    public final static String sqlSaveData2 = "CREATE TABLE IF NOT EXISTS roomLog" +
        "(ID INTEGER PRIMARY KEY NOT NULL," +
        " roomID INTEGER NOT NULL," + 
        " time INTEGER NOT NULL," + 
        " visitCounter INTEGER NOT NULL," + 
        " north BOOLEAN NOT NULL," +
        " east BOOLEAN NOT NULL," +
        " south BOOLEAN NOT NULL," +
        " west BOOLEAN NOT NULL," +
        " cameFrom String NOT NULL," +
        " roomAge INTEGER NOT NULL)";

    public final static String sqlSaveData3 = "CREATE TABLE IF NOT EXISTS player" +
        "(name STRING NULL," +
        " hp INTEGER NULL," + 
        " xp INTEGER NULL," + 
        " energy INTEGER NULL)";

    public final static String sqlSaveData4 = "CREATE TABLE IF NOT EXISTS playerInventory" +
        "(ID INTEGER NOT NULL," +
        " name STRING NOT NULL," + 
        " type STRING NOT NULL," +
        " rank INTEGER NOT NULL)";

    public final static String[] sqlSaveData = {sqlSaveData1, sqlSaveData2, sqlSaveData3, sqlSaveData4};

    public final static String sqlProgramFiles1 = "CREATE TABLE IF NOT EXISTS userInterface" +
        "(type STRING NOT NULL," +
        " content STRING NOT NULL)";

    /**
     * Constructor for class DatabaseManager.
     */    
    public DatabaseManager() 
    {
        dir = new DirectoryMaker();
    }

    public static void connectDB(String folder, String database, boolean autoCommit)
    {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + folder + "/" + database + ".zuul");
            stmt = c.createStatement();
            c.setAutoCommit(autoCommit);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void initialiseSaveData(String filename)
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

    public static void initialiseProgramData(String filename)
    {
        try {
            connectDB("ProgramFiles", filename, true);
            stmt.executeUpdate(sqlProgramFiles1);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }  

    public static void insertDB(String folder, String filename, String table, String columns, String data)
    {
        try {
            connectDB(folder, filename, false);
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

    public static void findDB(String folder, String filename, String table, String column, String dataType)
    {
        try {
            connectDB(folder, filename, false);
            int type = dataTypeParser(dataType);
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
    
    public static void getmaxID(String folder, String filename, String table, String column, String dataType)
    {
        try {
            connectDB(folder, filename, false);
            int type = dataTypeParser(dataType);
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
    
    private static int dataTypeParser(String type)
    {
        type.toLowerCase();
        if(type.equals("string"))
        {
            return 1;
        }
        else if(type.matches("int|integer"))
        {
            return 2;
        }
        else if(type.matches("boolean"))
        {
            return 3;
        }
        else
        {
            return 4;
        }
    }
    
    public static void updateDB()
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:myBlog.sqlite");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE web_blog set message = 'This is updated by updateDB()' where ID=1;";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM web_blog;" );
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
}