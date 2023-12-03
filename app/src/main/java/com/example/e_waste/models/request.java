package com.example.e_waste.models;

import java.util.Date;

public class request {
    private String username;
    private String location;
    private Status status;
    private Date dateTime;
    private String phoneNumber;
    private String[] dumpsites;

    public request() {
    }
    // Constructor
    public request(String username, String location, Status status, Date dateTime, String phoneNumber, String[] dumpsites) {
        this.username = username;
        this.location = location;
        this.status = status;
        this.dateTime = dateTime;
        this.phoneNumber = phoneNumber;
        this.dumpsites = dumpsites;
    }

    // Getter and setter methods for other properties...

    // Enum for status
    public enum Status {
        DONE,
        PENDING,
        IN_PROGRESS
    }

    // Getter and setter for status
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

