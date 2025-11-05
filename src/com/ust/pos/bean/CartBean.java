package com.ust.pos.bean;

import java.sql.Date;

public class CartBean {
    private String cartID;
    private String userID;
    private String foodID;
    private String type;
    private int quantity;
    private double cost;
    private Date orderDate;
    public String getCartID() {
        return cartID;
    }
    public void setCartID(String cartID) {
        this.cartID = cartID;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getFoodID() {
        return foodID;
    }
    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
}
