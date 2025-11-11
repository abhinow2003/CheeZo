package com.ust.pos.dao.data;
import com.ust.pos.dao.domain.StoreDao;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDaoImplements implements StoreDao {

    @Override
  public int create(StoreBean store) {
        int rows = 0;
        String query = "INSERT INTO Store (storeID, name, street, mobileNo, city, state, pincode) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, store.getStoreID());
            ps.setString(2, store.getName());
            ps.setString(3, store.getStreet());
            ps.setString(4, store.getMobileNo());
            ps.setString(5, store.getCity());
            ps.setString(6, store.getState());
            ps.setString(7, store.getPincode());

            rows = ps.executeUpdate();
            System.out.println(" Store added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

     public int update(StoreBean store) {
        int rows = 0;
        String query = "UPDATE Store SET name=?, street=?, mobileNo=?, city=?, state=?, pincode=? WHERE storeID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, store.getName());
            ps.setString(2, store.getStreet());
            ps.setString(3, store.getMobileNo());
            ps.setString(4, store.getCity());
            ps.setString(5, store.getState());
            ps.setString(6, store.getPincode());
            ps.setString(7, store.getStoreID());

            rows = ps.executeUpdate();
            System.out.println(" Store updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }


    public int delete(String storeID) {
        int rows = 0;
        String query = "DELETE FROM Store WHERE storeID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, storeID);
            rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Store deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

     public StoreBean findById(String storeID) {
        StoreBean store = null;
        String query = "SELECT * FROM Store WHERE storeID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, storeID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                store = new StoreBean(
                    rs.getString("storeID"),
                    rs.getString("name"),
                    rs.getString("street"),
                    rs.getString("mobileNo"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("pincode")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return store;
    }


     public List<StoreBean> findAll() {
        List<StoreBean> storeList = new ArrayList<>();
        String query = "SELECT * FROM Store";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                StoreBean store = new StoreBean(
                    rs.getString("storeID"),
                    rs.getString("name"),
                    rs.getString("street"),
                    rs.getString("mobileNo"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("pincode")
                );
                storeList.add(store);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeList;
    }
    

    public List<StoreBean> findByCity(String city) {
        List<StoreBean> storeList = new ArrayList<>();
        String query = "SELECT * FROM Store WHERE city = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StoreBean store = new StoreBean(
                    rs.getString("storeID"),
                    rs.getString("name"),
                    rs.getString("street"),
                    rs.getString("mobileNo"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("pincode")
                );
                storeList.add(store);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeList;
    }
}
