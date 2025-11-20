package com.ust.pos.ui;

import javax.swing.*;
import java.awt.*;

import com.ust.pos.bean.FoodBean;
import com.ust.pos.dao.data.FoodDaoImplemets;

public class UpdateFoodForm extends JFrame {

    private AdminPanel parent;
    private FoodBean loadedFood;

    public UpdateFoodForm(AdminPanel parent) {
        this.parent = parent;
        setTitle("Update Food");
        setSize(400, 550);
        setLocationRelativeTo(null);

        FoodDaoImplemets dao = new FoodDaoImplemets();

        JTextField txtId = new JTextField();
        JButton btnLoad = new JButton("Load Food");

        JTextField txtName = new JTextField();
        JTextField txtType = new JTextField();
        JTextField txtSize = new JTextField();
        JTextField txtQty = new JTextField();
        JTextField txtPrice = new JTextField();
        JTextField txtStoreID = new JTextField();

        JButton btnUpdate = new JButton("Update Food");
        btnUpdate.setEnabled(false);

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Food ID:"));
        panel.add(txtId);

        panel.add(btnLoad);
        panel.add(new JLabel(""));

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

        panel.add(new JLabel("Store ID:"));
        panel.add(txtStoreID);

        panel.add(btnUpdate);
        panel.add(new JLabel(""));

        add(panel);

        // Load food by ID
        btnLoad.addActionListener(e -> {
            loadedFood = dao.findById(txtId.getText());

            if (loadedFood == null) {
                JOptionPane.showMessageDialog(this, "❌ Food not found!");
                return;
            }

            txtName.setText(loadedFood.getName());
            txtType.setText(loadedFood.getType());
            txtSize.setText(loadedFood.getFoodSize());
            txtQty.setText(String.valueOf(loadedFood.getQuantity()));
            txtPrice.setText(String.valueOf(loadedFood.getPrice()));
            txtStoreID.setText(loadedFood.getStoreID());

            btnUpdate.setEnabled(true);
        });

        btnUpdate.addActionListener(e -> {

            loadedFood.setName(txtName.getText());
            loadedFood.setType(txtType.getText());
            loadedFood.setFoodSize(txtSize.getText());
            loadedFood.setQuantity(Integer.parseInt(txtQty.getText()));
            loadedFood.setPrice(Double.parseDouble(txtPrice.getText()));
            loadedFood.setStoreID(txtStoreID.getText());

            int rows = dao.update(loadedFood);

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "✔ Food Updated!");
                parent.showFoodResult(parent.formatFood(loadedFood));
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Update failed!");
            }
        });

        setVisible(true);
    }
}

