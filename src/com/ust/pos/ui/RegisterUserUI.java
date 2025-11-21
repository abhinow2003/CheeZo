package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;

import com.ust.pos.bean.CredentialBean;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.dao.data.CredentialDaoImplements;
import com.ust.pos.dao.data.ProfileDaoImplements;

public class RegisterUserUI extends JFrame {

    private JTextField txtFirstName, txtLastName, txtDOB, txtStreet, txtLocation, txtCity,
            txtState, txtPincode, txtMobile, txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbGender;
    private JButton btnRegister, btnBack;

    // THEME COLORS
    private final Color ORANGE = new Color(255, 120, 0);
    private final Color LIGHT_FIELD = new Color(250, 250, 250);

    public RegisterUserUI() {
        setTitle("Cheezo Registration");
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Create Your Cheezo Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(ORANGE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        addField(formPanel, gbc, "First Name:", txtFirstName = new JTextField(20), y++);
        addField(formPanel, gbc, "Last Name:", txtLastName = new JTextField(20), y++);
        addField(formPanel, gbc, "Date of Birth (yyyy-MM-dd):", txtDOB = new JTextField(20), y++);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(createLabel("Gender:"), gbc);

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
        buttonPanel.setBackground(Color.WHITE);

        btnRegister = new JButton("Register");
        btnBack = new JButton("Back to Login");

        styleButton(btnRegister, ORANGE, Color.WHITE);
        styleButton(btnBack, Color.WHITE, ORANGE);
        btnBack.setBorder(BorderFactory.createLineBorder(ORANGE, 2));

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = y;
        formPanel.add(buttonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel);

        // ACTIONS
        btnRegister.addActionListener(this::handleRegister);

        btnBack.addActionListener(e -> {
            dispose();
            new LoginPage(null).setVisible(true);
        });

        setVisible(true);
    }

    private void handleRegister(ActionEvent e) {
        try {
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

            if (firstName.isEmpty() || emailID.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "⚠ Please fill all required fields.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProfileBean profile = new ProfileBean();
            profile.setFirstName(firstName);
            profile.setLastName(lastName);
            profile.setDateOfBirth(sqlDate);
            profile.setGender(gender);
            profile.setStreet(street);
            profile.setLocation(location);
            profile.setCity(city);
            profile.setState(state);
            profile.setPincode(pincode);
            profile.setMobileNo(mobileNo);
            profile.setEmailID(emailID);
            profile.setPassword(password);

            CredentialBean creds = new CredentialBean();
            creds.setPassword(password);
            creds.setUserType("user");
            creds.setLoginStatus(0);
            creds.setEmail(emailID);

            ProfileDaoImplements dao = new ProfileDaoImplements();
            String generatedUserID = dao.register(profile, creds);

            JOptionPane.showMessageDialog(this,
                    "✅ Registration Successful!\nYour User ID: " + generatedUserID);

            dispose();
            new LoginPage(null).setVisible(true);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error: " + ex.getMessage(),
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
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
        label.setForeground(new Color(80, 80, 80));
        return label;
    }

    private void styleField(JComponent field) {
    field.setFont(new Font("Arial", Font.PLAIN, 15));
    field.setBackground(Color.WHITE);
    field.setForeground(Color.BLACK);

    // 🔥 ORANGE BORDER
    field.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 0), 2));

    if (field instanceof JTextField || field instanceof JPasswordField) {
        ((JTextField) field).setCaretColor(Color.BLACK);
        ((JTextField) field).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 120, 0), 2),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }
}


    private void styleButton(JButton button, Color bg, Color fg) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setPreferredSize(new Dimension(180, 45));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterUserUI::new);
    }
}
