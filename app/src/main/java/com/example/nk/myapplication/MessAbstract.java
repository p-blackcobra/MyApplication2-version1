package com.example.nk.myapplication;

public class MessAbstract {
    private String messName;
    private String messType;
    private String messRate;
    private String address;
    private String contactNumber;
    private String guestTiffinCharges;
    private String menus;
    private String messOwner;
    private  String remarks;
    private String service;
    private String feast;



    private String messUID;

    public MessAbstract() {

    }

    public MessAbstract(String messName, String messType, String messRate, String address, String contactNumber, String guestTiffinCharges, String menus, String messOwner, String remarks, String service, String feast,String messUID) {
        this.messName = messName;
        this.messType = messType;
        this.messRate = messRate;
        this.address = address;
        this.contactNumber = contactNumber;
        this.guestTiffinCharges = guestTiffinCharges;
        this.menus = menus;
        this.messOwner = messOwner;
        this.remarks = remarks;
        this.service = service;
        this.feast = feast;
        this.messUID = messUID;
    }

    public String getMessName() {
        return messName;
    }

    public void setMessName(String messName) {
        this.messName = messName;
    }

    public String getMessType() {
        return messType;
    }

    public void setMessType(String messType) {
        this.messType = messType;
    }

    public String getMessRate() {
        return messRate;
    }

    public void setMessRate(String messRate) {
        this.messRate = messRate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getGuestTiffinCharges() {
        return guestTiffinCharges;
    }

    public void setGuestTiffinCharges(String guestTiffinCharges) {
        this.guestTiffinCharges = guestTiffinCharges;
    }

    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public String getMessOwner() {
        return messOwner;
    }

    public void setMessOwner(String messOwner) {
        this.messOwner = messOwner;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getFeast() {
        return feast;
    }

    public void setFeast(String feast) {
        this.feast = feast;
    }
    public String getMessUID() {
        return messUID;
    }

    public void setMessUID(String messUID) {
        this.messUID = messUID;
    }
}
