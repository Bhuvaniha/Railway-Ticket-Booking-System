package app.dao;

import app.model.Booking;
import java.util.List;

public interface BookingDAO {
    
    /**
     * Book a ticket for a passenger
     * @param trainId Train ID
     * @param passengerName Passenger name
     * @return Booking object if successful, null otherwise
     */
    Booking bookTicket(int trainId, String passengerName);
    
    /**
     * Get all bookings from the database
     * @return List of all bookings
     */
    List<Booking> getAllBookings();
    
    /**
     * Get bookings by train ID
     * @param trainId Train ID
     * @return List of bookings for the train
     */
    List<Booking> getBookingsByTrainId(int trainId);
    
    /**
     * Get bookings by passenger name
     * @param passengerName Passenger name
     * @return List of bookings for the passenger
     */
    List<Booking> getBookingsByPassengerName(String passengerName);
    
    /**
     * Cancel a booking
     * @param bookingId Booking ID
     * @return true if successful, false otherwise
     */
    boolean cancelBooking(int bookingId);
}
