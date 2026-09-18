/**
 * This class establishes a connection to our espressohno database. This connection will be used by other
 * classes to write to and query different tables.
 * To establish a connection, our main game brain will need to create an instance of DBManager, call the connect and close
 * methods as pictured in the commented out MAIN function at the bottom of this file.
 */

package dbInteraction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static Connection conn; // for connecting to mysql with username and pwd
    private static boolean testMode = false; //boolean used to switch modes (ie using a different db for JUnits

    //adding env varibales 

    //private static final String url = System.getenv("DB_URL");
    //private static final String db_name = System.getenv("DB_NAME");
    //private static final String username = System.getenv("DB_USERNAME");
    //private static final String password = System.getenv("DB_PASSWORD");
    private static final String baseurl = "jdbc:mysql://localhost:1521/";
    private static final String params = "?user=k_smela&password=Changeme_00";

    /**
     * construcs a new DBManager
     */
    public DBManager() {}

    public static void enableTestMode() {
        testMode = true;
    }

    /**
     * establishes a connection our SQL database
     */
    public static void connect() throws SQLException {
        if (conn != null) {
            return; // returns if the db is already connected
        }
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            //enables the dbName to switch based on the value of testMode
            String dbName = testMode ? "espressohno_test" : "espressohno";


            //updated combined url name
            //String combined_url = url + db_name + "?user=" + username + "&password="+password;
            String url = baseurl + dbName + params;

            conn = DriverManager.getConnection(url);
            //System.out.println("Connected to the "+ dbName +" db successfully");

        }
        catch(Exception e) {
            throw new SQLException();
        }
    }

    /**
     * gets the current connection object
     * @return Conn current connection
     */
    public static Connection getConnection() {
        return conn;
    }

    /**
     * closes the connection to the database
     */
    public static void close() {
        try{
            if (conn != null) {
                conn.close();
                conn = null; //sets connection object back to null once closed
                //System.out.println("Connection closed successfully");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
