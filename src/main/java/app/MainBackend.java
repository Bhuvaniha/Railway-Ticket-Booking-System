package app;

import app.database.DatabaseSchema;
import app.model.Train;
import app.model.Booking;
import app.service.TrainService;
import app.service.BookingService;

import java.util.List;

public class MainBackend {
    
    public static void main(String[] args) {
        // Initialize database
        System.out.println("=== Initializing Railway Ticket System Backend ===");
        DatabaseSchema.createTables();
        DatabaseSchema.insertSampleData();
        
        // Create services
        TrainService trainService = new TrainService();
        BookingService bookingService = new BookingService();
        
        // Demo 1: Get all trains
        System.out.println("\n=== All Available Trains ===");
        List<Train> trains = trainService.getAllTrains();
        for (Train train : trains) {
            System.out.println(train + " - Available Seats: " + train.getAvailableSeats());
        }
        
        // Demo 2: Search trains by route
        System.out.println("\n=== Trains from CityA to CityB ===");
        List<Train> cityAToCityBTrains = trainService.searchTrains("CityA", "CityB");
        for (Train train : cityAToCityBTrains) {
            System.out.println(train + " - Price: $" + train.getPrice());
        }
        
        // Demo 3: Check available seats
        if (!trains.isEmpty()) {
            int firstTrainId = trains.get(0).getId();
            System.out.println("\n=== Available Seats for Train ID " + firstTrainId + " ===");
            int availableSeats = trainService.checkAvailableSeats(firstTrainId);
            System.out.println("Available seats: " + availableSeats);
            
            // Demo 4: Book a ticket
            System.out.println("\n=== Booking a Ticket ===");
            Booking booking = bookingService.bookTicket(firstTrainId, "John Doe");
            if (booking != null) {
                System.out.println("Booking successful: " + booking);
                System.out.println("Updated available seats: " + trainService.checkAvailableSeats(firstTrainId));
            } else {
                System.out.println("Booking failed");
            }
            
            // Demo 5: Book another ticket
            System.out.println("\n=== Booking Another Ticket ===");
            Booking booking2 = bookingService.bookTicket(firstTrainId, "Jane Smith");
            if (booking2 != null) {
                System.out.println("Booking successful: " + booking2);
                System.out.println("Updated available seats: " + trainService.checkAvailableSeats(firstTrainId));
            }
        }
        
        // Demo 6: Get all bookings
        System.out.println("\n=== All Bookings ===");
        List<Booking> bookings = bookingService.getAllBookings();
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
        
        // Demo 7: Search bookings by passenger
        System.out.println("\n=== Bookings for John Doe ===");
        List<Booking> johnBookings = bookingService.getBookingsByPassenger("John Doe");
        for (Booking booking : johnBookings) {
            System.out.println(booking);
        }
        
        System.out.println("\n=== Backend Demo Complete ===");
    }
}
