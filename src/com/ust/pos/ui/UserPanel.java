package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.ust.pos.bean.FoodBean;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.dao.data.FoodDaoImplemets;
import com.ust.pos.dao.data.StoreDaoImplements;

// ---------------------------------------------------------------------------------------
// FOOD CARD COMPONENT
// ---------------------------------------------------------------------------------------
class FoodCard extends JPanel {

    public FoodCard(FoodBean food, JTextArea cartArea) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(180, 250));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setBackground(Color.WHITE);

        // ---- Same image for all foods ----
        String imagePath = "images/food.jpg";
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(180, 130, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        add(imgLabel, BorderLayout.NORTH);

        // ---- Name + Price ----
        JPanel info = new JPanel(new GridLayout(3, 1));
        info.setBackground(Color.WHITE);

        JLabel lblName = new JLabel(food.getName(), SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblsize = new JLabel(food.getFoodSize(), SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblPrice = new JLabel("₹ " + food.getPrice(), SwingConstants.CENTER);
        lblPrice.setFont(new Font("Arial", Font.PLAIN, 13));

        info.add(lblName);
        info.add(lblsize);
        info.add(lblPrice);
        

        add(info, BorderLayout.CENTER);

        // ---- Add to Cart Button ----
        JButton btnAdd = new JButton("Add to Cart");
        add(btnAdd, BorderLayout.SOUTH);

        btnAdd.addActionListener(e ->
                cartArea.append(food.getName() + " ₹" + food.getPrice() + "\n")
        );
    }
}

// ---------------------------------------------------------------------------------------
// USER PANEL MAIN CLASS
// ---------------------------------------------------------------------------------------
public class UserPanel extends JFrame {

    private JTextArea cartArea = new JTextArea();
    private JTextArea paymentArea = new JTextArea();
    private JTextArea profileArea = new JTextArea();

    private FoodDaoImplemets foodDao = new FoodDaoImplemets();
    private StoreDaoImplements storeDao = new StoreDaoImplements();

    // Card container for foods
    private JPanel cardContainer = new JPanel();

    public UserPanel() {
        setTitle("User Dashboard");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Order", createOrderPanel());
        tabs.addTab("Cart", createCartPanel());
        tabs.addTab("Payment", createPaymentPanel());
        tabs.addTab("Profile", createProfilePanel());

        add(tabs);
        setVisible(true);
    }

    // =====================================================================================
    // ORDER PANEL (DROPDOWNS + CARDS)
    // =====================================================================================
    private JPanel createOrderPanel() {

        JPanel main = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(12, 1, 10, 10));

        // ---- Veg Toggle ----
        JToggleButton btnVeg = new JToggleButton("Veg");
        btnVeg.addActionListener(e -> {
            if (btnVeg.isSelected()) btnVeg.setText("Non-Veg");
            else btnVeg.setText("Veg");
        });

        // ---- Location Dropdown ----
        JComboBox<String> locationDropdown = new JComboBox<>();
        locationDropdown.addItem("Select Location");

        List<StoreBean> storeList = storeDao.findAll();
        java.util.Set<String> uniqueLocations = new java.util.HashSet<>();

        for (StoreBean s : storeList) uniqueLocations.add(s.getCity());
        for (String loc : uniqueLocations) locationDropdown.addItem(loc);

        // ---- Store Dropdown ----
        JComboBox<String> storeDropdown = new JComboBox<>();
        storeDropdown.addItem("Select Store");

        locationDropdown.addActionListener(e -> {
            storeDropdown.removeAllItems();
            storeDropdown.addItem("Select Store");

            String selectedLoc = (String) locationDropdown.getSelectedItem();
            if (selectedLoc == null || selectedLoc.equals("Select Location")) return;

            for (StoreBean s : storeList) {
                if (s.getCity().equalsIgnoreCase(selectedLoc)) {
                    storeDropdown.addItem(s.getStoreID() + " - " + s.getName());
                }
            }
        });

        JButton btnLoadFoods = new JButton("Show Foods");

        // ---- Left Layout ----
        left.add(btnVeg);
        left.add(new JLabel("Select Location:"));
        left.add(locationDropdown);
        left.add(new JLabel("Select Store:"));
        left.add(storeDropdown);
        left.add(btnLoadFoods);

        main.add(left, BorderLayout.WEST);

        // ---- Card container ----
        cardContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        JScrollPane scroll = new JScrollPane(cardContainer);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);

        // ---- Load Food Cards ----
        btnLoadFoods.addActionListener(e -> {
            if (storeDropdown.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(this, "Select a store first.");
                return;
            }

            String storeID = storeDropdown.getSelectedItem().toString().split(" - ")[0];
            loadFoodCards(storeID, btnVeg.isSelected());
        });

        return main;
    }

    // =====================================================================================
    // LOAD FOOD CARDS
    // =====================================================================================
    private void loadFoodCards(String storeID, boolean nonVegSelected) {

        List<FoodBean> foods = foodDao.findByStore(storeID);

        cardContainer.removeAll(); // clear old cards

        for (FoodBean f : foods) {

            if (nonVegSelected && f.getType().equalsIgnoreCase("Veg")) continue;
            if (!nonVegSelected && f.getType().equalsIgnoreCase("Non-Veg")) continue;

            cardContainer.add(new FoodCard(f, cartArea));
        }

        cardContainer.revalidate();
        cardContainer.repaint();
    }

    // =====================================================================================
    // CART PANEL
    // =====================================================================================
    private JPanel createCartPanel() {

        JPanel main = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(10, 1, 10, 10));

        JButton btnClearCart = new JButton("Clear Cart");
        left.add(btnClearCart);

        main.add(left, BorderLayout.WEST);

        cartArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        main.add(new JScrollPane(cartArea), BorderLayout.CENTER);

        btnClearCart.addActionListener(e -> cartArea.setText(""));
        return main;
    }

    // =====================================================================================
    // PAYMENT PANEL
    // =====================================================================================
    private JPanel createPaymentPanel() {
        JPanel main = new JPanel(new BorderLayout());

        JPanel left = new JPanel(new GridLayout(10, 1));
        JButton btnPay = new JButton("Proceed to Pay");

        left.add(btnPay);
        main.add(left, BorderLayout.WEST);

        paymentArea.setText("Payment Methods:\n• UPI\n• Card\n• Cash on Delivery");
        main.add(new JScrollPane(paymentArea), BorderLayout.CENTER);

        return main;
    }

    // =====================================================================================
    // PROFILE PANEL
    // =====================================================================================
    private JPanel createProfilePanel() {

        JPanel main = new JPanel(new BorderLayout());

        profileArea.setText("""
                USER PROFILE
                Name: Abhinav
                Email: abhi@example.com
                Mobile: 9876543210
                
                (Load from DB later)
                """);

        main.add(new JScrollPane(profileArea), BorderLayout.CENTER);
        return main;
    }

    public static void main(String[] args) {
        new UserPanel();
    }
}
