package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.ust.pos.bean.FoodBean;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.dao.data.FoodDaoImplemets;
import com.ust.pos.dao.data.ProfileDaoImplements;
import com.ust.pos.dao.data.StoreDaoImplements;

public class AdminPanel extends JFrame {

    // SEPARATE RESULT AREAS FOR EACH TAB  ✔ FIX
    private JTextArea userResultArea = new JTextArea();
    private JTextArea storeResultArea = new JTextArea();

    private JTextArea foodResultArea = new JTextArea();
    private FoodDaoImplemets foodDao = new FoodDaoImplemets();
    


    private ProfileDaoImplements profileDao = new ProfileDaoImplements();
    private StoreDaoImplements storeDao = new StoreDaoImplements();

    public AdminPanel() {
        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userResultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        storeResultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Users", createUserPanel());
        tabs.addTab("Stores", createStorePanel());
        tabs.addTab("Foods", createFoodPanel());
        tabs.addTab("Orders", new JPanel());

        add(tabs);
        setVisible(true);
    }

    // SECTION PANEL WITH LEFT BUTTONS + RIGHT RESULT
    private JPanel createSectionPanel(JPanel leftButtons, JTextArea resultArea) {

        JPanel main = new JPanel(new BorderLayout());

        // LEFT BUTTONS
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.add(leftButtons, BorderLayout.NORTH);

        main.add(leftContainer, BorderLayout.WEST);

        // RIGHT SCREEN (separate for each tab)
        JScrollPane scroll = new JScrollPane(resultArea);
        main.add(scroll, BorderLayout.CENTER);

        return main;
    }

    // ---------------- USERS PANEL ----------------
    private JPanel createUserPanel() {

        JPanel left = new JPanel(new GridLayout(10, 1, 10, 10));

        JButton btnShowUsers = new JButton("Show Users");
        JButton btnSearchUser = new JButton("Search User by ID");
        JButton btnUpdateUser = new JButton("Update User");
        JButton btnDeleteUser = new JButton("Delete User");

        left.add(btnShowUsers);
        left.add(btnSearchUser);
        left.add(btnUpdateUser);
        left.add(btnDeleteUser);

        // ACTION LISTENERS --------------------------------------

        // SHOW ALL USERS
        btnShowUsers.addActionListener(e -> {
            List<ProfileBean> users = profileDao.findAllProfiles();

            if (users.isEmpty()) {
                userResultArea.setText("No users found.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (ProfileBean p : users)
                sb.append(formatProfile(p)).append("\n--------------------\n");

            userResultArea.setText(sb.toString());
        });

        // SEARCH USER BY ID
        btnSearchUser.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter User ID:");

            if (id == null || id.isEmpty()) return;

            ProfileBean p = profileDao.findProfileById(id);

            if (p == null)
                userResultArea.setText("❌ No user found with ID: " + id);
            else
                userResultArea.setText(formatProfile(p));
        });

        // UPDATE USER FORM
        btnUpdateUser.addActionListener(e -> new UpdateUserForm(this));

        // DELETE USER
        btnDeleteUser.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter User ID to Delete:");

            if (id == null || id.isEmpty()) return;

            boolean deleted = deleteUserFromDB(id);

            if (deleted)
                userResultArea.setText("🗑️ User deleted successfully!\nID: " + id);
            else
                userResultArea.setText("❌ User deletion failed.");
        });

        return createSectionPanel(left, userResultArea);
    }

    // ---------- FORMAT PROFILE ----------
     String formatProfile(ProfileBean p) {

        return """
                USER DETAILS
                ID: %s
                Name: %s %s
                Email: %s
                Mobile: %s
                City: %s
                Gender: %s
                """.formatted(
                p.getUserID(),
                p.getFirstName(), p.getLastName(),
                p.getEmailID(),
                p.getMobileNo(),
                p.getCity(),
                p.getGender()
        );
    }

    // DELETE USER DB
    private boolean deleteUserFromDB(String userId) {
        try (java.sql.Connection con = com.ust.pos.util.DBConnection.getConnection();
             java.sql.PreparedStatement ps1 = con.prepareStatement("DELETE FROM Credential WHERE userID=?");
             java.sql.PreparedStatement ps2 = con.prepareStatement("DELETE FROM Profile WHERE userID=?")) {

            ps1.setString(1, userId);
            ps2.setString(1, userId);

            ps1.executeUpdate();
            int rows = ps2.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // CALLED BY UpdateUserForm
    public void showUserResult(String text) {
        userResultArea.setText(text);
    }

    // --------------------- STORES PANEL ---------------------
    private JPanel createStorePanel() {

        JPanel left = new JPanel(new GridLayout(10, 1, 10, 10));

        JButton btnShowStores = new JButton("Show All Stores");
        JButton btnSearchStore = new JButton("Search Store by ID");
        JButton btnAddStore = new JButton("Add Store");
        JButton btnUpdateStore = new JButton("Update Store");
        JButton btnDeleteStore = new JButton("Delete Store");

        left.add(btnShowStores);
        left.add(btnSearchStore);
        left.add(btnAddStore);
        left.add(btnUpdateStore);
        left.add(btnDeleteStore);

        // ACTION LISTENERS --------------------------------------------------

        btnShowStores.addActionListener(e -> {
            List<StoreBean> list = storeDao.findAll();

            if (list.isEmpty()) {
                storeResultArea.setText("No stores found.");
                return;
            }

            StringBuilder sb = new StringBuilder();

            for (StoreBean s : list)
                sb.append(formatStore(s)).append("\n--------------------\n");

            storeResultArea.setText(sb.toString());
        });

        btnSearchStore.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter Store ID:");
            if (id == null || id.isEmpty()) return;

            StoreBean s = storeDao.findById(id);

            if (s == null)
                storeResultArea.setText("❌ No store found with ID: " + id);
            else
                storeResultArea.setText(formatStore(s));
        });

        btnAddStore.addActionListener(e -> new AddStoreForm(this));
        btnUpdateStore.addActionListener(e -> new UpdateStoreForm(this));

        btnDeleteStore.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Enter Store ID to Delete:");
            if (id == null || id.isEmpty()) return;

            int rows = storeDao.delete(id);

            if (rows > 0)
                storeResultArea.setText("🗑️ Store deleted successfully!\nID: " + id);
            else
                storeResultArea.setText("❌ Store not found.");
        });

        return createSectionPanel(left, storeResultArea);
    }

    // ---------- FORMAT STORE ----------
    public String formatStore(StoreBean s) {

        return """
                STORE DETAILS
                ID: %s
                Name: %s
                Street: %s
                Mobile: %s
                City: %s
                State: %s
                Pincode: %s
                """.formatted(
                s.getStoreID(),
                s.getName(),
                s.getStreet(),
                s.getMobileNo(),
                s.getCity(),
                s.getState(),
                s.getPincode()
        );
    }
    public void showStoreResult(String text) {
    storeResultArea.setText(text);
}

//-------------FOOD-------------------------


private JPanel createFoodPanel() {

    JPanel left = new JPanel(new GridLayout(10, 1, 10, 10));

    JButton btnShowFoods = new JButton("Show All Foods");
    JButton btnSearchFood = new JButton("Search Food by ID");
    JButton btnAddFood = new JButton("Add Food");
    JButton btnUpdateFood = new JButton("Update Food");
    JButton btnDeleteFood = new JButton("Delete Food");
    JButton btnSearchByStore = new JButton("Search Food by Store");


    left.add(btnShowFoods);
    left.add(btnSearchByStore);
    left.add(btnSearchFood);
    left.add(btnAddFood);
    left.add(btnUpdateFood);
    left.add(btnDeleteFood);

    // ---------------- ACTION LISTENERS ----------------

    // 1) Show all foods
    btnShowFoods.addActionListener(e -> {
        var list = foodDao.findAll();

        if (list.isEmpty()) {
            foodResultArea.setText("No foods found.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (FoodBean f : list)
            sb.append(formatFood(f)).append("\n--------------------\n");

        foodResultArea.setText(sb.toString());
    });

    btnSearchByStore.addActionListener(e -> {

    // Step 1: Show all stores in the result area
    List<StoreBean> stores = storeDao.findAll();

    if (stores.isEmpty()) {
        foodResultArea.setText("❌ No stores available.");
        return;
    }

    StringBuilder sb = new StringBuilder("AVAILABLE STORES:\n\n");
    for (StoreBean s : stores) {
        sb.append("%s - %s\n".formatted(s.getStoreID(), s.getName()));
    }
    sb.append("\n--------------------------------------\n");

    // Step 2: Show list in result area
    foodResultArea.setText(sb.toString());

    // Step 3: Ask for store ID
    String storeID = JOptionPane.showInputDialog(this, "Enter Store ID:");

    if (storeID == null || storeID.isEmpty()) return;

    // Step 4: Use your DAO method to fetch foods
    List<FoodBean> foods = foodDao.findByStore(storeID);

    if (foods.isEmpty()) {
        foodResultArea.append("\n❌ No foods found for store: " + storeID);
        return;
    }

    // Step 5: Display foods below store list
    foodResultArea.append("\nFOODS IN STORE " + storeID + ":\n\n");

    for (FoodBean f : foods) {
        foodResultArea.append(formatFood(f) + "\n---------------------\n");
    }
});


    // 2) Search by ID
    btnSearchFood.addActionListener(e -> {
        String id = JOptionPane.showInputDialog(this, "Enter Food ID:");
        if (id == null || id.isEmpty()) return;

        FoodBean f = foodDao.findById(id);

        if (f == null)
            foodResultArea.setText("❌ No food found with ID: " + id);
        else
            foodResultArea.setText(formatFood(f));
    });

    // 3) Add Food
    btnAddFood.addActionListener(e -> new AddFoodForm(this));

    // 4) Update Food
    btnUpdateFood.addActionListener(e -> new UpdateFoodForm(this));

    // 5) Delete Food
    btnDeleteFood.addActionListener(e -> {
        String id = JOptionPane.showInputDialog(this, "Enter Food ID to delete:");
        if (id == null || id.isEmpty()) return;

        int rows = foodDao.delete(id);

        if (rows > 0)
            foodResultArea.setText("🗑️ Food deleted successfully!\nID: " + id);
        else
            foodResultArea.setText("❌ Food not found.");
    });

    return createSectionPanel(left, foodResultArea);
}
String formatFood(FoodBean f) {
    return """
            FOOD DETAILS
            ID: %s
            Name: %s
            Type: %s
            Size: %s
            Quantity: %d
            Price: %.2f
            StoreID: %s
            """.formatted(
            f.getFoodID(),
            f.getName(),
            f.getType(),
            f.getFoodSize(),
            f.getQuantity(),
            f.getPrice(),
            f.getStoreID()
    );
}
public void showFoodResult(String text) {
    foodResultArea.setText(text);
}




    public static void main(String[] args) {
        new AdminPanel();
    }
}
