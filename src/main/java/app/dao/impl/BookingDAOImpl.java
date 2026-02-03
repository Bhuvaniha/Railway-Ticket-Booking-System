package app.dao.impl;

import app.dao.BookingDAO;
import app.database.DatabaseConnection;
import app.model.Booking;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {
    
    @Override
    public Booking bookTicket(int trainId, String passengerName) {
        return bookTicket(trainId, passengerName, LocalDate.now());
    }
    
    public Booking bookTicket(int trainId, String passengerName, LocalDate travelDate) {
        String sql = "INSERT INTO bookings (train_id, passenger_name, seat_number, travel_date, amount) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Generate seat number (simple logic)
            String seatNumber = generateSeatNumber(connection, trainId, travelDate);
            
            // Get train price
            BigDecimal price = getTrainPrice(connection, trainId);
            
            statement.setInt(1, trainId);
            statement.setString(2, passengerName);
            statement.setString(3, seatNumber);
            statement.setDate(4, java.sql.Date.valueOf(travelDate));
            statement.setBigDecimal(5, price);
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Booking booking = new Booking(trainId, passengerName, seatNumber, travelDate, price);
                        booking.setId(generatedKeys.getInt(1));
                        return booking;
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error booking ticket: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY booking_date DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                bookings.add(extractBookingFromResultSet(resultSet));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all bookings: " + e.getMessage());
        }
        
        return bookings;
    }
    
    @Override
    public List<Booking> getBookingsByTrainId(int trainId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE train_id = ? ORDER BY booking_date DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, trainId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(extractBookingFromResultSet(resultSet));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting bookings by train ID: " + e.getMessage());
        }
        
        return bookings;
    }
    
    @Override
    public List<Booking> getBookingsByPassengerName(String passengerName) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE passenger_name LIKE ? ORDER BY booking_date DESC";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, "%" + passengerName + "%");
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bookings.add(extractBookingFromResultSet(resultSet));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting bookings by passenger name: " + e.getMessage());
        }
        
        return bookings;
    }
    
    @Override
    public boolean cancelBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, bookingId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error canceling booking: " + e.getMessage());
            return false;
        }
    }
    
    private String generateSeatNumber(Connection connection, int trainId, LocalDate travelDate) throws SQLException {
        String sql = "SELECT COUNT(*) as booking_count FROM bookings WHERE train_id = ? AND travel_date = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trainId);
            statement.setDate(2, java.sql.Date.valueOf(travelDate));
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("booking_count");
                    return "S" + (count + 1);
                }
            }
        }
        
        return "S1";
    }
    
    private BigDecimal getTrainPrice(Connection connection, int trainId) throws SQLException {
        String sql = "SELECT price FROM trains WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, trainId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("price");
                }
            }
        }
        
        return BigDecimal.ZERO;
    }
    
    private Booking extractBookingFromResultSet(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setId(resultSet.getInt("id"));
        booking.setTrainId(resultSet.getInt("train_id"));
        booking.setPassengerName(resultSet.getString("passenger_name"));
        booking.setSeatNumber(resultSet.getString("seat_number"));
        booking.setTravelDate(resultSet.getDate("travel_date").toLocalDate());
        booking.setBookingDate(resultSet.getTimestamp("booking_date").toLocalDateTime());
        booking.setAmount(resultSet.getBigDecimal("amount"));
        booking.setStatus(resultSet.getString("status"));
        return booking;
    }
}
