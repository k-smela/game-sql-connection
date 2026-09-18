package dbInteraction;

import java.sql.*;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import mainFiles.*;

/**
 * This class provides methods for interactions between java code and the nights table in our database
 */

public class NightAccess {
    private final Connection conn;

    /**
     * constructs a new NightAccess object
     */
    public NightAccess() {
        conn = DBManager.getConnection();
    }

    /**
     * Inserts a new row in the nights table
     * @param nightCycle the NightCycle object we want to create a row in our db for.
     */
    public void insertNightResult(NightCycle nightCycle) {
        String query = "INSERT INTO nights (nightID, accuracy, didEscape) VALUES (?, ?, ?)";

        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, nightCycle.getCycleID());
            statement.setDouble(2, nightCycle.getSuccess());
            statement.setBoolean(3, nightCycle.getEscape());

            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * returns an object array of the results of a night cycle stored in the nights table in our db
     * @param nightID the primary key uniquely identifying a row in the night table
     * @return an Object array of the nightID, accuracy, and didEscape data for the night cycle at hand
     */
    public Object[] getNightResult(int nightID){
        String query = "SELECT * FROM nights WHERE nightID = ?";

        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, nightID);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                double accuracy = rs.getDouble("accuracy");
                boolean didEscape = rs.getBoolean("didEscape");

                return new Object[]{nightID, accuracy, didEscape};
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null; //nothing found
    }

    /**
     * clears the nights table in the db
     */
    public void clearNightTable() throws NullPointerException, SQLException {
        String query = "TRUNCATE TABLE nights";
        try{
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            //System.out.println("nights table cleared"); // can be deleted if we don't want to print anything to terminal
        }
        catch(SQLException | NullPointerException s){
            throw new SQLException();
        }
    }

}
