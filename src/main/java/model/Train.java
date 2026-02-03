package model;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private String trainID;
    private String trainName;
    private String source;
    private String destination;
    private List<Coach> coaches;

    public Train(String trainID, String trainName, String source, String destination) {
        this.trainID = trainID;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.coaches = new ArrayList<>();
    }

    public void addCoach(Coach coach) {
        coaches.add(coach);
    }

    public String getTrainID() {
        return trainID;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public int getAvailableSeats() {
        int count = 0;
        for (Coach coach : coaches) {
            for (Seat seat : coach.getSeats()) {
                if (!seat.isBooked()) {
                    count++;
                }
            }
        }
        return count;
    }
}
