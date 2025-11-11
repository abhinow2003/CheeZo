import com.ust.pos.bean.ProfileBean;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.dao.data.StoreDaoImplements;
import com.ust.pos.bean.FoodBean;
import com.ust.pos.data.ProfileData;
import com.ust.pos.ui.AddFoodUI;
import com.ust.pos.ui.AddStoreUI;
import com.ust.pos.ui.Art;
import com.ust.pos.util.InMemoryDataStore;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class App {

    public static void main(String[] args) {
        Art.Draw();
        SwingUtilities.invokeLater(App::showLoginUI);
    }

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
                System.out.println("Login success for: " + user.getFirstName());
                frame.dispose();

                if (user.getFirstName().equalsIgnoreCase("Abhinav")) {
                    showAdminUI(user);
                } else {
                    showUserUI(user);
                }
            } else {
                lblMsg.setText("Invalid credentials!");
            }
        });
    }

    private static ProfileBean authenticate(String email, String password) {
        List<ProfileBean> profiles = ProfileData.getProfiles();
        for (ProfileBean p : profiles) {
            if (p.getEmailID().equalsIgnoreCase(email) && p.getPassword().equals(password)) {
                return p;
            }
        }
        return null;
    }

    private static void showAdminUI(ProfileBean admin) {

        StoreDaoImplements storedao = new StoreDaoImplements();

        JFrame frame = new JFrame("Admin Panel - " + admin.getFirstName());
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));

        
        JLabel lblTitle = new JLabel("Admin Panel", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(lblTitle, BorderLayout.NORTH);

        
        JTextArea resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);

    
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel lblCommand = new JLabel("Enter Admin Code:");
        JTextField txtCommand = new JTextField(10);
        JButton btnRun = new JButton("Run");
        JButton btnHelp = new JButton("Help");
        JButton btnLoginAsUser = new JButton("Login as User"); 
        JButton btnExit = new JButton("Exit");

        inputPanel.add(lblCommand);
        inputPanel.add(txtCommand);
        inputPanel.add(btnRun);
        inputPanel.add(btnHelp);
        inputPanel.add(btnLoginAsUser); 
        inputPanel.add(btnExit);

        panel.add(inputPanel, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);

        
        btnRun.addActionListener(e -> {
            String code = txtCommand.getText().trim().toUpperCase();
            resultArea.setText("");

            switch (code) {
                case "AD-001":
                    resultArea.append("🔹 Showing All Registered Users:\n\n");
                    for (ProfileBean p : InMemoryDataStore.PROFILE_LIST) {
                        resultArea.append(p.getUserID() + " - " + p.getFirstName() + " " + p.getLastName()
                                + " (" + p.getEmailID() + ")\n");
                    }
                    break;

                case "AD-002":
                    resultArea.append("🏬 Showing All Stores:\n\n");
                    for (StoreBean s : InMemoryDataStore.STORE_LIST) {
                        resultArea.append(s.getStoreID() + " - " + s.getName()
                                + ", " + s.getCity() + ", " + s.getState()
                                + " | Phone: " + s.getMobileNo() + "\n");
                    }
                    break;

                case "AD-003":
                    resultArea.append("🍕 Showing All Food Items:\n\n");
                    for (FoodBean f : InMemoryDataStore.FOOD_LIST) {
                        resultArea.append(f.getFoodID() + " - " + f.getName()
                                + " (" + f.getType() + ", " + f.getFoodSize() + ") ₹"
                                + f.getPrice() + " [Store: " + f.getStoreID() + "]\n");
                    }
                    break;

                case "AD-004":
                    StoreBean newStore = AddStoreUI.showDialog(frame);
                    if (newStore != null) {
                        StoreDaoImplements sdi =new StoreDaoImplements();
                        sdi.create(newStore);
                        resultArea.append("✅ New store added successfully!\n");
                        resultArea.append("---------------------------------\n");
                        resultArea.append("Store ID : " + newStore.getStoreID() + "\n");
                        resultArea.append("Name     : " + newStore.getName() + "\n");
                        resultArea.append("Street   : " + newStore.getStreet() + "\n");
                        resultArea.append("Mobile   : " + newStore.getMobileNo() + "\n");
                        resultArea.append("City     : " + newStore.getCity() + "\n");
                        resultArea.append("State    : " + newStore.getState() + "\n");
                        resultArea.append("Pincode  : " + newStore.getPincode() + "\n");
                        resultArea.append("---------------------------------\n");
                    } else {
                        resultArea.append("❌ Store creation cancelled by user.\n");
                    }
                    break;

                case "AD-005":
                    FoodBean newFood = AddFoodUI.showDialog(frame);
                    if (newFood != null) {
                        InMemoryDataStore.FOOD_LIST.add(newFood);
                        resultArea.append("✅ New food item added successfully!\n");
                        resultArea.append("---------------------------------\n");
                        resultArea.append("Food ID  : " + newFood.getFoodID() + "\n");
                        resultArea.append("Name     : " + newFood.getName() + "\n");
                        resultArea.append("Type     : " + newFood.getType() + "\n");
                        resultArea.append("Size     : " + newFood.getFoodSize() + "\n");
                        resultArea.append("Quantity : " + newFood.getQuantity() + "\n");
                        resultArea.append("Price ₹  : " + newFood.getPrice() + "\n");
                        resultArea.append("Store ID : " + newFood.getStoreID() + "\n");
                        resultArea.append("---------------------------------\n");
                    } else {
                        resultArea.append("❌ Food creation cancelled.\n");
                    }
                    break;

                default:
                    resultArea.append("❌ Invalid admin code. Type 'Help' for available commands.\n");
            }
            txtCommand.setText("");
        });

        // --- Help button ---
        btnHelp.addActionListener(e -> {
            resultArea.setText("""
            📘 Admin Command Reference:
            AD-001 → View All Users
            AD-002 → View All Stores
            AD-003 → View All Food Items
            AD-004 → Add New Store
            AD-005 → Add New Food Item
            """);
        });

       
        btnLoginAsUser.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame,
                    "Switching to User Mode...",
                    "Admin to User Login",
                    JOptionPane.INFORMATION_MESSAGE);

            frame.setState(Frame.ICONIFIED); 
            showUserUI(admin); 
        });

     
        btnExit.addActionListener(e -> System.exit(0));
    }

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
            List<StoreBean> stores = InMemoryDataStore.STORE_LIST;
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

        btnExit.addActionListener(e -> frame.dispose());
    }

    private static void showFoodsForStore(JFrame parent, String storeID) {
        StringBuilder sb = new StringBuilder("Food Items for Store " + storeID + ":\n\n");
        boolean hasFood = false;

        for (FoodBean f : InMemoryDataStore.FOOD_LIST) {
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
