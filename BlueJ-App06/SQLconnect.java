import java.sql.*;
/**
 * Write a description of class SQLconnect here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SQLconnect
{
    // instance variables - replace the example below with your own
    public static Connection c = null;
    public static Statement stmt = null;
    public static DirectoryMaker dir;
  public static void connectDB(String folder, String database, boolean autoCommit)
  {
      dir.createDir(folder);
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
}
