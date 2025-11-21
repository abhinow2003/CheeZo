package com.ust.pos.bean;

public class CredentialBean {
    private String userID;
    private String password;
    private String userType;
    private int loginStatus;
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public int getLoginStatus() {
        return loginStatus;
    }
    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }
    
}
