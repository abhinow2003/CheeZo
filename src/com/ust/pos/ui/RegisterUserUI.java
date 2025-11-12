package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.data.ProfileData;

public class RegisterUserUI extends JFrame {

    private JTextField txtUserId, txtFirstName, txtLastName, txtDOB, txtStreet, txtLocation, txtCity,
            txtState, txtPincode, txtMobile, txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbGender;
    private JButton btnRegister, btnBack;

    public RegisterUserUI() {
        setTitle("User Registration");
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Background panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(45, 45, 48));

        // Title
        JLabel titleLabel = new JLabel("Create Your Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(45, 45, 48));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // Helper to add labels and fields
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;
        addField(formPanel, gbc, "User ID:", txtUserId = new JTextField(20), y++);

        addField(formPanel, gbc, "First Name:", txtFirstName = new JTextField(20), y++);
        addField(formPanel, gbc, "Last Name:", txtLastName = new JTextField(20), y++);
        addField(formPanel, gbc, "Date of Birth (yyyy-MM-dd):", txtDOB = new JTextField(20), y++);

        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel lblGender = createLabel("Gender:");
        formPanel.add(lblGender, gbc);
        gbc.gridx = 1;
        cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        styleField(cmbGender);
        formPanel.add(cmbGender, gbc);
        y++;

        addField(formPanel, gbc, "Street:", txtStreet = new JTextField(20), y++);
        addField(formPanel, gbc, "Location:", txtLocation = new JTextField(20), y++);
        addField(formPanel, gbc, "City:", txtCity = new JTextField(20), y++);
        addField(formPanel, gbc, "State:", txtState = new JTextField(20), y++);
        addField(formPanel, gbc, "Pincode:", txtPincode = new JTextField(20), y++);
        addField(formPanel, gbc, "Mobile No:", txtMobile = new JTextField(20), y++);
        addField(formPanel, gbc, "Email ID:", txtEmail = new JTextField(20), y++);
        addField(formPanel, gbc, "Password:", txtPassword = new JPasswordField(20), y++);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(45, 45, 48));

        btnRegister = new JButton("Register");
        btnBack = new JButton("Back to Login");

        styleButton(btnRegister, new Color(0, 120, 215));
        styleButton(btnBack, new Color(80, 80, 80));

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(buttonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(45, 45, 48));

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel);

        // Actions
        btnRegister.addActionListener(this::handleRegister);
        btnBack.addActionListener(e -> {
            dispose();
            new LoginPage(null).setVisible(true);
        });

        setVisible(true);
    }

    private void handleRegister(ActionEvent e) {
        try {
            String userID = txtUserId.getText().trim();
            String firstName = txtFirstName.getText().trim();
            String lastName = txtLastName.getText().trim();
            String dobText = txtDOB.getText().trim();
            Date sqlDate = Date.valueOf(dobText);
            String gender = (String) cmbGender.getSelectedItem();
            String street = txtStreet.getText().trim();
            String location = txtLocation.getText().trim();
            String city = txtCity.getText().trim();
            String state = txtState.getText().trim();
            String pincode = txtPincode.getText().trim();
            String mobileNo = txtMobile.getText().trim();
            String emailID = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (userID.isEmpty() || firstName.isEmpty() || emailID.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProfileBean profile = new ProfileBean(userID, firstName, lastName, sqlDate, gender,
                    street, location, city, state, pincode, mobileNo, emailID, password);

            // Example save (replace with DAO or real persistence)
            ProfileData.getProfiles().add(profile);

            JOptionPane.showMessageDialog(this, "✅ Registration Successful!\nWelcome, " + firstName + "!");
            dispose();
            new LoginPage(null).setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(createLabel(labelText), gbc);
        gbc.gridx = 1;
        styleField(field);
        panel.add(field, gbc);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void styleField(JComponent field) {
        field.setFont(new Font("Arial", Font.PLAIN, 15));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        if (field instanceof JTextField || field instanceof JPasswordField) {
            ((JTextField) field).setCaretColor(Color.WHITE);
            ((JTextField) field).setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        }
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(160, 40));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterUserUI::new);
    }
}
