package com.example.nk.myapplication.Model;

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String EmailId;

    public User() {

    }

    public User(String name, String password, String emailId) {
        Name = name;
        Password = password;
        EmailId = emailId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }
}
