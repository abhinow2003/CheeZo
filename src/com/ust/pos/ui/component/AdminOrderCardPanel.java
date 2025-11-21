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
import com.ust.pos.dao.data.OrderDaoImplements;
import com.ust.pos.dao.data.PaymentDaoImplements;

public class AdminOrderCardPanel extends JPanel {

    private OrderDaoImplements orderDao = new OrderDaoImplements();
    private PaymentDaoImplements paymentDao = new PaymentDaoImplements();
    private CartDaoImplements cartDao = new CartDaoImplements();
    private FoodDaoImplemets foodDao = new FoodDaoImplemets();
    private StoreDaoImplements storeDao = new StoreDaoImplements();

    public AdminOrderCardPanel(OrderBean order, Runnable onRefresh) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(530, 210));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // ===== FETCH FOOD & STORE DETAILS =====
        CartBean cartBean = cartDao.findById(order.getCartID());
        FoodBean food = (cartBean != null) ? foodDao.findById(cartBean.getFoodID()) : null;
        StoreBean store = storeDao.findById(order.getStoreID());

        String foodName = (food != null) ? food.getName() : "Unknown Food";
        String storeName = (store != null) ? store.getName() : "Unknown Store";

        // LEFT PANEL DETAILS
        JPanel left = new JPanel(new GridLayout(9, 1, 3, 3));
        left.setOpaque(false);

        left.add(new JLabel("Order ID: " + order.getOrderID()));
        left.add(new JLabel("User ID: " + order.getUserID()));
        left.add(new JLabel("Food: " + foodName));
        left.add(new JLabel("Store: " + storeName));
        left.add(new JLabel("Card Used: " + order.getCardNumber()));
        left.add(new JLabel("Amount: ₹" + order.getTotalPrice()));
        left.add(new JLabel("Date: " + order.getOrderDate()));
        left.add(new JLabel("Status: " + order.getOrderStatus()));

        // RIGHT BUTTONS
        JPanel right = new JPanel(new GridLayout(3, 1, 5, 5));
        right.setOpaque(false);

        JButton btnAccept = new JButton("Accept");
        JButton btnDelivered = new JButton("Delivered");
        JButton btnDecline = new JButton("Decline");

        String status = order.getOrderStatus().toLowerCase().trim();

        btnAccept.setEnabled(status.equals("paid"));
        btnDelivered.setEnabled(status.equals("accepted"));
        btnDecline.setEnabled(status.equals("pending") || status.equals("paid"));

        // ACCEPT
        btnAccept.addActionListener(e -> {
            order.setOrderStatus("accepted");
            orderDao.update(order);
            JOptionPane.showMessageDialog(this, "Order Accepted!");
            onRefresh.run();
        });

        // DELIVERED
        btnDelivered.addActionListener(e -> {
            order.setOrderStatus("delivered");
            orderDao.update(order);
            JOptionPane.showMessageDialog(this, "Order Delivered!");
            onRefresh.run();
        });

        // DECLINE + REFUND
        btnDecline.addActionListener(e -> {

            if (status.equals("paid")) {
                boolean refunded = paymentDao.refund(order.getCardNumber(), order.getTotalPrice());

                if (!refunded) {
                    JOptionPane.showMessageDialog(this,
                            "Refund Failed (Check Payment DB)",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            order.setOrderStatus("declined");
            orderDao.update(order);

            JOptionPane.showMessageDialog(this,
                    "Order Declined" + (status.equals("paid") ? " & Refunded!" : "!"));

            onRefresh.run();
        });

        right.add(btnAccept);
        right.add(btnDelivered);
        right.add(btnDecline);

        card.add(left, BorderLayout.WEST);
        card.add(right, BorderLayout.EAST);
        add(card);
    }
}
