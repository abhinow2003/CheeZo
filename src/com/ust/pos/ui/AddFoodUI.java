package com.ust.pos.ui;

import com.ust.pos.bean.FoodBean;
import com.ust.pos.util.IdGenerator;
import com.ust.pos.util.InMemoryDataStore;
import com.ust.pos.bean.StoreBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AddFoodUI extends JDialog {

    private JTextField txtFoodID, txtName, txtType, txtSize, txtQuantity, txtPrice;
    private JComboBox<String> comboStore;
    private JButton btnSave, btnCancel;
    private FoodBean foodBean; // This will hold the result

    // ✅ Constructor — builds the UI dialog
    public AddFoodUI(JFrame parent) {
        super(parent, "Add New Food Item", true);
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel("Food ID:"));
        txtFoodID = new JTextField(IdGenerator.nextFoodId());
        txtFoodID.setEditable(false);
        add(txtFoodID);

        add(new JLabel("Food Name:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Type (Veg/Non-Veg):"));
        txtType = new JTextField();
        add(txtType);

        add(new JLabel("Size (Small/Medium/Large):"));
        txtSize = new JTextField();
        add(txtSize);

        add(new JLabel("Quantity:"));
        txtQuantity = new JTextField();
        add(txtQuantity);

        add(new JLabel("Price (₹):"));
        txtPrice = new JTextField();
        add(txtPrice);

        add(new JLabel("Select Store:"));
        comboStore = new JComboBox<>();
        populateStoreDropdown();
        add(comboStore);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        add(btnSave);
        add(btnCancel);

        // ✅ Save button
        btnSave.addActionListener((ActionEvent e) -> {
            if (txtName.getText().isEmpty() || txtType.getText().isEmpty() ||
                txtSize.getText().isEmpty() || txtQuantity.getText().isEmpty() ||
                txtPrice.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(txtQuantity.getText().trim());
                double price = Double.parseDouble(txtPrice.getText().trim());
                String storeID = (String) comboStore.getSelectedItem();

                foodBean = new FoodBean(
                        txtFoodID.getText(),
                        txtName.getText(),
                        txtType.getText(),
                        txtSize.getText(),
                        quantity,
                        price,
                        storeID
                );
                dispose(); // Close dialog
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Quantity and Price!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ✅ Cancel button
        btnCancel.addActionListener(e -> {
            foodBean = null;
            dispose();
        });
    }

    // ✅ Populate store dropdown dynamically from in-memory data
    private void populateStoreDropdown() {
        List<StoreBean> stores = InMemoryDataStore.STORE_LIST;
        if (stores != null && !stores.isEmpty()) {
            for (StoreBean s : stores) {
                comboStore.addItem(s.getStoreID());
            }
        } else {
            comboStore.addItem("No Stores Available");
            comboStore.setEnabled(false);
        }
    }

 
    public FoodBean getFoodBean() {
        return foodBean;
    }

    
    public static FoodBean showDialog(JFrame parent) {
        AddFoodUI dialog = new AddFoodUI(parent);
        dialog.setVisible(true);
        return dialog.getFoodBean();
    }

    
    public static void main(String[] args) {
        FoodBean food = AddFoodUI.showDialog(null);
        if (food != null) {
            System.out.println("Food Created: " + food.getName() + " (" + food.getStoreID() + ")");
        } else {
            System.out.println("No food created.");
        }
    }
}

