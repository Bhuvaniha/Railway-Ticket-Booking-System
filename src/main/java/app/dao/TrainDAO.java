package app.dao;

import app.model.Train;
import java.util.List;

public interface TrainDAO {
    
    /**
     * Get all trains from the database
     * @return List of all trains
     */
    List<Train> getAllTrains();
    
    /**
     * Get train by ID
     * @param id Train ID
     * @return Train object or null if not found
     */
    Train getTrainById(int id);
    
    /**
     * Get trains by source and destination
     * @param source Source city
     * @param destination Destination city
     * @return List of trains matching the route
     */
    List<Train> getTrainsByRoute(String source, String destination);
    
    /**
     * Update available seats for a train
     * @param trainId Train ID
     * @param availableSeats New available seats count
     * @return true if successful, false otherwise
     */
    boolean updateAvailableSeats(int trainId, int availableSeats);
    
    /**
     * Check available seats for a train
     * @param trainId Train ID
     * @return Number of available seats
     */
    int checkAvailableSeats(int trainId);
}
