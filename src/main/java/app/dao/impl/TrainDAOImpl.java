package app.dao.impl;

import app.dao.TrainDAO;
import app.database.DatabaseConnection;
import app.model.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAOImpl implements TrainDAO {
    
    @Override
    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM trains ORDER BY train_number";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                trains.add(extractTrainFromResultSet(resultSet));
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting all trains: " + e.getMessage());
        }
        
        return trains;
    }
    
    @Override
    public Train getTrainById(int id) {
        String sql = "SELECT * FROM trains WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractTrainFromResultSet(resultSet);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting train by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<Train> getTrainsByRoute(String source, String destination) {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM trains WHERE source = ? AND destination = ? ORDER BY departure_time";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, source);
            statement.setString(2, destination);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    trains.add(extractTrainFromResultSet(resultSet));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting trains by route: " + e.getMessage());
        }
        
        return trains;
    }
    
    @Override
    public boolean updateAvailableSeats(int trainId, int availableSeats) {
        String sql = "UPDATE trains SET available_seats = ? WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, availableSeats);
            statement.setInt(2, trainId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating available seats: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public int checkAvailableSeats(int trainId) {
        String sql = "SELECT available_seats FROM trains WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, trainId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("available_seats");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error checking available seats: " + e.getMessage());
        }
        
        return 0;
    }
    
    private Train extractTrainFromResultSet(ResultSet resultSet) throws SQLException {
        Train train = new Train();
        train.setId(resultSet.getInt("id"));
        train.setTrainNumber(resultSet.getString("train_number"));
        train.setTrainName(resultSet.getString("train_name"));
        train.setSource(resultSet.getString("source"));
        train.setDestination(resultSet.getString("destination"));
        train.setDepartureTime(resultSet.getString("departure_time"));
        train.setArrivalTime(resultSet.getString("arrival_time"));
        train.setTotalSeats(resultSet.getInt("total_seats"));
        train.setAvailableSeats(resultSet.getInt("available_seats"));
        train.setPrice(resultSet.getBigDecimal("price"));
        return train;
    }
}
