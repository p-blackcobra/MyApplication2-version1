package com.example.nk.myapplication;

public class MessCompleteDetail {
    String address;
    String contactNumber;
    String guestTiffinCharges;
    String menus;
    String messName;
    String messOwner;

    String messRate;
    String messHalfRate;
    String messType;
    String remarks;
    String service;
    String feast;
    String messUID;
    String photoURL;




    public MessCompleteDetail(String address, String contactNumber, String guestTiffinCharges, String menus, String messName, String messOwner, String messRate, String messType, String remarks, String service, String feast, String messUID,String photoURL, String messHalfRate) {
        this.address = address;
        this.contactNumber = contactNumber;
        this.guestTiffinCharges = guestTiffinCharges;
        this.menus = menus;
        this.messName = messName;
        this.messOwner = messOwner;
        this.messRate = messRate;
        this.messType = messType;
        this.remarks = remarks;
        this.service = service;
        this.feast = feast;
        this.messUID = messUID;
        this.photoURL = photoURL;
        this.messHalfRate = messHalfRate;

    }

    public MessCompleteDetail() {
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
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

    public String getMessName() {
        return messName;
    }

    public void setMessName(String messName) {
        this.messName = messName;
    }

    public String getMessOwner() {
        return messOwner;
    }

    public void setMessOwner(String messOwner) {
        this.messOwner = messOwner;
    }

    public String getMessRate() {
        return messRate;
    }

    public void setMessRate(String messRate) {
        this.messRate = messRate;
    }

    public String getMessType() {
        return messType;
    }

    public void setMessType(String messType) {
        this.messType = messType;
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

    public String getMessHalfRate() {
        return messHalfRate;
    }

    public void setMessHalfRate(String messHalfRate) {
        this.messHalfRate = messHalfRate;
    }
}
