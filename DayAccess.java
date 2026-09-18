package dbInteraction;

import java.sql.*;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import mainFiles.*;

/**
 * This class provides methods for interactions between java code and the days table in our database
 */

public class DayAccess {
    private final Connection conn;

    /**
     * constructs a new DayAccess object
     */
    public DayAccess() {
        conn = DBManager.getConnection();
    }

    /**
     * Inserts a new row into the days table in our db
     * @param dayCycle the DayCyle object we want to insert a row into our database for
     */
    public void insertDayResult(DayCycle dayCycle) {
        String query = "INSERT INTO days (dayID, accuracy, wasSued) VALUES (?, ?, ?)";

        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, dayCycle.getCycleID());
            statement.setDouble(2, dayCycle.getSuccess());
            statement.setBoolean(3, dayCycle.getLawsuit());

            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * returns an object array representation of a row in the days table
     * @param dayID the dayID primary key associated with the row in the day table we are interested in
     * @return
     */
    public Object[] getDayResult(int dayID){
        String query = "SELECT * FROM days WHERE dayID = ?";

        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, dayID);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                double accuracy = rs.getDouble("accuracy");
                boolean wasSued = rs.getBoolean("wasSued");
                return new Object[]{dayID, accuracy, wasSued};
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null; //nothing found
    }

    /**
     * Clears the days table in the espressohno database
     */
    public void clearDayTable() throws SQLException, NullPointerException {
        try {
            String query = "TRUNCATE TABLE days";
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            //System.out.println("days table cleared"); // can be deleted if we don't want to print anything to terminal
        } catch (SQLException | NullPointerException s){
            throw new SQLException();
        }

    }

}
