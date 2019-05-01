package com.example.nk.myapplication;

public class Reviews {
    String userName;
    Float ratingCount;
    String comment;

    public Reviews(String userName, Float ratingCount, String comment) {
       this.userName = userName;
       this.ratingCount = ratingCount;
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Float ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Reviews() {

    }
}
