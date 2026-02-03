package app.service;

import app.dao.TrainDAO;
import app.dao.impl.TrainDAOImpl;
import app.model.Train;

import java.util.List;

public class TrainService {
    private TrainDAO trainDAO;
    
    public TrainService() {
        this.trainDAO = new TrainDAOImpl();
    }
    
    public TrainService(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }
    
    /**
     * Get all available trains
     * @return List of all trains
     */
    public List<Train> getAllTrains() {
        return trainDAO.getAllTrains();
    }
    
    /**
     * Search trains by route
     * @param source Source city
     * @param destination Destination city
     * @return List of trains matching the route
     */
    public List<Train> searchTrains(String source, String destination) {
        if (source == null || source.trim().isEmpty() || 
            destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Source and destination cannot be empty");
        }
        
        return trainDAO.getTrainsByRoute(source.trim(), destination.trim());
    }
    
    /**
     * Get train by ID
     * @param trainId Train ID
     * @return Train object or null if not found
     */
    public Train getTrainById(int trainId) {
        if (trainId <= 0) {
            throw new IllegalArgumentException("Invalid train ID");
        }
        
        return trainDAO.getTrainById(trainId);
    }
    
    /**
     * Check available seats for a train
     * @param trainId Train ID
     * @return Number of available seats
     */
    public int checkAvailableSeats(int trainId) {
        if (trainId <= 0) {
            throw new IllegalArgumentException("Invalid train ID");
        }
        
        return trainDAO.checkAvailableSeats(trainId);
    }
    
    /**
     * Update available seats for a train
     * @param trainId Train ID
     * @param seatsToBook Number of seats to book (positive for booking, negative for cancellation)
     * @return true if successful, false otherwise
     */
    public boolean updateSeats(int trainId, int seatsToBook) {
        if (trainId <= 0) {
            throw new IllegalArgumentException("Invalid train ID");
        }
        
        int currentSeats = trainDAO.checkAvailableSeats(trainId);
        int newSeats = currentSeats - seatsToBook;
        
        if (newSeats < 0) {
            throw new IllegalArgumentException("Not enough seats available");
        }
        
        return trainDAO.updateAvailableSeats(trainId, newSeats);
    }
}
