
/**
 *  The MakeDB class.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 04/26/2018
 */
/* 
import java.sql.*;
import java.io.*;

public class MakeDB
{
    public static void main(String[] args) throws Exception
    {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        
        String url = "jdbc:odbc:StockTracker";
        
        Connection con = DriverManager.getConnection(url);
        Statement stmt = con.createStatement();
        
        //The following code deltes each index and table, if they exist.
        // If they do not exist, a message is displayed and execution continues.
        System.out.println("Dropping indexs & tables ...");
        
        try
        {
            stmt.executeUpdate("DROP INDEX PK_UserStocks ON UserStocks");
        }
        catch(Exception e)
        {
            System.out.println("Could not drop primary key on UserStocks table: "
                                +e.getMessage());
        }
        try
        {
            stmt.executeUpdate("DROP TABLE UserStocks");
        }
        catch (Exception e)
        {
            System.out.println("Could not drop UserStocks table: "
                                +e.getMessage());
        }
        try
        {
            stmt.executeUpdate("DROP TABLE Users");
        }
        catch (Exception e)
        {
            System.out.println("Could not drop Users table: "
                                + e.getMessage());
        }
        try
        {
            stmt.executeUpdate("DROP TABLE Stocks");
        }
        catch (Exception e)
        {
            System.out.println("Could not drop Stocks table: "
                                + e.getMessage());
        }
        
        /////// Create the database Tables ///////////
        System.out.println("\nCreating tables .............");
        try
        {
            System.out.println("Creating Stocks table with primary key index...");
            stmt.executeUpdate("CREATE TABLE Stocks ("
            + "symbol TEXT(8) NOT NULL "
            +"CONSTRAINT PK_Stocks PRIMARY KEY, "
            +"name TEXT(50)"
            +")");
        }
        catch (Exception e)
        {
            System.out.println("Exception creating Stocks table: "
                                +e.getMessage());
        }
        
        // Create Users table with primary key index
        try
        {
            System.out.println("Creating Users table with primary key index...");
            stmt.executeUpdate("CREATE TABLE Users ("
            +"userID TEXT(20) NOT NULL "
            +"CONSTRAINT PK_Users PRIMARY KEY  "
            +"lastName TEXT(30) NOT NULL,  "
            +"firstName TEXT(30) NOT NULL, "
            +"pswd LONGBINARY, "
            +"admin BIT"
            +")");
        }
        catch (Exception e)
        {
            System.out.println("Exception creating Users table: "
                                +e.getMessage());
        }
        
        // Create UserStocks table with foreign keys to Users and Stocks tables
        try
        {
            System.out.println("Creating UserStocks table...");
            stmt.executeUpdate("CREATE TABLE UserStocks ("
            +"userID TEXT(20) "
            +"CONSTRAINT FK1_UserStocks REFERENCES Users (userID), "
            +"symbol TEXT(8), "
            +"CONSTRAINT FK2_UserStocks FOREIGN KEY (symbol) "
            +"REFERENCES Stocks (symbol))");
        }
        catch (Exception e)
        {
            System.out.println("Exception creating UserStocks table: "
                                +e.getMessage());
        }
        
        // Create UserStocks table primary key index
        try
        {
            System.out.println("Creating UserStocks table primary key index...");
            stmt.executeUpdate("CREATE UNIQUE INDEX PK_UserStocks "
            +"ON UserStocks (userID, symbol)  "
            +"WITH PRIMARY DISALLOW NULL");
        }
        catch (Exception e)
        {
            System.out.println("Exception creating UserStocks index: "
                                +e.getMessage());
        }
        
        // Create one administrative user with password as initial data
        String userID = "admin01";
        String firstName = "Default";
        String lastName = "Admin";
        String initialPswd = "admin01";
        Password pswd = new Password(initialPswd);
        boolean admin = true;
        
        PreparedStatement pStmt=
                    con.prepareStatement("INSERT INTO Users VALUES (?,?,?,?,?)");
        try
        {
            pStmt.setString(1, userID);
            pStmt.setString(2, lastName);
            pStmt.setString(3, firstName);
            pStmt.setBytes(4, serializeObj(pswd));
            pStmt.setBoolean(5, admin);
            pStmt.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println("Exception inserting user: "
                                +e.getMessage());
        }
        
        pStmt.close();
        
        // Read and display all User data in the database
        ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");
        
        System.out.println("Database created. \n");
        System.out.println("Displaying data from database...\n");
        System.out.println("Users table contains:");
        
        Password pswdFromDB;
        byte[] buf = null;
        
        while(rs.next())
        {
            System.out.println("Logon ID                ="
                                +rs.getString("UserID"));
            System.out.println("First name              ="
                                +rs.getString("firstName"));
            System.out.println("Last name           ="+rs.getString("lastName"));
            System.out.println("Administrative      ="+rs.getBoolean("admin"));
            System.out.println("Initial password = "+initialPswd);
        }
        
        rs = stmt.executeQuery("SELECT * FROM Stocks");
        if(!rs.next())
            System.out.println("Stocks table contains no records.");
        else
            System.out.println("Stocks table still contains records!");
            
        rs = stmt.executeQuery("SELECT * FROM UserStocks");
        if(!rs.next())
            System.out.println("UserStocks table contains no records.");
        else
            System.out.println("UserStocks table still contains records!");
            
        stmt.close();  // closing Statement also closes ResultSet
        
    }
 // end of main

    //Method to write onject to byte array and then insert into prepared statement
    public static byte[] serializeObj(Object obj)
                                throws IOException
    {
        ByteArrayOutputStream baOStream = new ByteArrayOutputStream();
        ObjectOutputStream objOStream = new ObjectOutputStream(baOStream);
        
        objOStream.writeObject(obj);  // objects must be Serializeable
        objOStream.flush();
        objOStream.close();
        return baOStream.toByteArray(); // returns stream as byte array
        
    }
    
   // Method to read bytes from result set into a byte array and then
   // create an input stream and read the data into asn object
   public static Object deserializeObj(byte[] buf)
                                  throws IOException, ClassNotFoundException
   {
       Object obj = null;
       
       if (buf != null)
       {
           ObjectInputStream objIStream =
            new ObjectInputStream(new ByteArrayInputStream(buf));
            
           obj = objIStream.readObject(); // throws IOException, ClassNotFoundException
       }
       return obj;
    }
} // End of class
                                            
*/