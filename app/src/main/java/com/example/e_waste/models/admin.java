package com.example.e_waste.models;

public class admin {
    private String name;
    private String email;
    private String password;
    private String pin;
    private String phone;

    // Add a default constructor (required by some frameworks)
    public admin() {
    }

    // Add a constructor with parameters for initialization
    public admin(String name, String email, String password, String phone, String pin) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.pin = pin;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

