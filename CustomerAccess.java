package dbInteraction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mainFiles.*;

/**
 * This class establishes methods to handle all interactions between our java files and the customers table
 * in the espressohno database.
 */
public class CustomerAccess {

    //established the connection to use to get customer info from DB
    private Connection conn;
    private OrderAccess orderAccess;

    /**
     * constructs a new CustomerAccess
     */
    public CustomerAccess() {
        conn = DBManager.getConnection();
        orderAccess = new OrderAccess();
    }

    /**
     * creates a new Customers object instance given a provided customerID
     * @param id the custID of the customer who we are creating
     * @return a Customers object
     */
    public Customers getCustomerbyID(int id){
        Customers customer = null;

        try{
            String query = "SELECT * FROM customers WHERE custID =" + id;
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if(rs.next()){
                int drinkID = rs.getInt("drinkID");
                Order order = orderAccess.loadOrderByDrinkID(drinkID);

                // call the constructor to create a new instance of Customer class
                return new Customers(
                        rs.getInt("custID"),
                        rs.getString("name"),
                        rs.getString("imagePath"),
                        order
                );
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * creates a list of Customers to be served in a day based on a provided dayID
     * @param dayID the ID of the day we want to list the customers being served for
     * @return list of Customers objects
     */
    public List<Customers> loadCustomersForDay(int dayID){
        // make sure we don't accidentally try to load customers for an invalid dayID
        if(dayID > 5 || dayID < 0){
            return null;
        }

        List<Customers> list = new ArrayList<>();

        try {
            String query = "SELECT c.custID, c.name, c.imagePath, c.drinkID FROM customerDayPairs p JOIN customers c ON p.custID = c.custID WHERE p.dayID = " + dayID;
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()){
                int drinkID = rs.getInt("drinkID");
                Order order = orderAccess.loadOrderByDrinkID(drinkID);

                list.add(new Customers(
                        rs.getInt("custID"),
                        rs.getString("name"),
                        rs.getString("imagePath"),
                        order
                ));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returns a list of Customers objects for the customers to be served at night.
     * @param dayID the dayID that connects to the correct night
     * @return the list of night Customers (Freddy, Purple Guy, Chica)
     */
    public List<Customers> loadCustomersForNight(int dayID){
        List<Integer> ids = List.of(10,11,14); //list of custIDs of the night customers
        List<Customers> customers = new ArrayList<>();

        for(Integer id : ids){
            Customers customer = getCustomerbyID(id);
            if(customer != null){
                customers.add(customer);
            }
        }

        return customers;
    }

}
