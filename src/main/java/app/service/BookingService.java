package app.service;

import app.dao.BookingDAO;
import app.dao.TrainDAO;
import app.dao.impl.BookingDAOImpl;
import app.dao.impl.TrainDAOImpl;
import app.model.Booking;
import app.model.Train;

import java.time.LocalDate;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO;
    private TrainDAO trainDAO;
    
    public BookingService() {
        this.bookingDAO = new BookingDAOImpl();
        this.trainDAO = new TrainDAOImpl();
    }
    
    public BookingService(BookingDAO bookingDAO, TrainDAO trainDAO) {
        this.bookingDAO = bookingDAO;
        this.trainDAO = trainDAO;
    }
    
    /**
     * Book a ticket for a passenger on a specific date
     * @param trainId Train ID
     * @param passengerName Passenger name
     * @param travelDate Travel date
     * @return Booking object if successful, null otherwise
     */
    public Booking bookTicket(int trainId, String passengerName, LocalDate travelDate) {
        if (trainId <= 0) {
            throw new IllegalArgumentException("Invalid train ID");
        }
        
        if (passengerName == null || passengerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be empty");
        }
        
        if (travelDate == null) {
            throw new IllegalArgumentException("Travel date cannot be null");
        }
        
        if (travelDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Travel date cannot be in the past");
        }
        
        // Check if train exists and has available seats for the date
        Train train = trainDAO.getTrainById(trainId);
        if (train == null) {
            throw new IllegalArgumentException("Train not found");
        }
        
        int availableSeats = getAvailableSeatsByDate(trainId, travelDate);
        if (availableSeats <= 0) {
            throw new IllegalArgumentException("No seats available on this train for the selected date");
        }
        
        // Book the ticket
        Booking booking = ((BookingDAOImpl) bookingDAO).bookTicket(trainId, passengerName, travelDate);
        
        if (booking != null) {
            // Update available seats (this is now handled by date-specific logic)
            System.out.println("Booking successful for " + travelDate);
        }
        
        return booking;
    }
    
    /**
     * Get available seats for a specific train and date
     * @param trainId Train ID
     * @param travelDate Travel date
     * @return Number of available seats
     */
    public int getAvailableSeatsByDate(int trainId, LocalDate travelDate) {
        if (trainId <= 0) {
            throw new IllegalArgumentException("Invalid train ID");
        }
        
        if (travelDate == null) {
            throw new IllegalArgumentException("Travel date cannot be null");
        }
        
        // Get total seats for the train
        Train train = trainDAO.getTrainById(trainId);
        if (train == null) {
            return 0;
        }
        
        // Count booked seats for the specific date
        int bookedSeats = getBookedSeatsCount(trainId, travelDate);
        return train.getTotalSeats() - bookedSeats;
    }
    
    /**
     * Get count of booked seats for a specific train and date
     * @param trainId Train ID
     * @param travelDate Travel date
     * @return Number of booked seats
     */
    private int getBookedSeatsCount(int trainId, LocalDate travelDate) {
        // This would need to be implemented in BookingDAO
        // For now, we'll use a simple approach
        try (var connection = app.database.DatabaseConnection.getConnection();
             var statement = connection.prepareStatement(
                 "SELECT COUNT(*) FROM bookings WHERE train_id = ? AND travel_date = ?")) {
            
            statement.setInt(1, trainId);
            statement.setDate(2, java.sql.Date.valueOf(travelDate));
            
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (Exception e) {
            System.err.println("Error getting booked seats count: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Book a ticket for a passenger (today's date)
     * @param trainId Train ID
     * @param passengerName Passenger name
     * @return Booking object if successful, null otherwise
     */
    public Booking bookTicket(int trainId, String passengerName) {
        return bookTicket(trainId, passengerName, LocalDate.now());
    }
    
    /**
     * Get all bookings
     * @return List of all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }
    
    /**
     * Get bookings by passenger name
     * @param passengerName Passenger name
     * @return List of bookings for the passenger
     */
    public List<Booking> getBookingsByPassenger(String passengerName) {
        if (passengerName == null || passengerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger name cannot be empty");
        }
        
        return bookingDAO.getBookingsByPassengerName(passengerName.trim());
    }
    
    /**
     * Cancel a booking
     * @param bookingId Booking ID
     * @return true if successful, false otherwise
     */
    public boolean cancelBooking(int bookingId) {
        if (bookingId <= 0) {
            throw new IllegalArgumentException("Invalid booking ID");
        }
        
        // Get booking details before cancellation
        List<Booking> allBookings = bookingDAO.getAllBookings();
        Booking bookingToCancel = null;
        
        for (Booking booking : allBookings) {
            if (booking.getId() == bookingId) {
                bookingToCancel = booking;
                break;
            }
        }
        
        if (bookingToCancel == null) {
            throw new IllegalArgumentException("Booking not found");
        }
        
        // Cancel the booking
        boolean cancelled = bookingDAO.cancelBooking(bookingId);
        
        if (cancelled) {
            // Update available seats (this is now handled by date-specific logic)
            System.out.println("Booking cancelled for " + bookingToCancel.getTravelDate());
        }
        
        return cancelled;
    }
    
    /**
     * Check available seats for a train (legacy method)
     * @param trainId Train ID
     * @return Number of available seats
     */
    public int checkAvailableSeats(int trainId) {
        return checkAvailableSeats(trainId, LocalDate.now());
    }
    
    /**
     * Check available seats for a train on a specific date
     * @param trainId Train ID
     * @param travelDate Travel date
     * @return Number of available seats
     */
    public int checkAvailableSeats(int trainId, LocalDate travelDate) {
        return getAvailableSeatsByDate(trainId, travelDate);
    }
}
