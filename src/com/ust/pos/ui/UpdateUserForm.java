package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.dao.data.ProfileDaoImplements;

public class UpdateUserForm extends JFrame {

    private JTextField txtUserId, txtFirstName, txtLastName, txtEmail, txtMobile, txtCity, txtState;
    private JButton btnLoad, btnUpdate;

    private ProfileDaoImplements profileDao = new ProfileDaoImplements();
    private ProfileBean loadedUser = null;
    private AdminPanel parent;
public UpdateUserForm(AdminPanel parent) {
        this.parent = parent;
        setTitle("Update User");
        setSize(400, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Fields
        txtUserId = new JTextField();
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtEmail = new JTextField();
        txtMobile = new JTextField();
        txtCity = new JTextField();
        txtState = new JTextField();

        btnLoad = new JButton("Load User");
        btnUpdate = new JButton("Update User");
        btnUpdate.setEnabled(false);

        // Add Components
        panel.add(new JLabel("User ID:"));
        panel.add(txtUserId);

        panel.add(btnLoad);
        panel.add(new JLabel(""));

        panel.add(new JLabel("First Name:"));
        panel.add(txtFirstName);

        panel.add(new JLabel("Last Name:"));
        panel.add(txtLastName);

        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);

        panel.add(new JLabel("Mobile:"));
        panel.add(txtMobile);

        panel.add(new JLabel("City:"));
        panel.add(txtCity);

        panel.add(new JLabel("State:"));
        panel.add(txtState);

        panel.add(btnUpdate);
        panel.add(new JLabel(""));

        add(panel);

        // ------------------ ACTIONS ----------------------

        // Load user details
        btnLoad.addActionListener(e -> {
            String id = txtUserId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a User ID");
                return;
            }

            loadedUser = profileDao.findProfileById(id);

            if (loadedUser == null) {
                JOptionPane.showMessageDialog(this, "❌ User not found!");
                return;
            }

            // Fill form fields
            txtFirstName.setText(loadedUser.getFirstName());
            txtLastName.setText(loadedUser.getLastName());
            txtEmail.setText(loadedUser.getEmailID());
            txtMobile.setText(loadedUser.getMobileNo());
            txtCity.setText(loadedUser.getCity());
            txtState.setText(loadedUser.getState());

            btnUpdate.setEnabled(true);
        });

        // Update user
        btnUpdate.addActionListener(e -> {
    if (loadedUser == null) return;

    loadedUser.setFirstName(txtFirstName.getText());
    loadedUser.setLastName(txtLastName.getText());
    loadedUser.setEmailID(txtEmail.getText());
    loadedUser.setMobileNo(txtMobile.getText());
    loadedUser.setCity(txtCity.getText());
    loadedUser.setState(txtState.getText());

    boolean updated = profileDao.updateProfile(loadedUser);

    if (updated) {
        JOptionPane.showMessageDialog(this, "✅ User updated successfully!");

        String output = """
                ✅ USER UPDATED SUCCESSFULLY

                ID: %s
                Name: %s %s
                Email: %s
                Mobile: %s
                City: %s
                State: %s
                """.formatted(
                loadedUser.getUserID(),
                loadedUser.getFirstName(), loadedUser.getLastName(),
                loadedUser.getEmailID(),
                loadedUser.getMobileNo(),
                loadedUser.getCity(),
                loadedUser.getState()
        );

        parent.showUserResult(output); // <-- update result screen
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "❌ Update failed");
    }
});


        setVisible(true);
    }
}

