package com.example.e_waste.models;

public class driver {
    private String adminName;
    private String driverName;
    private String truckPlate;
    private String email;
    private String password;
    private String phoneNumber;

    public driver() {
        // Default constructor required for Firebase
    }
    public driver(String adminName, String driverName, String truckPlate, String email, String password, String phoneNumber) {
        this.adminName = adminName;
        this.driverName = driverName;
        this.truckPlate = truckPlate;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getTruckPlate() {
        return truckPlate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
