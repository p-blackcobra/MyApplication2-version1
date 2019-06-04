package com.example.nk.myapplication;

public class MessViewedObject {
    public String username;
    public String timeline;

    public MessViewedObject() {

    }

    public MessViewedObject(String username, String timeline) {
        this.username = username;
        this.timeline = timeline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }
}
