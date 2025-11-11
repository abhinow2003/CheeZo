package com.ust.pos.dao.data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.ust.pos.bean.FoodBean;
import com.ust.pos.dao.domain.FoodDao;
import com.ust.pos.util.DBConnection;
import com.ust.pos.util.IdGenerator;

public class FoodDaoImplemets implements FoodDao{

    public int create(FoodBean food) {
        int rows = 0;
        String query = "INSERT INTO Food (foodID, name, type, foodSize, quantity, price, storeID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            String newFoodID = IdGenerator.nextFoodId();
            ps.setString(1, newFoodID);
            ps.setString(2, food.getName());
            ps.setString(3, food.getType());
            ps.setString(4, food.getFoodSize());
            ps.setInt(5, food.getQuantity());
            ps.setDouble(6, food.getPrice());
            ps.setString(7, food.getStoreID());

            rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Food added successfully! (ID: " + newFoodID + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    
    public FoodBean findById(String foodID) {
        FoodBean food = null;
        String query = "SELECT * FROM Food WHERE foodID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, foodID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                food = new FoodBean();
                food.setFoodID(rs.getString("foodID"));
                food.setName(rs.getString("name"));
                food.setType(rs.getString("type"));
                food.setFoodSize(rs.getString("foodSize"));
                food.setQuantity(rs.getInt("quantity"));
                food.setPrice(rs.getDouble("price"));
                food.setStoreID(rs.getString("storeID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }   
    public List<FoodBean> findAll() {
        List<FoodBean> foodList = new ArrayList<>();
        String query = "SELECT * FROM Food";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                FoodBean food = new FoodBean();
                food.setFoodID(rs.getString("foodID"));
                food.setName(rs.getString("name"));
                food.setType(rs.getString("type"));
                food.setFoodSize(rs.getString("foodSize"));
                food.setQuantity(rs.getInt("quantity"));
                food.setPrice(rs.getDouble("price"));
                food.setStoreID(rs.getString("storeID"));
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    
    public List<FoodBean>findByStore(String storeID) {
        List<FoodBean> foodList = new ArrayList<>();
        String query = "SELECT * FROM Food WHERE storeID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, storeID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FoodBean food = new FoodBean();
                food.setFoodID(rs.getString("foodID"));
                food.setName(rs.getString("name"));
                food.setType(rs.getString("type"));
                food.setFoodSize(rs.getString("foodSize"));
                food.setQuantity(rs.getInt("quantity"));
                food.setPrice(rs.getDouble("price"));
                food.setStoreID(rs.getString("storeID"));
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    
    public int update(FoodBean food) {
        int rows = 0;
        String query = "UPDATE Food SET name=?, type=?, foodSize=?, quantity=?, price=?, storeID=? WHERE foodID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, food.getName());
            ps.setString(2, food.getType());
            ps.setString(3, food.getFoodSize());
            ps.setInt(4, food.getQuantity());
            ps.setDouble(5, food.getPrice());
            ps.setString(6, food.getStoreID());
            ps.setString(7, food.getFoodID());

            rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println(" Food updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    
    public int delete(String foodID) {
        int rows = 0;
        String query = "DELETE FROM Food WHERE foodID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, foodID);
            rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println(" Food deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }


    public List<FoodBean>findByType(String type) {
        List<FoodBean> foodList = new ArrayList<>();
        String query = "SELECT * FROM Food WHERE storeID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FoodBean food = new FoodBean();
                food.setFoodID(rs.getString("foodID"));
                food.setName(rs.getString("name"));
                food.setType(rs.getString("type"));
                food.setFoodSize(rs.getString("foodSize"));
                food.setQuantity(rs.getInt("quantity"));
                food.setPrice(rs.getDouble("price"));
                food.setStoreID(rs.getString("storeID"));
                foodList.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }
}
