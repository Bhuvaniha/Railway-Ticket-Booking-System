package app;

import app.database.DatabaseSchema;
import app.model.Train;
import app.model.Booking;
import app.service.TrainService;
import app.service.BookingService;

import java.util.List;
import java.util.Scanner;

public class MainWithConsole {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Railway Ticket System ===");
        System.out.println("1. Run Backend Demo");
        System.out.println("2. Start H2 Database Console");
        System.out.println("3. Exit");
        System.out.print("Choose option (1-3): ");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                runBackendDemo();
                break;
            case 2:
                startH2Console();
                break;
            case 3:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid option");
        }
        
        scanner.close();
    }
    
    private static void runBackendDemo() {
        // Initialize database
        System.out.println("\n=== Initializing Railway Ticket System Backend ===");
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
        System.out.println("\n=== Trains from Mumbai to Delhi ===");
        List<Train> mumbaiToDelhi = trainService.searchTrains("Mumbai", "Delhi");
        for (Train train : mumbaiToDelhi) {
            System.out.println(train + " - Price: $" + train.getPrice());
        }
        
        System.out.println("\n=== Backend Demo Complete ===");
    }
    
    private static void startH2Console() {
        try {
            System.out.println("\n=== Starting H2 Database Console ===");
            org.h2.tools.Server webServer = org.h2.tools.Server.createWebServer("-webPort", "8082", "-webAllowOthers");
            webServer.start();
            
            System.out.println("H2 Console started at: http://localhost:8082");
            System.out.println("JDBC URL: jdbc:h2:mem:railwaydb");
            System.out.println("Username: sa");
            System.out.println("Password: (leave blank)");
            System.out.println("\nPress Enter to stop the console...");
            
            // Wait for user to press Enter
            new Scanner(System.in).nextLine();
            
            webServer.stop();
            System.out.println("H2 Console stopped");
            
        } catch (Exception e) {
            System.err.println("Error starting H2 console: " + e.getMessage());
        }
    }
}
