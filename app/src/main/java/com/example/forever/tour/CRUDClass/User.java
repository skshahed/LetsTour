package com.example.forever.tour.CRUDClass;

import java.io.Serializable;

/**
 * Created by Ashif Rahman on 4/28/2017.
 */

public class User implements Serializable{
    private int userId;
    private String imagePath;
    private String fullName;
    private String userName;
    private String password;
    private String contactNumber;

    public User(int userId, String imagePath, String fullName, String userName, String password, String contactNumber) {
        this.userId = userId;
        this.imagePath = imagePath;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.contactNumber = contactNumber;
    }

    public User(String imagePath, String fullName, String userName, String password, String contactNumber) {
        this.imagePath = imagePath;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.contactNumber = contactNumber;
    }

    public User(String fullName, String userName, String password, String contactNumber) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.contactNumber = contactNumber;
    }


    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
