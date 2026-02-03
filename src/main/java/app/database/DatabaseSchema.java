package app.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSchema {
    
    public static void createTables() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            
            // Create Trains table
            String createTrainsTable = "CREATE TABLE IF NOT EXISTS trains (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "train_number VARCHAR(20) UNIQUE NOT NULL, " +
                "train_name VARCHAR(100) NOT NULL, " +
                "source VARCHAR(50) NOT NULL, " +
                "destination VARCHAR(50) NOT NULL, " +
                "departure_time VARCHAR(10) NOT NULL, " +
                "arrival_time VARCHAR(10) NOT NULL, " +
                "total_seats INT NOT NULL DEFAULT 50, " +
                "available_seats INT NOT NULL DEFAULT 50, " +
                "price DECIMAL(10,2) NOT NULL" +
                ")";
            
            // Create Bookings table
            String createBookingsTable = "CREATE TABLE IF NOT EXISTS bookings (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "train_id INT NOT NULL, " +
                "passenger_name VARCHAR(100) NOT NULL, " +
                "seat_number VARCHAR(10) NOT NULL, " +
                "travel_date DATE NOT NULL, " +
                "booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "amount DECIMAL(10,2) NOT NULL, " +
                "status VARCHAR(20) DEFAULT 'CONFIRMED', " +
                "FOREIGN KEY (train_id) REFERENCES trains(id)" +
                ")";
            
            statement.execute(createTrainsTable);
            statement.execute(createBookingsTable);
            
            System.out.println("Database tables created successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error creating database tables: " + e.getMessage());
        }
    }
    
    public static void insertSampleData() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            
            // Clear existing data
            statement.execute("DELETE FROM bookings");
            statement.execute("DELETE FROM trains");
            
            // Insert sample Indian trains
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12951', 'Mumbai Rajdhani', 'Mumbai', 'Delhi', '17:00', '08:15', 75, 75, 3850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12302', 'Howrah Rajdhani', 'Kolkata', 'Delhi', '16:50', '09:35', 75, 75, 4050.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12431', 'Thirukural Express', 'Chennai', 'Delhi', '07:40', '11:45', 72, 72, 2150.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12625', 'Kerala Express', 'Thiruvananthapuram', 'Delhi', '11:15', '13:40', 72, 72, 2850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12259', 'Duronto Express', 'Bangalore', 'Delhi', '20:00', '06:40', 78, 78, 2650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12723', 'Andhra Pradesh Express', 'Hyderabad', 'Delhi', '18:25', '13:00', 72, 72, 1950.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('11077', 'Jhelum Express', 'Pune', 'Jammu', '21:55', '15:10', 72, 72, 1650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12137', 'Punjab Mail', 'Mumbai', 'Firozpur', '19:40', '04:50', 72, 72, 1450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12859', 'Gitangiri Express', 'Nagpur', 'Mumbai', '18:45', '06:30', 72, 72, 850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12345', 'Saraighat Express', 'Guwahati', 'Delhi', '14:30', '13:00', 72, 72, 2250.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12555', 'Gorakhpur Express', 'Mumbai', 'Gorakhpur', '06:35', '13:30', 72, 72, 1250.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12707', 'Dibrugarh Rajdhani', 'Delhi', 'Dibrugarh', '09:35', '04:15', 72, 72, 4250.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12059', 'Kolkata Jan Shatabdi', 'Kolkata', 'Dhanbad', '06:05', '09:30', 78, 78, 450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12051', 'Jan Shatabdi Express', 'Delhi', 'Kanpur', '13:40', '18:15', 78, 78, 350.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12235', 'Madhya Pradesh Sampark', 'Delhi', 'Bhopal', '21:05', '06:30', 72, 72, 950.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12617', 'Ernakulam Express', 'Bangalore', 'Ernakulam', '17:30', '04:00', 72, 72, 650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12101', 'Jnaneswari Express', 'Mumbai', 'Kolkata', '20:35', '04:15', 72, 72, 1850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12801', 'Purushottam Express', 'Puri', 'Delhi', '21:55', '05:10', 72, 72, 2450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12395', 'Ziyarat Express', 'Delhi', 'Ajmer', '20:15', '05:30', 72, 72, 450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12493', 'Bangalore Rajdhani', 'Delhi', 'Bangalore', '20:50', '11:20', 75, 75, 3650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12511', 'Rapti Sagar Express', 'Gorakhpur', 'Trivandrum', '06:35', '13:30', 72, 72, 1850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12621', 'Tamil Nadu Express', 'Delhi', 'Chennai', '22:35', '07:05', 72, 72, 1750.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12725', 'South India Express', 'Delhi', 'Hyderabad', '21:15', '12:50', 72, 72, 1550.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12775', 'Humsafar Express', 'Delhi', 'Bengaluru', '20:10', '08:25', 72, 72, 1950.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12807', 'Samata Express', 'Vijayawada', 'Delhi', '06:00', '13:30', 72, 72, 1350.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12925', 'Paschim Express', 'Mumbai', 'Delhi', '12:00', '15:35', 72, 72, 1650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12953', 'August Kranti Rajdhani', 'Mumbai', 'Delhi', '17:40', '09:15', 75, 75, 3750.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12957', 'Swarna Jayanti Rajdhani', 'Ahmedabad', 'Delhi', '19:20', '10:40', 75, 75, 2850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12961', 'Avantika Express', 'Mumbai', 'Indore', '19:50', '07:40', 72, 72, 750.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12969', 'Cochin Express', 'Mumbai', 'Kochi', '09:35', '13:45', 72, 72, 1450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('19019', 'Dehradun Express', 'Mumbai', 'Dehradun', '08:25', '13:30', 72, 72, 1250.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('19415', 'Bandra Terminus Express', 'Delhi', 'Bandra', '16:55', '10:15', 72, 72, 1550.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22125', 'Madhya Pradesh Express', 'Mumbai', 'Delhi', '16:35', '10:15', 72, 72, 1450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22221', 'CSMT Mumbai Express', 'Delhi', 'Mumbai', '23:45', '13:10', 72, 72, 1550.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22417', 'Garib Rath Express', 'Delhi', 'Allahabad', '19:35', '02:30', 72, 72, 650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22635', 'Patna Express', 'Bangalore', 'Patna', '01:30', '09:45', 72, 72, 1850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22641', 'Chennai Express', 'Mumbai', 'Chennai', '23:55', '07:20', 72, 72, 1350.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22663', 'Thirukkural Express', 'Mumbai', 'Kanniyakumari', '20:35', '05:15', 72, 72, 1850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22709', 'Nizamuddin Express', 'Nizamuddin', 'Visakhapatnam', '08:20', '13:45', 72, 72, 1650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22710', 'Nizamuddin Express', 'Visakhapatnam', 'Nizamuddin', '23:50', '04:30', 72, 72, 1650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22849', 'Sampark Kranti Express', 'Delhi', 'Bhubaneswar', '14:35', '16:55', 72, 72, 1650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22905', 'Okha Express', 'Delhi', 'Okha', '22:55', '12:35', 72, 72, 1850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22953', 'Gujarat Express', 'Mumbai', 'Ahmedabad', '07:00', '12:15', 72, 72, 550.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22969', 'Uttaranchal Express', 'Mumbai', 'Dehradun', '09:35', '14:40', 72, 72, 1150.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22971', 'Bandra Terminus Express', 'Jaipur', 'Bandra', '13:15', '06:45', 72, 72, 1250.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22973', 'Bandra Terminus Express', 'Bandra', 'Jaipur', '14:50', '08:20', 72, 72, 1250.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22977', 'Bandra Terminus Express', 'Bandra', 'Jodhpur', '21:40', '12:15', 72, 72, 1150.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22979', 'Saurashtra Express', 'Mumbai', 'Porbandar', '09:20', '06:45', 72, 72, 850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22987', 'Bandra Terminus Express', 'Bandra', 'Jamnagar', '23:50', '09:30', 72, 72, 950.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22991', 'Uttaranchal Express', 'Delhi', 'Dehradun', '23:50', '04:20', 72, 72, 450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22999', 'Bandra Terminus Express', 'Bandra', 'Bhuj', '23:50', '11:15', 72, 72, 1050.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('23009', 'Bandra Terminus Express', 'Bandra', 'Rajkot', '21:45', '09:30', 72, 72, 850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('23181', 'Kolkata Express', 'Delhi', 'Kolkata', '23:55', '06:10', 72, 72, 1550.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('23187', 'Sealdah Express', 'Delhi', 'Sealdah', '22:40', '04:55', 72, 72, 1550.00)");
            
            // Additional trains for Chennai routes
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12621', 'Tamil Nadu Express', 'Chennai', 'Delhi', '22:00', '06:30', 72, 72, 1650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12622', 'Tamil Nadu Express', 'Delhi', 'Chennai', '22:15', '06:45', 72, 72, 1650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12431', 'Thirukural Express', 'Chennai', 'Delhi', '09:30', '18:00', 72, 72, 1450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12432', 'Thirukural Express', 'Delhi', 'Chennai', '10:00', '18:30', 72, 72, 1450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12841', 'Coromandel Express', 'Chennai', 'Kolkata', '08:45', '13:30', 72, 72, 1250.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12842', 'Coromandel Express', 'Kolkata', 'Chennai', '14:45', '19:30', 72, 72, 1250.00)");
            
            // Additional trains for Mumbai routes
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12951', 'Mumbai Rajdhani', 'Mumbai', 'Delhi', '17:00', '08:30', 75, 75, 3850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12952', 'Mumbai Rajdhani', 'Delhi', 'Mumbai', '16:55', '08:25', 75, 75, 3850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12953', 'August Kranti Rajdhani', 'Mumbai', 'Delhi', '17:40', '10:10', 75, 75, 3650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12954', 'August Kranti Rajdhani', 'Delhi', 'Mumbai', '21:15', '13:45', 75, 75, 3650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12147', 'Mumbai Express', 'Mumbai', 'Kolkata', '21:15', '03:30', 72, 72, 1850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12148', 'Mumbai Express', 'Kolkata', 'Mumbai', '23:45', '06:00', 72, 72, 1850.00)");
            
            // Additional trains for Kolkata routes
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12301', 'Howrah Rajdhani', 'Kolkata', 'Delhi', '14:35', '10:45', 75, 75, 4050.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12302', 'Howrah Rajdhani', 'Delhi', 'Kolkata', '16:55', '13:05', 75, 75, 4050.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12259', 'Duronto Express', 'Kolkata', 'Delhi', '13:05', '10:35', 78, 78, 2450.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12260', 'Duronto Express', 'Delhi', 'Kolkata', '23:15', '20:45', 78, 78, 2450.00)");
            
            // Additional trains for Thiruvananthapuram routes
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12625', 'Kerala Express', 'Thiruvananthapuram', 'Delhi', '12:45', '15:20', 72, 72, 1950.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12626', 'Kerala Express', 'Delhi', 'Thiruvananthapuram', '09:25', '12:00', 72, 72, 1950.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12695', 'Thiruvananthapuram Express', 'Thiruvananthapuram', 'Mumbai', '15:30', '16:45', 72, 72, 1550.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12696', 'Thiruvananthapuram Express', 'Mumbai', 'Thiruvananthapuram', '12:00', '13:15', 72, 72, 1550.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22641', 'Chennai Express', 'Thiruvananthapuram', 'Chennai', '12:50', '10:40', 72, 72, 850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22642', 'Chennai Express', 'Chennai', 'Thiruvananthapuram', '19:35', '17:25', 72, 72, 850.00)");
            
            // Additional inter-city trains
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22661', 'Chennai Express', 'Mumbai', 'Chennai', '23:45', '06:30', 72, 72, 1250.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22662', 'Chennai Express', 'Chennai', 'Mumbai', '14:00', '20:45', 72, 72, 1250.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22663', 'Thirukkural Express', 'Mumbai', 'Kanniyakumari', '20:35', '09:45', 72, 72, 1350.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('22664', 'Thirukkural Express', 'Kanniyakumari', 'Mumbai', '15:10', '04:20', 72, 72, 1350.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12723', 'Andhra Pradesh Express', 'Hyderabad', 'Delhi', '18:20', '13:10', 72, 72, 1750.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12724', 'Andhra Pradesh Express', 'Delhi', 'Hyderabad', '06:00', '00:50', 72, 72, 1750.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12775', 'Humsafar Express', 'Delhi', 'Bengaluru', '20:10', '09:30', 72, 72, 1850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12776', 'Humsafar Express', 'Bengaluru', 'Delhi', '10:15', '23:35', 72, 72, 1850.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12787', 'Humsafar Express', 'Delhi', 'Secunderabad', '21:15', '12:45', 72, 72, 1650.00)");
            
            statement.execute("INSERT INTO trains (train_number, train_name, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                "('12788', 'Humsafar Express', 'Secunderabad', 'Delhi', '09:30', '01:00', 72, 72, 1650.00)");
            
            System.out.println("Sample data inserted successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error inserting sample data: " + e.getMessage());
        }
    }
}
