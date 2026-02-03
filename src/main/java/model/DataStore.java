package model;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore instance;
    private List<User> users;
    private List<Train> trains;
    private User currentUser;

    private DataStore() {
        users = new ArrayList<>();
        trains = new ArrayList<>();
        initializeDummyData();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    private void initializeDummyData() {
        // Users
        users.add(new User("U001", "John Doe", "1234567890", "john@example.com"));
        users.add(new User("U002", "Jane Smith", "0987654321", "jane@example.com"));

        // Trains
        Train train1 = new Train("T101", "Express 101", "CityA", "CityB");
        train1.addCoach(new Coach("C1", "AC", 20));
        train1.addCoach(new Coach("C2", "Sleeper", 20));
        trains.add(train1);

        Train train2 = new Train("T102", "SuperFast 202", "CityB", "CityA");
        train2.addCoach(new Coach("C1", "AC", 20));
        trains.add(train2);
    }

    public User authenticate(String userID) {
        return users.stream()
                .filter(u -> u.getUserID().equals(userID))
                .findFirst()
                .orElse(null);
    }

    public List<Train> searchTrains(String source, String destination) {
        return trains.stream()
                .filter(t -> t.getSource().equalsIgnoreCase(source) && t.getDestination().equalsIgnoreCase(destination))
                .toList();
    }

    public List<Train> getAllTrains() {
        return trains;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
