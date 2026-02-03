package model;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private String coachID;
    private String coachType;
    private List<Seat> seats;

    public Coach(String coachID, String coachType, int numberOfSeats) {
        this.coachID = coachID;
        this.coachType = coachType;
        this.seats = new ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            seats.add(new Seat(i));
        }
    }

    public String getCoachID() {
        return coachID;
    }

    public String getCoachType() {
        return coachType;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
