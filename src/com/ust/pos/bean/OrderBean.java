package com.ust.pos.bean;

import java.sql.Date;

public class OrderBean {
    private String orderID;
    private String userID;
    private Date orderDate;
    private String storeID;
    private String cartID;
    private double totalPrice;
    private String orderStatus;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private String mobileNo;
    private String cardNumber;
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOrderID() {
        return orderID;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public String getStoreID() {
        return storeID;
    }
    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }
    public String getCartID() {
        return cartID;
    }
    public void setCartID(String cartID) {
        this.cartID = cartID;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPincode() {
        return pincode;
    }
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    

}
