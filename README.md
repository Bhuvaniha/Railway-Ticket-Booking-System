# Railway Ticket Booking System

A Maven-based JavaFX application for booking railway tickets.

## Prerequisites
- Java 17 or higher (Java 21 recommended)
- Maven 3.6+

## How to Run

1. **Navigate to the project directory:**
   ```bash
   cd /home/bloodmoon/.gemini/antigravity/scratch/railway_ticket_system
   ```

2. **Run the application using Maven:**
   ```bash
   mvn javafx:run
   ```

## Application Usage Flow

1. **Login Screen**
   - User ID: `U001` or `U002`
   - Click **Login**

2. **Search Trains**
   - Source: `CityA`
   - Destination: `CityB`
   - Click **Search**
   - Select the train from the list.
   - Click **Select Train & Book Seats**

3. **Select Seat**
   - Click on any **Green** (available) seat.
   - Click **Proceed to Payment**

4. **Payment**
   - Review amount.
   - Click **Confirm Payment**

5. **Confirmation**
   - View your generated ticket details.
   - Click **Home** to book another.
