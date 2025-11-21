package com.ust.pos.ui;

import javax.swing.*;

import com.ust.pos.bean.CredentialBean;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.dao.data.CredentialDaoImplements;

import java.awt.*;

public class CheezoSplashScreen extends JWindow {

    private JProgressBar progressBar;

    public CheezoSplashScreen() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());

        // ===============================
        // CHEEZO LOGO (put your image in /images/)
        // ===============================
        JLabel logo = new JLabel();
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icon = new ImageIcon("images/cheezo.png");  // ← update your file name
        Image img = icon.getImage().getScaledInstance(280, 280, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));

        panel.add(logo, BorderLayout.CENTER);

        // ===============================
        // Loading Text
        // ===============================
        JLabel title = new JLabel("Loading Cheezo...", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 20));
        title.setForeground(new Color(255, 100, 20));
        panel.add(title, BorderLayout.NORTH);

        // ===============================
        // Progress Bar
        // ===============================
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setForeground(new Color(255, 100, 20));
        progressBar.setBorderPainted(false);
        progressBar.setPreferredSize(new Dimension(0, 8));

        panel.add(progressBar, BorderLayout.SOUTH);

        add(panel);

        setSize(420, 420);
        setLocationRelativeTo(null);
        setVisible(true);

        loadApp();
    }

    // ============================================================
    // SIMULATED LOADING EFFECT (FEELS LIKE A REAL APP BOOTING)
    // ============================================================
    private void loadApp() {
        new Thread(() -> {

            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(25);
                    progressBar.setValue(i);
                }
            } catch (Exception ignored) {}

            // Close splash
            dispose();

            // Open Main Window (Login / Admin / User)
            javax.swing.SwingUtilities.invokeLater(() -> {

            new LoginPage(cred -> {
                CredentialDaoImplements credentialDao = new CredentialDaoImplements();
                // CLOSE LOGIN PAGE
                // (only if LoginPage constructor accepts listener and opens UI)
                
                // AUTH LOGIC: if usertype is Admin, show Admin UI, else User UI
                if (cred.getUserType().equalsIgnoreCase("Admin")) {

                    showAdminUI(cred);

                } else {

                    showUserUI(cred);

                }

            }).setVisible(true);
        });

        }).start();
    }

     private static void showAdminUI(CredentialBean cred) {
        System.out.println("Logged in as admin: " + cred.getUserID());
        AdminPanel admin = new AdminPanel();
        admin.setVisible(true);
    }

    private static void showUserUI(CredentialBean cred) {
        System.out.println("Logged in as user: " + cred.getUserID());
        CredentialDaoImplements dao = new CredentialDaoImplements();
        ProfileBean profile = dao.findProfileByUserId(cred.getUserID());
        UserPanel panel = new UserPanel(profile);
        // pass user if needed
        panel.setVisible(true);
    }
}

