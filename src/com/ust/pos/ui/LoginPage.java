package com.ust.pos.ui;

import com.ust.pos.bean.CredentialBean;
import com.ust.pos.dao.data.CredentialDaoImplements;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {

    public interface LoginListener {
        void onLoginSuccess(CredentialBean cred);
    }

    private LoginListener listener;

    public LoginPage(LoginListener listener) {
        this.listener = listener;

        setTitle("Cheezo Login");
        setSize(480, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // MAIN BACKGROUND — WHITE
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        // -------------------- LOGO --------------------
        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icon = new ImageIcon("images/cheezo.png");
        Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));

        contentPanel.add(logo, BorderLayout.NORTH);

        // -------------------- FORM PANEL --------------------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title — Orange
        JLabel titleLabel = new JLabel("Login to Cheezo");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(new Color(255, 120, 0)); // Cheezo Orange
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(titleLabel, gbc);

        // EMAIL LABEL
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(new Color(120, 120, 120));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);

        // EMAIL FIELD — Orange border
        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 0), 2));
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // PASSWORD LABEL
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(new Color(120, 120, 120));
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        // PASSWORD FIELD — Orange border
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 0), 2));
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // LOGIN BUTTON — Orange filled
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(255, 120, 0));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(loginButton, gbc);

        // REGISTER BUTTON — white with orange border
        JButton registerButton = new JButton("Create Account");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 15));
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(255, 120, 0));
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 0), 2));
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(160, 40));
        gbc.gridy = 4;
        formPanel.add(registerButton, gbc);

        contentPanel.add(formPanel, BorderLayout.CENTER);

        // REGISTER ACTION
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterUserUI();
        });

        // LOGIN ACTION
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Please enter both email and password.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                CredentialBean cred = authenticate(email, password);

                if (cred != null) {
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Welcome back, " + cred.getUserID() + "!");
                    dispose();
                    if (listener != null) listener.onLoginSuccess(cred);
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Invalid email or password.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // DAO AUTHENTICATION
    private CredentialBean authenticate(String email, String password) {
        CredentialDaoImplements dao = new CredentialDaoImplements();
        return dao.authenticate(email, password);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new LoginPage(user -> System.out.println("Logged in: " + user.getUserID()))
                        .setVisible(true)
        );
    }
}
