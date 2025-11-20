package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import com.ust.pos.bean.FoodBean;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.dao.data.FoodDaoImplemets;
import com.ust.pos.dao.data.StoreDaoImplements;
import com.ust.pos.util.IdGenerator;

public class AddFoodForm extends JFrame {

    private AdminPanel parent;

    public AddFoodForm(AdminPanel parent) {
        this.parent = parent;
        setTitle("Add Food");
        setSize(450, 600);
        setLocationRelativeTo(null);

        FoodDaoImplemets foodDao = new FoodDaoImplemets();
        StoreDaoImplements storeDao = new StoreDaoImplements();

        // Auto Food ID using IdGenerator
        String autoId = IdGenerator.nextFoodId();
        JTextField txtId = new JTextField(autoId);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);

        JTextField txtName = new JTextField();
        JTextField txtType = new JTextField();
        JTextField txtSize = new JTextField();
        JTextField txtQty = new JTextField();
        JTextField txtPrice = new JTextField();

        // ---------------- STORE DROPDOWN ------------------
        JComboBox<String> storeDropdown = new JComboBox<>();
        List<StoreBean> storeList = storeDao.findAll();

        for (StoreBean s : storeList) {
            storeDropdown.addItem(s.getStoreID() + " - " + s.getName());
        }

        JButton btnSubmit = new JButton("Add Food");

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Food ID (Auto):"));
        panel.add(txtId);

        panel.add(new JLabel("Name:"));
        panel.add(txtName);

        panel.add(new JLabel("Type:"));
        panel.add(txtType);

        panel.add(new JLabel("Size:"));
        panel.add(txtSize);

        panel.add(new JLabel("Quantity:"));
        panel.add(txtQty);

        panel.add(new JLabel("Price:"));
        panel.add(txtPrice);

        panel.add(new JLabel("Store:"));
        panel.add(storeDropdown);

        panel.add(btnSubmit);
        add(panel);

        btnSubmit.addActionListener(e -> {

            // Extract storeID only from: "ST-001 - Pizza Hub"
            String selectedStore = (String) storeDropdown.getSelectedItem();
            String storeID = selectedStore.split(" - ")[0];

            FoodBean f = new FoodBean(
                autoId,
                txtName.getText(),
                txtType.getText(),
                txtSize.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtPrice.getText()),
                storeID
            );

            int rows = foodDao.create(f);

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✔ Food Added Successfully!");
                parent.showFoodResult(parent.formatFood(f));
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to add food.");
            }
        });

        setVisible(true);
    }
}
