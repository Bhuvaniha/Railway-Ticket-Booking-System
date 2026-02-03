package app.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private int id;
    private int trainId;
    private String passengerName;
    private String seatNumber;
    private LocalDate travelDate;
    private LocalDateTime bookingDate;
    private BigDecimal amount;
    private String status;
    
    public Booking() {}
    
    public Booking(int trainId, String passengerName, String seatNumber, LocalDate travelDate, BigDecimal amount) {
        this.trainId = trainId;
        this.passengerName = passengerName;
        this.seatNumber = seatNumber;
        this.travelDate = travelDate;
        this.amount = amount;
        this.status = "CONFIRMED";
        this.bookingDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getTrainId() {
        return trainId;
    }
    
    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public String getSeatNumber() {
        return seatNumber;
    }
    
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public LocalDate getTravelDate() {
        return travelDate;
    }
    
    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }
    
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Booking #" + id + " - " + passengerName + " (Seat: " + seatNumber + ", Travel: " + travelDate + ", Amount: $" + amount + ")";
    }
}
