package dbInteraction;

import java.sql.*;
import enums.enumerables.*;

import mainFiles.*;

/**
 * This class provides methods for interactions between our java code and the orders table in our db
 */

public class OrderAccess {
    //established the connection to use to get customer info from DB
    private final Connection conn;

    /**
     * constructs a new OrderAccess object
     */
    public OrderAccess() {
        conn = DBManager.getConnection();
    }

    /**
     * Create an Order object based on a provided drinkID and row in the orders table
     * @param drinkID
     * @return
     */
    public Order loadOrderByDrinkID(int drinkID) {
        try{
            String query = "SELECT * FROM orders WHERE drinkID = " + drinkID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String type = rs.getString("type");
                dType typeEnum = dType.valueOf(type);

                String size = rs.getString("size");
                dSize sizeEnum = dSize.valueOf(size);

                return new Order(
                        rs.getInt("drinkID"),
                        typeEnum,
                        sizeEnum,
                        rs.getBoolean("isHot"),
                        rs.getInt("QID"),
                        rs.getString("syrup")
                );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * returns the QID associated with a given drinkID
     * @param drinkID the drinkID of the order or made order we want to get the QID for
     * @return the QID
     */
    public int getQIDByDrinkID(int drinkID) {
        String query = "SELECT QID FROM orders WHERE drinkID = " + drinkID;
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt("QID");
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return -1; //returns negative if QID not found
    }

}
