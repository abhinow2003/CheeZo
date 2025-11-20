package com.ust.pos.dao.data;

import com.ust.pos.bean.CredentialBean;
import com.ust.pos.dao.domain.CredentialDao;
import com.ust.pos.util.DBConnection;

import java.sql.*;

public class CredentialDaoImplements implements CredentialDao {

    @Override
    public int create(CredentialBean creds) {
        int rows = 0;
        String query = "INSERT INTO Credential (userID, password, userType, loginStatus) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, creds.getUserID());
            ps.setString(2, creds.getPassword());
            ps.setString(3, creds.getUserType());
            ps.setInt(4, creds.getLoginStatus());

            rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Credentials created for UserID: " + creds.getUserID());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    @Override
    public CredentialBean findByUserId(String userId) {
        CredentialBean creds = null;
        String query = "SELECT * FROM Credential WHERE userID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                creds = new CredentialBean();
                creds.setUserID(rs.getString("userID"));
                creds.setPassword(rs.getString("password"));
                creds.setUserType(rs.getString("userType"));
                creds.setLoginStatus(rs.getInt("loginStatus"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creds;
    }

    @Override
    public int update(CredentialBean creds) {
        int rows = 0;
        String query = "UPDATE Credential SET password=?, userType=?, loginStatus=? WHERE userID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, creds.getPassword());
            ps.setString(2, creds.getUserType());
            ps.setInt(3, creds.getLoginStatus());
            ps.setString(4, creds.getUserID());

            rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("🔄 Credentials updated for UserID: " + creds.getUserID());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    @Override
    public int delete(String userId) {
        int rows = 0;
        String query = "DELETE FROM Credential WHERE userID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, userId);

            rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("🗑️ Credentials deleted for UserID: " + userId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    @Override
    public CredentialBean authenticate(String userId, String password) {
        CredentialBean creds = null;
        String query = "SELECT * FROM Credential WHERE userID=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, userId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                creds = new CredentialBean();
                creds.setUserID(rs.getString("userID"));
                creds.setPassword(rs.getString("password"));
                creds.setUserType(rs.getString("userType"));
                creds.setLoginStatus(rs.getInt("loginStatus"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creds;
    }
}
