package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import com.ust.pos.bean.FoodBean;
import com.ust.pos.bean.OrderBean;
import com.ust.pos.bean.PaymentBean;
import com.ust.pos.bean.ProfileBean;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.bean.CartBean;

import com.ust.pos.dao.data.FoodDaoImplemets;
import com.ust.pos.dao.data.OrderDaoImplements;
import com.ust.pos.dao.data.PaymentDaoImplements;
import com.ust.pos.dao.data.ProfileDaoImplements;
import com.ust.pos.dao.data.StoreDaoImplements;
import com.ust.pos.ui.component.OrderCardPanel;
import com.ust.pos.ui.component.PaymentCardPanel;
import com.ust.pos.util.DBConnection;
import com.ust.pos.dao.data.CartDaoImplements;

// ---------------------------------------------------------------------------------------
// FOOD CARD COMPONENT
// ---------------------------------------------------------------------------------------
class FoodCard extends JPanel {

    public FoodCard(FoodBean food, Runnable onAdd) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(180, 250));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setBackground(Color.WHITE);

        String imagePath = "images/food.jpg";
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(180, 130, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(img));
        add(imgLabel, BorderLayout.NORTH);

        JPanel info = new JPanel(new GridLayout(3, 1));
        info.setBackground(Color.WHITE);

        JLabel lblName = new JLabel(food.getName(), SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lblSize = new JLabel(food.getFoodSize(), SwingConstants.CENTER);
        JLabel lblPrice = new JLabel("₹ " + food.getPrice(), SwingConstants.CENTER);

        info.add(lblName);
        info.add(lblSize);
        info.add(lblPrice);

        add(info, BorderLayout.CENTER);

        JButton btnAdd = new JButton("Add to Cart");
        add(btnAdd, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> onAdd.run());
    }
}

// ---------------------------------------------------------------------------------------
// CART CARD COMPONENT
// ---------------------------------------------------------------------------------------
class CartCard extends JPanel {

    public CartCard(FoodBean food, Runnable onRemove) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(180, 220));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon("images/food.jpg");
        Image img = icon.getImage().getScaledInstance(180, 120, Image.SCALE_SMOOTH);
        add(new JLabel(new ImageIcon(img)), BorderLayout.NORTH);

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setBackground(Color.WHITE);

        info.add(new JLabel(food.getName(), SwingConstants.CENTER));
        info.add(new JLabel("₹ " + food.getPrice(), SwingConstants.CENTER));

        add(info, BorderLayout.CENTER);

        JButton btnRemove = new JButton("Remove");
        add(btnRemove, BorderLayout.SOUTH);

        btnRemove.addActionListener(e -> onRemove.run());
    }
}

// ---------------------------------------------------------------------------------------
// USER PANEL MAIN CLASS
// ---------------------------------------------------------------------------------------
public class UserPanel extends JFrame {

    private JTextArea paymentArea = new JTextArea();
    private JTextArea profileArea = new JTextArea();

    private FoodDaoImplemets foodDao = new FoodDaoImplemets();
    private StoreDaoImplements storeDao = new StoreDaoImplements();
    private CartDaoImplements cartDao = new CartDaoImplements();
    private ProfileDaoImplements profileDao = new ProfileDaoImplements();
    private ProfileBean profile; // store full user profile
    private OrderDaoImplements orderDao = new OrderDaoImplements();

    private PaymentDaoImplements paymentDao = new PaymentDaoImplements();
private double pendingPaymentAmount = 0; // to pass cart total to payment tab



    private JPanel cardContainer = new JPanel();
    private JPanel cartContainer = new JPanel();

    private String userID;

    public UserPanel(ProfileBean user) {
        this.userID = user.getUserID(); 
        
        this.profile = profileDao.findProfileById(userID);// IMPORTANT
//----------------------------------------------------------------------------------------------
        setTitle(user.getFirstName());
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Order", createOrderPanel(user.getLocation()));
        tabs.addTab("Cart", createCartPanel());
        tabs.addTab("Payment", createPaymentPanel());
        tabs.addTab("Profile", createProfilePanel(user));
        tabs.addTab("Orders", createOrdersPanel());

        add(tabs);
        setVisible(true);

        refreshCartUI(); // load DB cart on startup
    }

    // =====================================================================================
    // ORDER PANEL
    // =====================================================================================
    private JPanel createOrderPanel(String location) {

        JPanel main = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(12, 1, 10, 10));

        // Veg Toggle
        JToggleButton btnVeg = new JToggleButton("Veg");
        btnVeg.addActionListener(e -> {
            btnVeg.setText(btnVeg.isSelected() ? "Non-Veg" : "Veg");
        });

        // Location dropdown
        JComboBox<String> locationDropdown = new JComboBox<>();
        locationDropdown.addItem("Select Location");

        List<StoreBean> storeList = storeDao.findAll();
        java.util.Set<String> uniqueLocations = new java.util.HashSet<>();

        for (StoreBean s : storeList)
            uniqueLocations.add(s.getCity());

        for (String loc : uniqueLocations)
            locationDropdown.addItem(loc);

        // Store dropdown
        JComboBox<String> storeDropdown = new JComboBox<>();
        storeDropdown.addItem("Select Store");

        // Listener: populate stores based on selected location
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

        // ---- PRESET USER LOCATION (MUST COME AFTER LISTENER) ----
        if (location != null && !location.isEmpty()) {

            DefaultComboBoxModel<String> model =
                    (DefaultComboBoxModel<String>) locationDropdown.getModel();

            if (model.getIndexOf(location) != -1) {

                locationDropdown.setSelectedItem(location);

                // MANUALLY TRIGGER LISTENER so storeDropdown gets filled
                for (ActionListener al : locationDropdown.getActionListeners()) {
                    al.actionPerformed(
                            new ActionEvent(locationDropdown, ActionEvent.ACTION_PERFORMED, "manual")
                    );
                }
            }
        }

        JButton btnLoadFoods = new JButton("Show Foods");

        left.add(btnVeg);
        left.add(new JLabel("Select Location:"));
        left.add(locationDropdown);
        left.add(new JLabel("Select Store:"));
        left.add(storeDropdown);
        left.add(btnLoadFoods);

        main.add(left, BorderLayout.WEST);

        cardContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        JScrollPane scroll = new JScrollPane(cardContainer);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);

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

        cardContainer.removeAll();

        for (FoodBean f : foods) {

            if (nonVegSelected && f.getType().equalsIgnoreCase("Veg")) continue;
            if (!nonVegSelected && f.getType().equalsIgnoreCase("Non-Veg")) continue;

            cardContainer.add(new FoodCard(f, () -> addToCartDB(f)));
        }

        cardContainer.revalidate();
        cardContainer.repaint();
    }

    // =====================================================================================
    // ADD ITEM TO CART (DB INSERT)
    // =====================================================================================
    private void addToCartDB(FoodBean food) {

        CartBean cart = new CartBean();

        cart.setUserID(userID);
        cart.setFoodID(food.getFoodID());
        cart.setType(food.getType());
        cart.setQuantity(1);
        cart.setCost(food.getPrice());
        cart.setOrderDate(new Date(System.currentTimeMillis()));

        cartDao.add(cart);

        JOptionPane.showMessageDialog(this, food.getName() + " added to cart!");

        refreshCartUI();
    }

    // =====================================================================================
    // CART PANEL (DB BASED)
    // =====================================================================================
    private JPanel createCartPanel() {

        JPanel main = new JPanel(new BorderLayout());

        JPanel left = new JPanel(new GridLayout(10, 1, 10, 10));
        JButton btnClear = new JButton("Clear Cart");
        JButton btnBuyAll = new JButton("BUY ALL");
        left.add(btnBuyAll);
        left.add(btnClear);

        main.add(left, BorderLayout.WEST);

        cartContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        JScrollPane scroll = new JScrollPane(cartContainer);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);

        btnClear.addActionListener(e -> clearCartDB());
btnBuyAll.addActionListener(e -> {

    List<CartBean> carts = cartDao.findByUser(userID);

    if (carts.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Cart is empty!");
        return;
    }

    // Calculate total
    double total = 0;
    for (CartBean c : carts) {
        total += c.getCost();
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Total Amount: ₹" + total + "\nProceed to Payment?",
            "Confirm Payment",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    // ------------------------------------------------------------
    // SAVE TOTAL AMOUNT FOR PAYMENT TAB
    // ------------------------------------------------------------
    pendingPaymentAmount = total;

    // SWITCH TO PAYMENT TAB (index 2)
    JTabbedPane tabs = (JTabbedPane) getContentPane().getComponent(0);
    tabs.setSelectedIndex(2);

});




        return main;
    }

    // =====================================================================================
    // REFRESH CART UI FROM DB
    // =====================================================================================
    private void refreshCartUI() {

        cartContainer.removeAll();

        List<CartBean> cartItems = cartDao.findByUser(userID);

        for (CartBean c : cartItems) {

            FoodBean food = foodDao.findById(c.getFoodID());

            cartContainer.add(new CartCard(food, () -> {
                cartDao.remove(c.getCartID());
                refreshCartUI();
            }));
        }

        cartContainer.revalidate();
        cartContainer.repaint();
    }

    // =====================================================================================
    // CLEAR CART (DB DELETE)
    // =====================================================================================
    private void clearCartDB() {

        List<CartBean> carts = cartDao.findByUser(userID);

        for (CartBean c : carts)
            cartDao.remove(c.getCartID());

        refreshCartUI();
    }

    // =====================================================================================
    // PAYMENT & PROFILE PANELS
    // =====================================================================================
private JPanel createPaymentPanel() {

    JPanel main = new JPanel(new BorderLayout());

    JLabel lblTitle = new JLabel("Select a Card to Pay", SwingConstants.CENTER);
    lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
    main.add(lblTitle, BorderLayout.NORTH);

    JPanel cardListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));

    JScrollPane scroll = new JScrollPane(cardListPanel);
    scroll.getVerticalScrollBar().setUnitIncrement(16);

    main.add(scroll, BorderLayout.CENTER);

    // When Payment tab is opened → load cards
    main.addHierarchyListener(e -> {

        // Refresh amount
        double amount = pendingPaymentAmount;

        // Clear old cards
        cardListPanel.removeAll();

        // Load all cards of user
        List<PaymentBean> cards = loadAllCardsOfUser();

        if (cards.isEmpty()) {
            cardListPanel.add(new JLabel("No Cards Added. Go to Profile → Add Card"));
        } else {
            for (PaymentBean card : cards) {
                PaymentCardPanel cardPanel = new PaymentCardPanel(
                        card,
                        amount,
                        () -> handlePayment(card, amount)
                );
                cardListPanel.add(cardPanel);
            }
        }

        cardListPanel.revalidate();
        cardListPanel.repaint();
    });

    return main;
}






private void handlePayment(PaymentBean card, double amount) {

    // Basic verification
    if (card.getBalance() < amount) {
        JOptionPane.showMessageDialog(this,
                "Insufficient Balance!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Deduct balance
    boolean ok = paymentDao.processPayment(card.getCreditCardNumber(), amount);

    if (!ok) {
        JOptionPane.showMessageDialog(this,
                "Payment Failed!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // CREATE ORDERS
    List<CartBean> carts = cartDao.findByUser(userID);

    for (CartBean c : carts) {

        FoodBean food = foodDao.findById(c.getFoodID());

        OrderBean order = new OrderBean();
        order.setUserID(userID);
        order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
        order.setStoreID(food.getStoreID());
        order.setCartID(c.getCartID());
        order.setTotalPrice(c.getCost());
        order.setOrderStatus("Paid");
        order.setCardNumber(card.getCreditCardNumber());
        // Profile info
        order.setStreet(profile.getStreet());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setPincode(profile.getPincode());
        order.setMobileNo(profile.getMobileNo());
        orderDao.create(order);
    }

    clearCartDB();

    JOptionPane.showMessageDialog(this,
            "Payment Successful!\nOrders Created.",
            "Success",
            JOptionPane.INFORMATION_MESSAGE);

    pendingPaymentAmount = 0;

    refreshCartUI();
}



    private JPanel createProfilePanel(ProfileBean user) {

    JPanel main = new JPanel(new BorderLayout());
    JPanel form = new JPanel(new GridLayout(13, 2, 10, 10));
    form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Load DB profile
    ProfileBean p = profileDao.findProfileById(userID);

    JTextField txtFirst = new JTextField(p.getFirstName());
    JTextField txtLast = new JTextField(p.getLastName());
    JTextField txtEmail = new JTextField(p.getEmailID());
    JTextField txtMobile = new JTextField(p.getMobileNo());
    JTextField txtStreet = new JTextField(p.getStreet());
    JTextField txtLocation = new JTextField(p.getLocation());
    JTextField txtCity = new JTextField(p.getCity());
    JTextField txtState = new JTextField(p.getState());
    JTextField txtPincode = new JTextField(p.getPincode());

    JButton btnSave = new JButton("Save Profile");
    JButton btnCard = new JButton("Add Card Details");

    form.add(new JLabel("First Name:")); form.add(txtFirst);
    form.add(new JLabel("Last Name:")); form.add(txtLast);
    form.add(new JLabel("Email:")); form.add(txtEmail);
    form.add(new JLabel("Mobile No:")); form.add(txtMobile);
    form.add(new JLabel("Street:")); form.add(txtStreet);
    form.add(new JLabel("Location:")); form.add(txtLocation);
    form.add(new JLabel("City:")); form.add(txtCity);
    form.add(new JLabel("State:")); form.add(txtState);
    form.add(new JLabel("Pincode:")); form.add(txtPincode);

    form.add(btnSave);
    form.add(btnCard);

    main.add(form, BorderLayout.CENTER);

    // ============================
    //  SAVE PROFILE BUTTON LOGIC
    // ============================
    btnSave.addActionListener(e -> {

        p.setFirstName(txtFirst.getText());
        p.setLastName(txtLast.getText());
        p.setEmailID(txtEmail.getText());
        p.setMobileNo(txtMobile.getText());
        p.setStreet(txtStreet.getText());
        p.setLocation(txtLocation.getText());
        p.setCity(txtCity.getText());
        p.setState(txtState.getText());
        p.setPincode(txtPincode.getText());

        if (profileDao.updateProfile(p)) {
            JOptionPane.showMessageDialog(this, "Profile Updated Successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Update Failed!");
        }
    });

    // ============================
    //   ADD CARD POPUP WINDOW
    // ============================
    btnCard.addActionListener(e -> openAddCardWindow());

    return main;
}



private List<PaymentBean> loadAllCardsOfUser() {

    List<PaymentBean> list = new ArrayList<>();

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(
                 "SELECT * FROM Payment WHERE userID = ?"
         )) {

        ps.setString(1, userID);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            PaymentBean card = new PaymentBean();
            card.setCreditCardNumber(rs.getString("creditCardNumber"));
            card.setValidFrom(rs.getString("validFrom"));
            card.setValidTo(rs.getString("validTo"));
            card.setBalance(rs.getDouble("balance"));
            card.setUserID(rs.getString("userID"));
            list.add(card);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}



private void openAddCardWindow() {

    JDialog cardDialog = new JDialog(this, "Add Card Details", true);
    cardDialog.setSize(400, 400);
    cardDialog.setLayout(new GridLayout(7, 2, 10, 10));
    cardDialog.setLocationRelativeTo(this);

    JTextField txtCard = new JTextField();
    JTextField txtFrom = new JTextField();
    JTextField txtTo = new JTextField();
    JTextField txtBalance = new JTextField();

    JButton btnAdd = new JButton("Add Card");

    cardDialog.add(new JLabel("Credit Card Number:"));
    cardDialog.add(txtCard);
    cardDialog.add(new JLabel("Valid From (MM/YY):"));
    cardDialog.add(txtFrom);
    cardDialog.add(new JLabel("Valid To (MM/YY):"));
    cardDialog.add(txtTo);
    cardDialog.add(new JLabel("Balance:"));
    cardDialog.add(txtBalance);
    cardDialog.add(new JLabel(""));
    cardDialog.add(btnAdd);

    btnAdd.addActionListener(e -> {

        // Build PaymentBean
        PaymentBean card = new PaymentBean();
        card.setCreditCardNumber(txtCard.getText());
        card.setValidFrom(txtFrom.getText());
        card.setValidTo(txtTo.getText());
        card.setBalance(Double.parseDouble(txtBalance.getText()));
        card.setUserID(userID);

        // Insert into DB
        String result = paymentDao.addCard(card);

        if (result == null) {
            JOptionPane.showMessageDialog(cardDialog, "Error adding card!");
        } 
        else if (result.equals("EXISTS")) {
            JOptionPane.showMessageDialog(cardDialog, "Card Already Added!");
        }
        else {
            JOptionPane.showMessageDialog(cardDialog, "Card Added Successfully!");
            cardDialog.dispose();
        }
    });

    cardDialog.setVisible(true);
}


//-------------------------------------------------------------------
    private JPanel createOrdersPanel() {

    JPanel main = new JPanel(new BorderLayout());

    // ---------- TOP BAR WITH REFRESH BUTTON ----------
    JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton btnRefresh = new JButton("Refresh");
    topBar.add(btnRefresh);

    JLabel lblTitle = new JLabel("Your Orders");
    lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
    topBar.add(lblTitle);

    main.add(topBar, BorderLayout.NORTH);

    // ---------- CARD LIST ----------
    JPanel cardList = new JPanel();
    cardList.setLayout(new BoxLayout(cardList, BoxLayout.Y_AXIS));
    cardList.setBackground(new Color(245, 245, 245));

    JScrollPane scroll = new JScrollPane(cardList);
    scroll.getVerticalScrollBar().setUnitIncrement(16);
    main.add(scroll, BorderLayout.CENTER);

    // ---------- RELOAD LOGIC ----------
    Runnable loadOrders = () -> {
        List<OrderBean> orders = orderDao.findByUser(userID);

        // Show latest orders at top
        orders.sort((o1, o2) -> o2.getOrderID().compareTo(o1.getOrderID()));



        cardList.removeAll();

        if (orders.isEmpty()) {
            JLabel lbl = new JLabel("No orders found.", SwingConstants.CENTER);
            lbl.setFont(new Font("Arial", Font.PLAIN, 16));
            cardList.add(lbl);
        } else {
            for (OrderBean o : orders) {
                OrderCardPanel card = new OrderCardPanel(o);
                card.setAlignmentX(Component.CENTER_ALIGNMENT);
                cardList.add(card);
                cardList.add(Box.createVerticalStrut(15));
            }
        }

        cardList.revalidate();
        cardList.repaint();
    };

    // Button runs the refresh
    btnRefresh.addActionListener(e -> loadOrders.run());

    // Load once initially
    loadOrders.run();

    return main;
}


    public static void main(String[] args) {

        ProfileBean dummy = new ProfileBean();
        dummy.setFirstName("Abhinav");
        dummy.setUserID("US1001");
        dummy.setEmailID("abhi@example.com");
        dummy.setMobileNo("9876543210");
        dummy.setLocation("Kochi"); // preset location for demo

        new UserPanel(dummy);
    }
}
