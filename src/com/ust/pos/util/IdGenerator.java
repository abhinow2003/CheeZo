package com.ust.pos.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IdGenerator {

    
    private static String getNextId(String tableName, String columnName, String prefix) {
        String nextId = null;
        String query = "SELECT " + columnName + " FROM " + tableName + 
                       " ORDER BY " + columnName + " DESC LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastId = rs.getString(columnName);
                // Example: ST1005 → extract number
                int number = Integer.parseInt(lastId.replaceAll("\\D", ""));
                number++;
                nextId = prefix + number;
            } else {
                // No records yet — start from 1001
                nextId = prefix + "1001";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Fallback if DB fails
            nextId = prefix + "1001";
        }
        return nextId;
    }

    // ✅ For each entity type
    public static String nextUserId() {
        return getNextId("Profile", "userID", "US");
    }

    public static String nextStoreId() {
        return getNextId("Store", "storeID", "ST");
    }

    public static String nextFoodId() {
        return getNextId("Food", "foodID", "FD");
    }

    public static String nextOrderId() {
        return getNextId("Orders", "orderID", "OR");
    }

    public static String nextCartId() {
        return getNextId("Cart", "cartID", "CT");
    }
}
