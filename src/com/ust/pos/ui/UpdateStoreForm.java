package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;

import com.ust.pos.bean.StoreBean;
import com.ust.pos.dao.data.StoreDaoImplements;

public class UpdateStoreForm extends JFrame {

    private AdminPanel parent;
    private StoreBean loadedStore;

    public UpdateStoreForm(AdminPanel parent) {
        this.parent = parent;
        setTitle("Update Store");
        setSize(400, 500);
        setLocationRelativeTo(null);

        StoreDaoImplements dao = new StoreDaoImplements();

        JTextField txtId = new JTextField();
        JButton btnLoad = new JButton("Load Store");

        JTextField txtName = new JTextField();
        JTextField txtStreet = new JTextField();
        JTextField txtMobile = new JTextField();
        JTextField txtCity = new JTextField();
        JTextField txtState = new JTextField();
        JTextField txtPin = new JTextField();

        JButton btnUpdate = new JButton("Update Store");
        btnUpdate.setEnabled(false);

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Store ID:"));
        panel.add(txtId);

        panel.add(btnLoad);
        panel.add(new JLabel(""));

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

        panel.add(btnUpdate);
        panel.add(new JLabel(""));

        add(panel);

        // ---------------- LOAD STORE ----------------
        btnLoad.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a Store ID!");
                return;
            }

            loadedStore = dao.findById(id);

            if (loadedStore == null) {
                JOptionPane.showMessageDialog(this, "❌ Store Not Found!");
                return;
            }

            // Fill fields
            txtName.setText(loadedStore.getName());
            txtStreet.setText(loadedStore.getStreet());
            txtMobile.setText(loadedStore.getMobileNo());
            txtCity.setText(loadedStore.getCity());
            txtState.setText(loadedStore.getState());
            txtPin.setText(loadedStore.getPincode());

            btnUpdate.setEnabled(true);
        });

        // ---------------- UPDATE STORE ----------------
        btnUpdate.addActionListener(e -> {

            loadedStore.setName(txtName.getText());
            loadedStore.setStreet(txtStreet.getText());
            loadedStore.setMobileNo(txtMobile.getText());
            loadedStore.setCity(txtCity.getText());
            loadedStore.setState(txtState.getText());
            loadedStore.setPincode(txtPin.getText());

            int rows = dao.update(loadedStore);

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✔ Store Updated Successfully!");

                // SEND OUTPUT TO STORE TAB RESULT AREA
                parent.showStoreResult(parent.formatStore(loadedStore));

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Update Failed!");
            }
        });

        setVisible(true);
    }
}
