package model;

import java.time.LocalDate;

public class Booking {
    private String bookingID;
    private LocalDate bookingDate;
    private String status;
    private User user;
    private Train train;
    private Ticket ticket;
    private Payment payment;

    public Booking(String bookingID, User user, Train train) {
        this.bookingID = bookingID;
        this.bookingDate = LocalDate.now();
        this.status = "Initiated";
        this.user = user;
        this.train = train;
    }

    public void createBooking(Ticket ticket, Payment payment) {
        this.ticket = ticket;
        this.payment = payment;
        this.status = "Created";
    }

    public void confirmBooking() {
        if (payment != null && "Completed".equals(payment.getPaymentStatus())) {
            this.status = "Confirmed";
            System.out.println("Booking " + bookingID + " confirmed.");
        } else {
            System.out.println("Payment not completed cannot confirm booking.");
        }
    }

    public String getBookingID() {
        return bookingID;
    }

    public String getStatus() {
        return status;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public User getUser() {
        return user;
    }

    public Train getTrain() {
        return train;
    }

    public Payment getPayment() {
        return payment;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }
}
