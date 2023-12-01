package com.example.e_waste.models;

import java.util.ArrayList;
import java.util.List;

public class request {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String location;
   // private List<String> dumpSites = new ArrayList<>();

    // Add a default constructor
    public request() {
        // Default constructor required for Firebase
    }

    // Add a constructor with parameters for initialization
    public request(String name, String email, String password, String phone, String location) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.location = location;
    }

    // Add getter and setter methods for each field

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
