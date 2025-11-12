package com.ust.pos.ui;

import com.ust.pos.bean.ProfileBean;
import com.ust.pos.data.ProfileData;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.bean.FoodBean;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.dao.data.StoreDaoImplements;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class MainAppFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainAppFrame() {
        setTitle("Pizza Ordering System");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createRegisterPanel(), "register");
        mainPanel.add(createAdminPanel(), "admin");
        mainPanel.add(createUserPanel(), "user");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");

        setVisible(true);
    }

    // ------------------- LOGIN PANEL -------------------
    private JPanel createLoginPanel() {
        JPanel panel = basePanel("Login to Your Account");

        JTextField txtEmail = field("Email ID:");
        JPasswordField txtPassword = passwordField("Password:");
        JButton btnLogin = button("Login", new Color(0, 120, 215));
        JButton btnRegister = button("Go to Register", new Color(80, 80, 80));

        panel.add(txtEmail);
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnRegister);

        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText();
            String password = new String(txtPassword.getPassword());

            ProfileBean user = authenticate(email, password);
            if (user != null) {
                if (user.getFirstName().equalsIgnoreCase("Abhinav")) {
                    cardLayout.show(mainPanel, "admin");
                } else {
                    cardLayout.show(mainPanel, "user");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        });

        btnRegister.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        return panel;
    }

    // ------------------- REGISTER PANEL -------------------
    private JPanel createRegisterPanel() {
        JPanel panel = basePanel("Register New User");

        JTextField txtId = field("User ID:");
        JTextField txtName = field("First Name:");
        JTextField txtEmail = field("Email ID:");
        JTextField txtDOB = field("DOB (yyyy-MM-dd):");
        JPasswordField txtPass = passwordField("Password:");
        JButton btnRegister = button("Register", new Color(0, 120, 215));
        JButton btnBack = button("Back to Login", new Color(80, 80, 80));

        panel.add(txtId);
        panel.add(txtName);
        panel.add(txtEmail);
        panel.add(txtDOB);
        panel.add(txtPass);
        panel.add(btnRegister);
        panel.add(btnBack);

        btnRegister.addActionListener(e -> {
            try {
                ProfileBean p = new ProfileBean(
                        txtId.getText(),
                        txtName.getText(),
                        "",
                        Date.valueOf(txtDOB.getText()),
                        "Male",
                        "", "", "", "", "", "", txtEmail.getText(),
                        new String(txtPass.getPassword())
                );
                ProfileData.getProfiles().add(p);
                JOptionPane.showMessageDialog(this, "Registered Successfully!");
                cardLayout.show(mainPanel, "login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        return panel;
    }

    // ------------------- ADMIN PANEL -------------------
    private JPanel createAdminPanel() {
        JPanel panel = basePanel("Admin Dashboard");
        JButton btnUsers = button("View Users", new Color(100, 149, 237));
        JButton btnStores = button("View Stores", new Color(70, 130, 180));
        JButton btnFoods = button("View Foods", new Color(0, 128, 128));
        JButton btnLogout = button("Logout", new Color(139, 0, 0));

        panel.add(btnUsers);
        panel.add(btnStores);
        panel.add(btnFoods);
        panel.add(btnLogout);

        btnUsers.addActionListener(e -> showData("Users", InMemoryDataStore.PROFILE_LIST));
        btnStores.addActionListener(e -> showData("Stores", InMemoryDataStore.STORE_LIST));
        btnFoods.addActionListener(e -> showData("Foods", InMemoryDataStore.FOOD_LIST));

        btnLogout.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        return panel;
    }

    // ------------------- USER PANEL -------------------
    private JPanel createUserPanel() {
        JPanel panel = basePanel("User Dashboard");
        JButton btnProfile = button("My Profile", new Color(0, 191, 255));
        JButton btnViewMenu = button("View Stores & Menu", new Color(0, 139, 139));
        JButton btnLogout = button("Logout", new Color(139, 0, 0));

        panel.add(btnProfile);
        panel.add(btnViewMenu);
        panel.add(btnLogout);

        btnLogout.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        return panel;
    }

    // ------------------- UTILS -------------------
    private JPanel basePanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(45, 45, 48));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(lblTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        return panel;
    }

    private JTextField field(String placeholder) {
        JTextField txt = new JTextField(20);
        styleField(txt, placeholder);
        return txt;
    }

    private JPasswordField passwordField(String placeholder) {
        JPasswordField txt = new JPasswordField(20);
        styleField(txt, placeholder);
        return txt;
    }

    private void styleField(JTextField txt, String placeholder) {
        txt.setMaximumSize(new Dimension(300, 40));
        txt.setFont(new Font("Arial", Font.PLAIN, 16));
        txt.setBackground(new Color(60, 60, 60));
        txt.setForeground(Color.WHITE);
        txt.setCaretColor(Color.WHITE);
        txt.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), placeholder,
                0, 0, new Font("Arial", Font.PLAIN, 14), Color.LIGHT_GRAY));
        txt.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JButton button(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setMaximumSize(new Dimension(300, 45));
        return btn;
    }

    private ProfileBean authenticate(String email, String password) {
        List<ProfileBean> profiles = ProfileData.getProfiles();
        for (ProfileBean p : profiles) {
            if (p.getEmailID().equalsIgnoreCase(email) && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    private void showData(String title, List<?> dataList) {
        JTextArea area = new JTextArea(15, 50);
        for (Object obj : dataList) area.append(obj.toString() + "\n\n");
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        JOptionPane.showMessageDialog(this, scroll, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainAppFrame::new);
    }
}

