package com.ust.pos.bean;

public class FoodBean {
    private String foodID;
    private String name;
    private String type;
    private String foodSize;
    private int quantity;
    private double price;
    private String storeID; // ✅ reference to Store

    public FoodBean() {}

    public FoodBean(String foodID, String name, String type, String foodSize,
                    int quantity, double price, String storeID) {
        this.foodID = foodID;
        this.name = name;
        this.type = type;
        this.foodSize = foodSize;
        this.quantity = quantity;
        this.price = price;
        this.storeID = storeID;
    }

    public String getFoodID() {
        return foodID;
    }
    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getFoodSize() {
        return foodSize;
    }
    public void setFoodSize(String foodSize) {
        this.foodSize = foodSize;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getStoreID() {
        return storeID;
    }
    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    @Override
    public String toString() {
        return foodID + " - " + name + " (" + type + ", " + foodSize + ") ₹" + price + " [Store: " + storeID + "]";
    }
}
