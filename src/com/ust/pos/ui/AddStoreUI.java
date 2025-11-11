package com.ust.pos.ui;
import com.ust.pos.bean.StoreBean;
import com.ust.pos.util.IdGenerator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddStoreUI extends JDialog {

    private JTextField txtStoreID, txtName, txtStreet, txtMobile, txtCity, txtState, txtPincode;
    private JButton btnSave, btnCancel;
    private StoreBean storeBean; 

    public AddStoreUI(JFrame parent) {
        super(parent, "Add New Store", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel("Store ID:"));
        txtStoreID = new JTextField(IdGenerator.nextStoreId());
        txtStoreID.setEditable(false);
        add(txtStoreID);

        add(new JLabel("Store Name:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Street:"));
        txtStreet = new JTextField();
        add(txtStreet);

        add(new JLabel("Mobile No:"));
        txtMobile = new JTextField();
        add(txtMobile);

        add(new JLabel("City:"));
        txtCity = new JTextField();
        add(txtCity);

        add(new JLabel("State:"));
        txtState = new JTextField();
        add(txtState);

        add(new JLabel("Pincode:"));
        txtPincode = new JTextField();
        add(txtPincode);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");

        add(btnSave);
        add(btnCancel);
        btnSave.addActionListener((ActionEvent e) -> {
            if (txtName.getText().isEmpty() || txtMobile.getText().isEmpty() || txtCity.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            storeBean = new StoreBean(
                    txtStoreID.getText(),
                    txtName.getText(),
                    txtStreet.getText(),
                    txtMobile.getText(),
                    txtCity.getText(),
                    txtState.getText(),
                    txtPincode.getText()
            );

            dispose();
        });

        
        btnCancel.addActionListener(e -> {
            storeBean = null;
            dispose();
        });
    }

    public StoreBean getStoreBean() {
        return storeBean;
    }
    public static StoreBean showDialog(JFrame parent) {
        AddStoreUI dialog = new AddStoreUI(parent);
        dialog.setVisible(true); 
        return dialog.getStoreBean();
    }

    public static void main(String[] args) {
        StoreBean store = AddStoreUI.showDialog(null);
        if (store != null) {
            System.out.println("Store Created: " + store.getName());
        } else {
            System.out.println("No store created.");
        }
    }
}
