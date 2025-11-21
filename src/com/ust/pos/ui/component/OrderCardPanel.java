package com.ust.pos.ui.component;

import javax.swing.*;
import java.awt.*;

import com.ust.pos.bean.OrderBean;
import com.ust.pos.bean.CartBean;
import com.ust.pos.bean.FoodBean;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.dao.data.CartDaoImplements;
import com.ust.pos.dao.data.FoodDaoImplemets;
import com.ust.pos.dao.data.StoreDaoImplements;

public class OrderCardPanel extends JPanel {

    private CartDaoImplements cartDao = new CartDaoImplements();
    private FoodDaoImplemets foodDao = new FoodDaoImplemets();
    private StoreDaoImplements storeDao = new StoreDaoImplements();

    public OrderCardPanel(OrderBean order) {

        // =======================
        // FETCH FOOD & STORE
        // =======================
        CartBean cart = cartDao.findById(order.getCartID());
        FoodBean food = (cart != null) ? foodDao.findById(cart.getFoodID()) : null;
        StoreBean store = storeDao.findById(order.getStoreID());

        String foodName = (food != null) ? food.getName() : "Unknown Food";
        String storeName = (store != null) ? store.getName() : "Unknown Store";

        // =======================
        // UI CARD LAYOUT
        // =======================
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 190));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // =======================
        // LEFT COLUMN DETAILS
        // =======================
        JPanel left = new JPanel(new GridLayout(7, 1, 4, 4));
        left.setOpaque(false);

        left.add(new JLabel("Order ID: " + order.getOrderID()));
        left.add(new JLabel("Food: " + foodName));
        left.add(new JLabel("Store: " + storeName));
        left.add(new JLabel("Date: " + order.getOrderDate()));
        left.add(new JLabel("City: " + order.getCity()));
        left.add(new JLabel("Pincode: " + order.getPincode()));

        // =======================
        // RIGHT COLUMN (PRICE + STATUS)
        // =======================
        JPanel right = new JPanel(new GridLayout(2, 1, 5, 5));
        right.setOpaque(false);

        JLabel lblPrice = new JLabel("₹ " + order.getTotalPrice());
        lblPrice.setFont(new Font("Arial", Font.BOLD, 20));
        lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel lblStatus = new JLabel(order.getOrderStatus().toUpperCase(), SwingConstants.RIGHT);
        lblStatus.setOpaque(true);
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        // =======================
        // STATUS COLOR
        // =======================
        String status = order.getOrderStatus().toLowerCase().trim();

        switch (status) {
            case "pending":
            case "paid":
                lblStatus.setBackground(new Color(255, 204, 0)); // Yellow
                lblStatus.setForeground(Color.BLACK);
                break;

            case "accepted":
                lblStatus.setBackground(new Color(0, 170, 0)); // Green
                break;

            case "delivered":
                lblStatus.setBackground(new Color(0, 120, 255)); // Blue
                break;

            case "declined":
                lblStatus.setBackground(Color.RED);
                break;

            default:
                lblStatus.setBackground(Color.GRAY);
                break;
        }

        right.add(lblPrice);
        right.add(lblStatus);

        // =======================
        // ADD PANELS TO CARD
        // =======================
        cardPanel.add(left, BorderLayout.WEST);
        cardPanel.add(right, BorderLayout.EAST);

        add(cardPanel, BorderLayout.CENTER);
    }
}
