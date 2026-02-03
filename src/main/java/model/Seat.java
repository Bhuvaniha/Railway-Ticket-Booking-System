package model;

public class Seat {
    private int seatNumber;
    private boolean isBooked;
    private String seatColor; // e.g., "Green" for available, "Red" for booked

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
        this.seatColor = "Green";
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public String getSeatColor() {
        return seatColor;
    }

    public void holdSeat() {
        // Logic to hold seat
        System.out.println("Seat " + seatNumber + " held.");
    }

    public void bookSeat() {
        this.isBooked = true;
        this.seatColor = "Red";
    }
}
