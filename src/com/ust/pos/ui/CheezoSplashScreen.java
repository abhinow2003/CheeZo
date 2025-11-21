package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ust.pos.bean.CredentialBean;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.dao.data.CredentialDaoImplements;

public class CheezoSplashScreen extends JWindow {

    private float opacity = 0f;        // Fade-in animation
    private JProgressBar progressBar;  // Loading bar

    public CheezoSplashScreen() {
        setBackground(new Color(0, 0, 0, 0)); // transparent window

        JPanel panel = new RoundedPanel(30);
        panel.setBackground(new Color(255, 255, 255));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ---------- LOGO ----------
        JLabel logo = new JLabel("", SwingConstants.CENTER);
        ImageIcon icon = new ImageIcon("images/cheezo.png");
        Image img = icon.getImage().getScaledInstance(240, 240, Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));

        // ---------- TITLE ----------
        JLabel title = new JLabel("Cheezo", SwingConstants.CENTER);
        title.setFont(new Font("Poppins", Font.BOLD, 30));
        title.setForeground(new Color(255, 120, 50));

        JLabel sub = new JLabel("Food Delivery • Fast & Fresh", SwingConstants.CENTER);
        sub.setFont(new Font("Poppins", Font.PLAIN, 14));
        sub.setForeground(new Color(100, 100, 100));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(sub);

        // ---------- PROGRESS BAR ----------
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);

        progressBar.setStringPainted(false);
        progressBar.setForeground(new Color(255, 120, 50));
        progressBar.setBackground(new Color(240, 240, 240));
        progressBar.setPreferredSize(new Dimension(0, 10));

        // ---------- Adding to Panel ----------
        panel.add(logo, BorderLayout.CENTER);
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.SOUTH);

        add(panel);

        setSize(480, 480);
        setLocationRelativeTo(null);

        fadeIn();
    }

    // ============================================================
    // FADE-IN ANIMATION
    // ============================================================
    private void fadeIn() {
        setOpacity(0f);
        setVisible(true);

        Timer timer = new Timer(20, null);
        timer.addActionListener(e -> {
            opacity += 0.03f;
            if (opacity >= 1f) {
                opacity = 1f;
                timer.stop();
                startLoading();
            }
            setOpacity(opacity);
        });
        timer.start();
    }

    // ============================================================
    // FAKE LOADING → OPEN LOGIN
    // ============================================================
    private void startLoading() {
        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(25);
                    progressBar.setValue(i);
                }
            } catch (Exception ignored) {}

            fadeOut();
        }).start();
    }

    // ============================================================
    // FADE-OUT ANIMATION
    // ============================================================
    private void fadeOut() {
        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.04f;
                if (opacity <= 0f) {
                    opacity = 0f;
                    timer.stop();
                    dispose();
                    openLogin();
                }
                setOpacity(opacity);
            }
        });
        timer.start();
    }

    // ============================================================
    // OPEN LOGIN PAGE
    // ============================================================
    private void openLogin() {
        new LoginPage(cred -> {
            if (cred.getUserType().equalsIgnoreCase("Admin")) {
                showAdminUI(cred);
            } else {
                showUserUI(cred);
            }
        }).setVisible(true);
    }

    // ============================================================
    // OPEN ADMIN PANEL
    // ============================================================
    private static void showAdminUI(CredentialBean cred) {
        AdminPanel admin = new AdminPanel();
        admin.setVisible(true);
    }

    // ============================================================
    // OPEN USER PANEL
    // ============================================================
    private static void showUserUI(CredentialBean cred) {
        CredentialDaoImplements dao = new CredentialDaoImplements();
        ProfileBean profile = dao.findProfileByUserId(cred.getUserID());
        new UserPanel(profile).setVisible(true);
    }
}


// ============================================================
// BEAUTIFUL ROUNDED PANEL (CUSTOM COMPONENT)
// ============================================================
class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();
    }
}
