

import com.ust.pos.bean.ProfileBean;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.bean.FoodBean;
import com.ust.pos.data.ProfileData;
import com.ust.pos.data.StoreData;
import com.ust.pos.data.FoodData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::showLoginUI);
    }

    // --- Login UI ---
    private static void showLoginUI() {
        JFrame frame = new JFrame("Pizza Ordering System - Login");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JLabel lblUser = new JLabel("Email:");
        JTextField txtUser = new JTextField();
        JLabel lblPass = new JLabel("Password:");
        JPasswordField txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Login");
        JLabel lblMsg = new JLabel("", SwingConstants.CENTER);

        panel.add(lblUser);
        panel.add(txtUser);
        panel.add(lblPass);
        panel.add(txtPass);
        panel.add(new JLabel());
        panel.add(btnLogin);
        panel.add(lblMsg);

        frame.add(panel);
        frame.setVisible(true);

        btnLogin.addActionListener(e -> {
            String email = txtUser.getText().trim();
            String password = new String(txtPass.getPassword()).trim();

            ProfileBean user = authenticate(email, password);
            if (user != null) {
                lblMsg.setText("Login Successful!");
                System.out.println("✅ Login success for: " + user.getFirstName());
                frame.dispose();

                if (user.getFirstName().equalsIgnoreCase("Abhinav")) {
                    showAdminUI(user);
                } else {
                    showUserUI(user);
                }
            } else {
                lblMsg.setText("❌ Invalid credentials!");
            }
        });
    }

    // --- Authentication ---
    private static ProfileBean authenticate(String email, String password) {
        List<ProfileBean> profiles = ProfileData.getProfiles();
        for (ProfileBean p : profiles) {
            if (p.getEmailID().equalsIgnoreCase(email) && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    // --- Admin UI ---
    private static void showAdminUI(ProfileBean admin) {
        JFrame frame = new JFrame("Admin Panel - " + admin.getFirstName());
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnViewProfiles = new JButton("View All Users");
        JButton btnViewStores = new JButton("View All Stores");
        JButton btnViewFoods = new JButton("View All Foods");
        JButton btnExit = new JButton("Exit");

        panel.add(btnViewProfiles);
        panel.add(btnViewStores);
        panel.add(btnViewFoods);
        panel.add(btnExit);
        frame.add(panel);
        frame.setVisible(true);

        btnViewProfiles.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Registered Users:\n\n");
            for (ProfileBean p : ProfileData.getProfiles()) {
                sb.append(p.getUserID()).append(" - ")
                  .append(p.getFirstName()).append(" ").append(p.getLastName())
                  .append(" (").append(p.getEmailID()).append(")\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        btnViewStores.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Available Stores:\n\n");
            for (StoreBean s : StoreData.getStores()) {
                sb.append(s.getStoreID()).append(" - ").append(s.getName())
                  .append(", ").append(s.getCity()).append(", ").append(s.getState())
                  .append(" | Phone: ").append(s.getMobileNo()).append("\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        btnViewFoods.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Available Foods:\n\n");
            for (FoodBean f : FoodData.getFoods()) {
                sb.append(f.getFoodID()).append(" - ").append(f.getName())
                  .append(" (").append(f.getType()).append(", ").append(f.getFoodSize()).append(") ₹")
                  .append(f.getPrice()).append(" [Store: ").append(f.getStoreID()).append("]\n");
            }
            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    // --- User UI ---
    private static void showUserUI(ProfileBean user) {
        JFrame frame = new JFrame("User Panel - " + user.getFirstName());
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton btnViewProfile = new JButton("View My Profile");
        JButton btnViewStores = new JButton("View Stores & Menu");
        JButton btnExit = new JButton("Exit");

        panel.add(btnViewProfile);
        panel.add(btnViewStores);
        panel.add(btnExit);
        frame.add(panel);
        frame.setVisible(true);

        btnViewProfile.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append("User ID: ").append(user.getUserID()).append("\n")
              .append("Name: ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("\n")
              .append("Email: ").append(user.getEmailID()).append("\n")
              .append("Phone: ").append(user.getMobileNo()).append("\n")
              .append("City: ").append(user.getCity()).append("\n")
              .append("State: ").append(user.getState()).append("\n");
            JOptionPane.showMessageDialog(frame, sb.toString());
        });

        btnViewStores.addActionListener(e -> {
            List<StoreBean> stores = StoreData.getStores();
            String[] storeNames = stores.stream()
                    .map(s -> s.getStoreID() + " - " + s.getName() + " (" + s.getCity() + ")")
                    .toArray(String[]::new);

            String selected = (String) JOptionPane.showInputDialog(
                    frame,
                    "Select a store to view menu:",
                    "Available Stores",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    storeNames,
                    storeNames[0]
            );

            if (selected != null) {
                String storeID = selected.split(" - ")[0];
                showFoodsForStore(frame, storeID);
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    // --- Show food items for selected store ---
    private static void showFoodsForStore(JFrame parent, String storeID) {
        StringBuilder sb = new StringBuilder("Food Items for Store " + storeID + ":\n\n");
        boolean hasFood = false;

        for (FoodBean f : FoodData.getFoods()) {
            if (f.getStoreID().equalsIgnoreCase(storeID)) {
                sb.append(f.getFoodID()).append(" - ").append(f.getName())
                  .append(" (").append(f.getType()).append(", ").append(f.getFoodSize())
                  .append(") ₹").append(f.getPrice()).append("\n");
                hasFood = true;
            }
        }

        if (!hasFood) {
            sb.append("No food items available for this store.");
        }

        JOptionPane.showMessageDialog(parent, sb.toString());
    }
}
