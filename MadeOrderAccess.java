package dbInteraction;

import java.sql.*;

import enums.enumerables.*;

import mainFiles.*;

/**
 * This class provides methods for all interactions in our java with the userMade table.
 */

public class MadeOrderAccess {
    private final Connection conn;

    /**
     * constructs a new MadeOrderAccess object
     */
    public MadeOrderAccess() {
        conn = DBManager.getConnection();
    }

    /**
     * inserts a new row into the userMade table
     *
     * Note that method utilizes the replace keyword so when duplicate primary keys are encountered, ie
     * we add a madeOrder to the table again (using the same primary key), the existing row will be overwritten.
     *
     * @param madeOrder the object we want to insert a representative row for into userMade table
     */
    public void insertMadeOrder(MadeOrder madeOrder) {
        String sql =
                "REPLACE INTO userMade " +
                        "(userMadeID, type, size, isHot, syrup, milkTime, grindTime, brewTime, drinkID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            // userMadeID is always required (PK)
            ps.setInt(1, madeOrder.getMadeID());

            // nullable enum fields
            ps.setString(2, madeOrder.getType() == null ? null : madeOrder.getType().name());
            ps.setString(3, madeOrder.getSize() == null ? null : madeOrder.getSize().name());

            // regular fields
            ps.setBoolean(4, madeOrder.isHot());
            ps.setString(5, madeOrder.getSyrup());
            ps.setDouble(6, madeOrder.getMilkTime());
            ps.setDouble(7, madeOrder.getGrindTime());
            ps.setDouble(8, madeOrder.getBrewTime());
            ps.setInt(9, madeOrder.getDrinkID());

            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns a new MadeOrder object based off a row in the userMade table.
     * Assumes that the row for this userMadeID has already been populated
     * @param userMadeID the unique key associated with the user made drink we want to make a made order instance of
     * @return the new MadeOrder instance
     */
    public MadeOrder getMadeOrder(int userMadeID){

        try{
            String query = "SELECT * FROM userMade WHERE userMadeID = " + userMadeID;
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next()){
                String type = rs.getString("type");
                dType typeEnum = dType.valueOf(type);

                String size = rs.getString("size");
                dSize sizeEnum = dSize.valueOf(size);

                boolean isHot = rs.getBoolean("isHot");
                String syrup = rs.getString("syrup");
                double milkTime = rs.getDouble("milkTime");
                double grindTime = rs.getDouble("grindTime");
                double brewTime = rs.getDouble("brewTime");
                int drinkID = rs.getInt("drinkID");

                //fetch correct QID
                OrderAccess orderAccess = new OrderAccess();
                int QID = orderAccess.getQIDByDrinkID(drinkID);

                //construct new made order object
                MadeOrder madeOrder = new MadeOrder(drinkID, userMadeID);
                madeOrder.setType(typeEnum);
                madeOrder.setSize(sizeEnum);
                madeOrder.setIsHot(isHot);
                madeOrder.setSyrup(syrup);

                madeOrder.setMilkTime(milkTime);
                madeOrder.setGrindTime(grindTime);
                madeOrder.setBrewTime(brewTime);

                return madeOrder;

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return null;

    }

    /**
     * clears the userMade table holding info associated with MadeOrders in the db
     */
    public void clearUserMadeTable() throws NullPointerException, SQLException{
        String query = "TRUNCATE TABLE userMade";
        try{
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            //System.out.println("userMade table cleared"); // can be deleted if we don't want to print anything to terminal
        }
        catch (SQLException | NullPointerException e){
            throw new SQLException();
        }
    }
}
