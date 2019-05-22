package com.example.nk.myapplication.Model;

public class User {
    private String Name;
    private String Phone;
    public User() {

    }

    public User(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
