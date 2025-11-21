package com.ust.pos.dao.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ust.pos.bean.ProfileBean;
import com.ust.pos.bean.CredentialBean;
import com.ust.pos.dao.domain.ProfileDao;
import com.ust.pos.util.DBConnection;
import com.ust.pos.util.IdGenerator;

public class ProfileDaoImplements implements ProfileDao {

    @Override
    public String register(ProfileBean profile, CredentialBean creds) {
        String userId = IdGenerator.nextUserId();
        profile.setUserID(userId);
        creds.setUserID(userId);

        String profileQuery = "INSERT INTO Profile (userID, firstName, lastName, dateOfBirth, gender, street, location, city, state, pincode, mobileNo, emailID, password) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String credsQuery = "INSERT INTO Credential (userID,emailId, password, userType, loginStatus) VALUES (?,?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false); 

            try (PreparedStatement psProfile = con.prepareStatement(profileQuery);
                 PreparedStatement psCreds = con.prepareStatement(credsQuery)) {                
                psProfile.setString(1, profile.getUserID());
                psProfile.setString(2, profile.getFirstName());
                psProfile.setString(3, profile.getLastName());
                psProfile.setDate(4, profile.getDateOfBirth());
                psProfile.setString(5, profile.getGender());
                psProfile.setString(6, profile.getStreet());
                psProfile.setString(7, profile.getLocation());
                psProfile.setString(8, profile.getCity());
                psProfile.setString(9, profile.getState());
                psProfile.setString(10, profile.getPincode());
                psProfile.setString(11, profile.getMobileNo());
                psProfile.setString(12, profile.getEmailID());
                psProfile.setString(13, profile.getPassword());
                psProfile.executeUpdate();

                
                psCreds.setString(1, creds.getUserID());
                psCreds.setString(2, creds.getEmail());
                psCreds.setString(3, creds.getPassword());
                psCreds.setString(4, creds.getUserType());
                psCreds.setInt(5, creds.getLoginStatus());
                psCreds.executeUpdate();
                con.commit(); 
                System.out.println("✅ User registered successfully! (User ID: " + userId + ")");
            } catch (SQLException e) {
                con.rollback(); 
                e.printStackTrace();
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    @Override
    public ProfileBean findProfileById(String userId) {
        ProfileBean profile = null;
        String query = "SELECT * FROM Profile WHERE userID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                profile = new ProfileBean();
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }

    @Override
    public CredentialBean findCredentialsById(String userId) {
        CredentialBean creds = null;
        String query = "SELECT * FROM Credential WHERE userID = ?";

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
    public boolean updateProfile(ProfileBean profile) {
        boolean updated = false;
        String query = "UPDATE Profile SET firstName=?, lastName=?, dateOfBirth=?, gender=?, street=?, location=?, city=?, state=?, pincode=?, mobileNo=?, emailID=?, password=? WHERE userID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, profile.getFirstName());
            ps.setString(2, profile.getLastName());
            ps.setDate(3, profile.getDateOfBirth());
            ps.setString(4, profile.getGender());
            ps.setString(5, profile.getStreet());
            ps.setString(6, profile.getLocation());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getPincode());
            ps.setString(10, profile.getMobileNo());
            ps.setString(11, profile.getEmailID());
            ps.setString(12, profile.getPassword());
            ps.setString(13, profile.getUserID());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Profile updated successfully!");
                updated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    @Override
    public boolean updateCredentials(CredentialBean creds) {
        boolean updated = false;
        String query = "UPDATE Credential SET password=?, userType=?, loginStatus=? WHERE userID=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, creds.getPassword());
            ps.setString(2, creds.getUserType());
            ps.setInt(3, creds.getLoginStatus());
            ps.setString(4, creds.getUserID());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Credentials updated successfully!");
                updated = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }
    @Override
public ProfileBean authenticate(String email, String password) {
    ProfileBean profile = null;
    String query = "SELECT * FROM Profile WHERE emailID = ? AND password = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {

        ps.setString(1, email);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            profile = new ProfileBean();
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
        }

    } catch (SQLException e) {
        System.out.println("❌ Error during authentication: " + e.getMessage());
    }

    return profile;
}

    @Override
    public List<ProfileBean> findAllProfiles() {
        List<ProfileBean> profiles = new ArrayList<>();
        String query = "SELECT * FROM Profile";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
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
                profiles.add(profile);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }
}
