package com.ust.pos.dao.data;

import com.ust.pos.dao.domain.PaymentDao;
import com.ust.pos.bean.PaymentBean;
import com.ust.pos.util.DBConnection;

import java.sql.*;

public class PaymentDaoImplements implements PaymentDao {

    // =========================================================
    // ADD CARD
    // =========================================================
    @Override
    public String addCard(PaymentBean card) {
        String check = "SELECT creditCardNumber FROM Payment WHERE creditCardNumber=?";
        String insert = "INSERT INTO Payment (creditCardNumber, validFrom, validTo, balance, userID) "
                       + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            // Check duplicate card
            try (PreparedStatement ps = con.prepareStatement(check)) {
                ps.setString(1, card.getCreditCardNumber());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return "EXISTS";
            }

            // Insert card
            try (PreparedStatement ps = con.prepareStatement(insert)) {
                ps.setString(1, card.getCreditCardNumber());
                ps.setString(2, card.getValidFrom());
                ps.setString(3, card.getValidTo());
                ps.setDouble(4, card.getBalance());
                ps.setString(5, card.getUserID());

                ps.executeUpdate();
                return card.getCreditCardNumber();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // =========================================================
    // FIND CARD
    // =========================================================
    @Override
    public PaymentBean findByCardNumber(String cardNumber) {
        String sql = "SELECT * FROM Payment WHERE creditCardNumber=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cardNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PaymentBean card = new PaymentBean();
                card.setCreditCardNumber(rs.getString("creditCardNumber"));
                card.setValidFrom(rs.getString("validFrom"));
                card.setValidTo(rs.getString("validTo"));
                card.setBalance(rs.getDouble("balance"));
                card.setUserID(rs.getString("userID"));
                return card;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =========================================================
    // UPDATE CARD DETAILS
    // =========================================================
    @Override
    public boolean update(PaymentBean card) {
        String sql = "UPDATE Payment SET validFrom=?, validTo=?, balance=?, userID=? "
                   + "WHERE creditCardNumber=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, card.getValidFrom());
            ps.setString(2, card.getValidTo());
            ps.setDouble(3, card.getBalance());
            ps.setString(4, card.getUserID());
            ps.setString(5, card.getCreditCardNumber());

            return ps.executeUpdate() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // =========================================================
    // PROCESS PAYMENT
    // =========================================================
    @Override
    public boolean processPayment(String cardNumber, double amount) {

        PaymentBean card = findByCardNumber(cardNumber);
        if (card == null) return false;

        if (card.getBalance() < amount) return false;

        // Deduct
        card.setBalance(card.getBalance() - amount);

        return update(card);
    }

    // =========================================================
    // REFUND
    // =========================================================
    @Override
    public boolean refund(String cardNumber, double amount) {
        PaymentBean card = findByCardNumber(cardNumber);
        if (card == null) return false;

        card.setBalance(card.getBalance() + amount);
        return update(card);
    }
}
