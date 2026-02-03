package model;

public class User {
    private String userID;
    private String name;
    private String phone;
    private String email;

    public User(String userID, String name, String phone, String email) {
        this.userID = userID;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    // Methods as per requirements, placeholder for now or implemented in controller
    public void searchTrains() {
        System.out.println("Searching trains..."); // Logic usually in controller
    }

    public void selectTrains() {
        System.out.println("Selecting trains...");
    }

    public void makePayment() {
        System.out.println("Making payment...");
    }
}
