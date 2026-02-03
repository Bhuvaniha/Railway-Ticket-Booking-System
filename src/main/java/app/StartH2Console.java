package app;

import java.sql.SQLException;

public class StartH2Console {
    
    public static void main(String[] args) {
        try {
            // Start H2 web server
            org.h2.tools.Server webServer = org.h2.tools.Server.createWebServer("-webPort", "8083", "-webAllowOthers");
            webServer.start();
            
            System.out.println("=== H2 Database Console Started ===");
            System.out.println("Open your browser and go to: http://localhost:8083");
            System.out.println("JDBC URL: jdbc:h2:mem:railwaydb");
            System.out.println("Username: sa");
            System.out.println("Password: (leave blank)");
            System.out.println("\nPress Ctrl+C to stop the console");
            
            // Keep the server running
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                System.out.println("\nH2 Console stopped");
                webServer.stop();
            }
            
        } catch (SQLException e) {
            System.err.println("Error starting H2 console: " + e.getMessage());
        }
    }
}
