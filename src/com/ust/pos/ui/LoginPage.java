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

        setTitle("User Login");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(45, 45, 48));



        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icon = new ImageIcon("images/cheezo.png");  // ← update your file name
        Image img = icon.getImage().getScaledInstance(280, 280, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));

        


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(45, 45, 48));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Login to Your Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(titleLabel, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 120, 215));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 40));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setBackground(new Color(60, 60, 60));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(120, 40));

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(loginButton, gbc);

        gbc.gridy = 4;
        formPanel.add(registerButton, gbc);

        contentPanel.add(formPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // ---------------- Register Button ----------------
        registerButton.addActionListener(e -> {
            dispose();
            new RegisterUserUI();
        });

        // ---------------- Login Button ----------------
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginPage.this, "⚠ Please enter both email and password.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                CredentialBean cred = authenticate(email, password);

                if ( cred!= null) {
                    JOptionPane.showMessageDialog(LoginPage.this, "✅ Login Successful! Welcome, " + cred.getEmail());
                    dispose();
                    if (listener != null) listener.onLoginSuccess(cred);
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "❌ Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // ---------------- AUTHENTICATION USING DAO ----------------
    private CredentialBean authenticate(String email, String password) {
        CredentialDaoImplements credentialDao = new CredentialDaoImplements();
        return credentialDao.authenticate(email, password);
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage(user -> {
            System.out.println("Logged in as: " + user.getUserID());
        }).setVisible(true));
    }
}
