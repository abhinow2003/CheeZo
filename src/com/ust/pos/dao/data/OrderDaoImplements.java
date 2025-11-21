package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.OrderDao;
import com.ust.pos.bean.OrderBean;
import com.ust.pos.util.DBConnection;
import com.ust.pos.util.IdGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImplements implements OrderDao {

    // =====================================================================================
    // CREATE ORDER
    // =====================================================================================
    @Override
    public String create(OrderBean order) {

        String id = IdGenerator.nextOrderId();
        order.setOrderID(id);

        String sql = "INSERT INTO Orders (orderID, userID, orderDate, storeID, cartID, totalPrice, orderStatus, " +
                     "street, city, state, pincode, mobileNo,cardnumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, order.getOrderID());
            ps.setString(2, order.getUserID());
            ps.setDate(3, order.getOrderDate());
            ps.setString(4, order.getStoreID());
            ps.setString(5, order.getCartID());
            ps.setDouble(6, order.getTotalPrice());
            ps.setString(7, order.getOrderStatus());
            ps.setString(8, order.getStreet());
            ps.setString(9, order.getCity());
            ps.setString(10, order.getState());
            ps.setString(11, order.getPincode());
            ps.setString(12, order.getMobileNo());
            ps.setString(13, order.getCardNumber());

            ps.executeUpdate();
            System.out.println("✅ Order Created: " + id);
        }
        catch (SQLException e) {
            System.out.println("❌ Error inserting order");
            e.printStackTrace();
        }

        return id;
    }

    // =====================================================================================
    // UPDATE ORDER
    // =====================================================================================
    @Override
    public boolean update(OrderBean updated) {

        String sql = "UPDATE Orders SET userID=?, orderDate=?, storeID=?, cartID=?, totalPrice=?, orderStatus=?, " +
             "street=?, city=?, state=?, pincode=?, mobileNo=?, cardNumber=? WHERE orderID=?";


        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

           ps.setString(1, updated.getUserID());
ps.setDate(2, updated.getOrderDate());
ps.setString(3, updated.getStoreID());
ps.setString(4, updated.getCartID());
ps.setDouble(5, updated.getTotalPrice());
ps.setString(6, updated.getOrderStatus());
ps.setString(7, updated.getStreet());
ps.setString(8, updated.getCity());
ps.setString(9, updated.getState());
ps.setString(10, updated.getPincode());
ps.setString(11, updated.getMobileNo());
ps.setString(12, updated.getCardNumber());  // ⭐ FIXED
ps.setString(13, updated.getOrderID());


            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // =====================================================================================
    // DELETE ORDER
    // =====================================================================================
    @Override
    public boolean delete(String orderId) {

        String sql = "DELETE FROM Orders WHERE orderID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, orderId);
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // =====================================================================================
    // FIND BY ID
    // =====================================================================================
    @Override
    public OrderBean findById(String orderId) {

        String sql = "SELECT * FROM Orders WHERE orderID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractOrder(rs);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // =====================================================================================
    // FIND ALL
    // =====================================================================================
    @Override
    public List<OrderBean> findAll() {

        List<OrderBean> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractOrder(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // =====================================================================================
    // FIND BY USER
    // =====================================================================================
    @Override
    public List<OrderBean> findByUser(String userId) {

        List<OrderBean> list = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE userID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractOrder(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // =====================================================================================
    // MAP RESULTSET TO ORDERBEAN
    // =====================================================================================
    private OrderBean extractOrder(ResultSet rs) throws SQLException {

    OrderBean order = new OrderBean();

    order.setOrderID(rs.getString("orderID"));
    order.setUserID(rs.getString("userID"));
    order.setOrderDate(rs.getDate("orderDate"));
    order.setStoreID(rs.getString("storeID"));
    order.setCartID(rs.getString("cartID"));
    order.setTotalPrice(rs.getDouble("totalPrice"));
    order.setOrderStatus(rs.getString("orderStatus"));
    order.setStreet(rs.getString("street"));
    order.setCity(rs.getString("city"));
    order.setState(rs.getString("state"));
    order.setPincode(rs.getString("pincode"));
    order.setMobileNo(rs.getString("mobileNo"));

    // ⭐ FIXED: Load cardNumber also!
    order.setCardNumber(rs.getString("cardNumber"));

    return order;
}

}
