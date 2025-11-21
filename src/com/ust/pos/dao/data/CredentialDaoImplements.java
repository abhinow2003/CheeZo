package com.ust.pos.dao.data;
import com.ust.pos.bean.CredentialBean;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.dao.domain.CredentialDao;
import com.ust.pos.util.DBConnection;

import java.sql.*;

public class CredentialDaoImplements implements CredentialDao {

    @Override
    public int create(CredentialBean creds) {
        int rows = 0;
        String query = "INSERT INTO Credential (userID, emailID, password, userType, loginStatus) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, creds.getUserID());
            ps.setString(2, creds.getEmail());
            ps.setString(3, creds.getPassword());
            ps.setString(4, creds.getUserType());
            ps.setInt(5, creds.getLoginStatus());

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
                creds.setEmail(rs.getString("emailID"));
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
        String query = "UPDATE Credential SET emailID=?, password=?, userType=?, loginStatus=? WHERE userID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, creds.getEmail());
            ps.setString(2, creds.getPassword());
            ps.setString(3, creds.getUserType());
            ps.setInt(4, creds.getLoginStatus());
            ps.setString(5, creds.getUserID());

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

    // -------------------- NEW METHODS --------------------

    @Override
    public CredentialBean authenticate(String email, String password) {
        CredentialBean creds = null;
        String query = "SELECT * FROM Credential WHERE emailID=? AND password=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                creds = new CredentialBean();
                creds.setUserID(rs.getString("userID"));
                creds.setEmail(rs.getString("emailID"));
                creds.setPassword(rs.getString("password"));
                creds.setUserType(rs.getString("userType"));
                creds.setLoginStatus(rs.getInt("loginStatus"));

                // Set login status to logged-in
                updateLoginStatus(creds.getUserID(), 1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creds;
    }

    @Override
    public boolean logout(String userID) {
        return updateLoginStatus(userID, 0);
    }

    @Override
    public boolean updateLoginStatus(String userID, int status) {
        String query = "UPDATE Credential SET loginStatus=? WHERE userID=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, status);
            ps.setString(2, userID);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ProfileBean findProfileByUserId(String userID) {

    String query = "SELECT * FROM Profile WHERE userID=?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {

        ps.setString(1, userID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            ProfileBean profile = new ProfileBean();

            profile.setUserID(rs.getString("userID"));
            profile.setFirstName(rs.getString("firstName"));
            profile.setLastName(rs.getString("lastName"));
            profile.setDateOfBirth(rs.getDate("dateOfBirth"));
            profile.setGender(rs.getString("gender"));
            profile.setStreet(rs.getString("street"));
            profile.setLocation(rs.getString("location"));
            profile.setCity(rs.getString("city"));
            profile.setState(rs.getString("state"));
            profile.setPincode(rs.getString("pincode"));
            profile.setMobileNo(rs.getString("mobileNo"));
            profile.setEmailID(rs.getString("emailID"));
            profile.setPassword(rs.getString("password"));

            return profile;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

}
