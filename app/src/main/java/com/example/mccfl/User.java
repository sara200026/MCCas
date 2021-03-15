package com.example.mccfl;

public class User {
    private String id;
    private String userName;
    private String mobileNumber;
    private String userAddress;

    private  User(){}
    User(String id, String username, String userNumber, String userAddress) {
        this.id = id;
        this.userName = username;
        this.mobileNumber = userNumber;
        this.userAddress = userAddress;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setAddress(String address) {
        this.userAddress = address;
    }

    public String getAddress() {
        return this.userAddress;
    }


}
