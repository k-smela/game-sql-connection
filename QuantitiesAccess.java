package dbInteraction;

import java.sql.*;

/**
 * This class provides methods and functionality to retrieve the milk, grind, and brew goal times for an order from the
 * quantities table in our db.
 */

public class QuantitiesAccess {

    private final Connection conn;

    /**
     * constructs a new QuantitiesAcess object
     */
    public QuantitiesAccess() {
        conn = DBManager.getConnection();
    }

    /**
     * Returns an array holding the goal milk, grind, and brew times based on a provided QID
     * @param QID the QID of the recipe we want the goal times for
     * @return a double array holding milkTime, grindTime, brewTime
     */
    public double[] getGoalTimesByQID(int QID) {
        String query = "SELECT milkTime, grindTime, brewTime FROM quantities WHERE QID = ?";
        try{
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,QID);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                double milkTime = rs.getDouble("milkTime");
                double grindTime = rs.getDouble("grindTime");
                double brewTime = rs.getDouble("brewTime");

                return new double[] {milkTime, grindTime, brewTime};
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;

    }



}
