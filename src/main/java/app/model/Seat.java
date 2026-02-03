package app.model;

public class Seat {
    private String seatNumber;
    private boolean isBooked;
    private String seatType; // AC, Sleeper, etc.
    
    public Seat(String seatNumber, String seatType) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.isBooked = false;
    }
    
    public String getSeatNumber() {
        return seatNumber;
    }
    
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public boolean isBooked() {
        return isBooked;
    }
    
    public void setBooked(boolean booked) {
        isBooked = booked;
    }
    
    public String getSeatType() {
        return seatType;
    }
    
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }
    
    public void bookSeat() {
        this.isBooked = true;
    }
    
    @Override
    public String toString() {
        return seatNumber + " (" + seatType + ")" + (isBooked ? " [Booked]" : " [Available]");
    }
}
