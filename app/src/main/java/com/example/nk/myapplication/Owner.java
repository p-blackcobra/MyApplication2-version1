package com.example.nk.myapplication;

public class Owner {
    private String name;
    private String email;

    public Owner()
    {

    }

    public Owner(String name, String email, String contactno) {
        this.name = name;
        this.email = email;
        this.contactno = contactno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    private String contactno;
}
