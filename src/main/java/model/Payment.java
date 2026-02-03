package model;

public class Payment {
    private String paymentID;
    private double amount;
    private String paymentStatus;

    public Payment(String paymentID, double amount) {
        this.paymentID = paymentID;
        this.amount = amount;
        this.paymentStatus = "Pending";
    }

    public void processPayment() {
        this.paymentStatus = "Completed";
        System.out.println("Payment " + paymentID + " processed successfully.");
    }

    public String getPaymentID() {
        return paymentID;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
