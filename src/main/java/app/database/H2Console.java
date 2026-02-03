package app.database;

import java.sql.SQLException;

public class H2Console {
    
    /**
     * Start H2 web console for database access
     * Open browser to http://localhost:8082
     */
    public static void startConsole() {
        try {
            org.h2.tools.Server webServer = org.h2.tools.Server.createWebServer("-webPort", "8082", "-webAllowOthers");
            webServer.start();
            System.out.println("H2 Console started at: http://localhost:8082");
            System.out.println("JDBC URL: jdbc:h2:mem:railwaydb");
            System.out.println("Username: sa");
            System.out.println("Password: (leave blank)");
        } catch (SQLException e) {
            System.err.println("Error starting H2 console: " + e.getMessage());
        }
    }
    
    /**
     * Create a modified DatabaseConnection class for web console access
     */
    public static void main(String[] args) {
        startConsole();
        
        // Keep the console running
        try {
            System.out.println("Press Ctrl+C to stop the console...");
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println("Console stopped");
        }
    }
}
