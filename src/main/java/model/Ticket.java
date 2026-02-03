package model;

import java.time.LocalDate;

public class Ticket {
    private String ticketID;
    private LocalDate journeyDate;
    private String seatDetails;

    public Ticket(String ticketID, LocalDate journeyDate, String seatDetails) {
        this.ticketID = ticketID;
        this.journeyDate = journeyDate;
        this.seatDetails = seatDetails;
    }

    public String generateTicket() {
        return "Ticket ID: " + ticketID + "\nDate: " + journeyDate + "\nSeat: " + seatDetails;
    }

    public String getTicketID() {
        return ticketID;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public String getSeatDetails() {
        return seatDetails;
    }
}
