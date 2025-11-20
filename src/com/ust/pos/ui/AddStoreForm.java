package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;

import com.ust.pos.bean.StoreBean;
import com.ust.pos.dao.data.StoreDaoImplements;
import com.ust.pos.util.IdGenerator;

public class AddStoreForm extends JFrame {

    private AdminPanel parent;

    public AddStoreForm(AdminPanel parent) {
        this.parent = parent;
        setTitle("Add Store");
        setSize(400, 500);
        setLocationRelativeTo(null);

        StoreDaoImplements dao = new StoreDaoImplements();

        // AUTO STORE ID
        String generatedId = IdGenerator.nextStoreId();

        JTextField txtId = new JTextField(generatedId);
        txtId.setEditable(false);           // 🔒 User cannot change ID
        txtId.setBackground(Color.LIGHT_GRAY);

        JTextField txtName = new JTextField();
        JTextField txtStreet = new JTextField();
        JTextField txtMobile = new JTextField();
        JTextField txtCity = new JTextField();
        JTextField txtState = new JTextField();
        JTextField txtPin = new JTextField();

        JButton btnSubmit = new JButton("Save Store");

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Store ID (Auto):"));
        panel.add(txtId);

        panel.add(new JLabel("Name:"));
        panel.add(txtName);

        panel.add(new JLabel("Street:"));
        panel.add(txtStreet);

        panel.add(new JLabel("Mobile No:"));
        panel.add(txtMobile);

        panel.add(new JLabel("City:"));
        panel.add(txtCity);

        panel.add(new JLabel("State:"));
        panel.add(txtState);

        panel.add(new JLabel("Pincode:"));
        panel.add(txtPin);

        panel.add(btnSubmit);
        add(panel);

        btnSubmit.addActionListener(e -> {

            StoreBean s = new StoreBean(
                    generatedId,                 // auto ID passed here
                    txtName.getText(),
                    txtStreet.getText(),
                    txtMobile.getText(),
                    txtCity.getText(),
                    txtState.getText(),
                    txtPin.getText()
            );

            int rows = dao.create(s);

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✔ Store Added Successfully!");

                // Send to Store result screen
                parent.showStoreResult(parent.formatStore(s));

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to add store.");
            }
        });

        setVisible(true);
    }
}
