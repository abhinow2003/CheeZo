package com.ust.pos.bean;

public class StoreBean {
    private String storeID;
    private String name;
    private String street;
    private String mobileNo;
    private String city;
    private String state;
    private String pincode;

    public StoreBean(String storeID, String name, String street, String mobileNo, String city, String state,
            String pincode) {
        this.storeID = storeID;
        this.name = name;
        this.street = street;
        this.mobileNo = mobileNo;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }
    public StoreBean() {
        
    }
    public String getStoreID() {
        return storeID;
    }
    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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
    @Override
public String toString() {
    return "===== Store Details =====\n" +
           "StoreID   : " + storeID + "\n" +
           "Name      : " + name + "\n" +
           "Address   : " + street + ", " + city + ", " + state + " - " + pincode + "\n" +
           "MobileNo  : " + mobileNo + "\n" +
           "=========================";
}

    
}
