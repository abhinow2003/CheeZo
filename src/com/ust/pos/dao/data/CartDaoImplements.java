package com.ust.pos.dao.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ust.pos.bean.CartBean;
import com.ust.pos.dao.domain.CartDao;
import com.ust.pos.util.DBConnection;
import com.ust.pos.util.IdGenerator;

public class CartDaoImplements implements CartDao {

    // ✅ INSERT Cart
    @Override
    public String add(CartBean cart) {
        String cartId = IdGenerator.nextCartId();
        cart.setCartID(cartId);

        String query = "INSERT INTO Cart (cartID, userID, foodID, type, quantity, cost, orderDate) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, cart.getCartID());
            ps.setString(2, cart.getUserID());
            ps.setString(3, cart.getFoodID());
            ps.setString(4, cart.getType());
            ps.setInt(5, cart.getQuantity());
            ps.setDouble(6, cart.getCost());
            ps.setDate(7, cart.getOrderDate());

            ps.executeUpdate();
            System.out.println("✅ Cart item added! Cart ID: " + cartId);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartId;
    }

    // ✅ UPDATE Cart
    @Override
    public boolean update(CartBean updated) {
        String query = "UPDATE Cart SET userID=?, foodID=?, type=?, quantity=?, cost=?, orderDate=? "
                     + "WHERE cartID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, updated.getUserID());
            ps.setString(2, updated.getFoodID());
            ps.setString(3, updated.getType());
            ps.setInt(4, updated.getQuantity());
            ps.setDouble(5, updated.getCost());
            ps.setDate(6, updated.getOrderDate());
            ps.setString(7, updated.getCartID());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ DELETE Cart Entry
    @Override
    public boolean remove(String cartId) {
        String query = "DELETE FROM Cart WHERE cartID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, cartId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("🗑️ Cart item deleted: " + cartId);
            }
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ GET Cart by ID
    @Override
    public CartBean findById(String cartId) {
        CartBean cart = null;
        String query = "SELECT * FROM Cart WHERE cartID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, cartId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cart = new CartBean();
                cart.setCartID(rs.getString("cartID"));
                cart.setUserID(rs.getString("userID"));
                cart.setFoodID(rs.getString("foodID"));
                cart.setType(rs.getString("type"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setCost(rs.getDouble("cost"));
                cart.setOrderDate(rs.getDate("orderDate"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    // ✅ GET All Carts for a User
    @Override
    public List<CartBean> findByUser(String userId) {
        List<CartBean> list = new ArrayList<>();
        String query = "SELECT * FROM Cart WHERE userID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartBean cart = new CartBean();
                cart.setCartID(rs.getString("cartID"));
                cart.setUserID(rs.getString("userID"));
                cart.setFoodID(rs.getString("foodID"));
                cart.setType(rs.getString("type"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setCost(rs.getDouble("cost"));
                cart.setOrderDate(rs.getDate("orderDate"));
                list.add(cart);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
