package com.ust.pos.ui.component;

import javax.swing.*;
import java.awt.*;


import com.ust.pos.bean.PaymentBean;

public class PaymentCardPanel extends JPanel {

    public PaymentCardPanel(PaymentBean card, double amount, Runnable onPay) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 180));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setBackground(Color.WHITE);

        JPanel info = new JPanel(new GridLayout(4, 1));
        info.setBackground(Color.WHITE);

        info.add(new JLabel("Card: " + card.getCreditCardNumber()));
        info.add(new JLabel("Valid: " + card.getValidFrom() + " - " + card.getValidTo()));
        info.add(new JLabel("Balance: ₹" + card.getBalance()));
        info.add(new JLabel("Payable Amount: ₹" + amount));

        JButton btnPay = new JButton("Pay Using This Card");

        add(info, BorderLayout.CENTER);
        add(btnPay, BorderLayout.SOUTH);

        btnPay.addActionListener(e -> onPay.run());
    }
}

