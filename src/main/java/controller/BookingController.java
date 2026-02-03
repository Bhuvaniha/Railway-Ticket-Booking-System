package controller;

import app.model.Train;
import app.model.Seat;
import app.model.Booking;
import app.service.TrainService;
import app.service.BookingService;
import app.database.DatabaseSchema;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import app.MainApp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingController {

    // Seat Selection Fields
    @FXML
    private Label trainNameLabel;
    @FXML
    private FlowPane seatContainer;
    @FXML
    private Label selectedSeatLabel;
    @FXML
    private Label errorLabel;

    // Payment Fields
    @FXML
    private Label amountLabel;

    // Ticket Fields
    @FXML
    private Label ticketDetailsLabel;

    private static Seat selectedSeat;
    private static List<Seat> selectedSeats;
    private static Booking confirmedBooking;
    private TrainService trainService;
    private BookingService bookingService;

    // Seat Selection Methods
    public void initialize() {
        trainService = new TrainService();
        bookingService = new BookingService();
        selectedSeats = new ArrayList<>();
        
        // Since this controller is shared, we should check which view is loaded
        if (seatContainer != null) {
            // Seat View
            loadSeatSelection();
        }

        if (amountLabel != null) {
            // Payment View
            loadPaymentView();
        }

        if (ticketDetailsLabel != null && confirmedBooking != null) {
            // Ticket View
            loadTicketView();
        }
    }
    
    private void loadSeatSelection() {
        Train train = TrainController.getSelectedTrain();
        LocalDate travelDate = TrainController.getSelectedTravelDate();
        
        if (train != null && travelDate != null) {
            trainNameLabel.setText("Train: " + train.getTrainName() + " (" + train.getTrainNumber() + ")");
            trainNameLabel.setText(trainNameLabel.getText() + "\nDate: " + travelDate);
            loadSeats(train, travelDate);
        } else {
            errorLabel.setText("No train selected or travel date not set.");
        }
    }

    private void loadSeats(Train train, LocalDate travelDate) {
        seatContainer.getChildren().clear();
        
        // Get available seats for this train and date
        int availableSeats = trainService.checkAvailableSeats(train.getId());
        int totalSeats = train.getTotalSeats();
        
        // Create seat layout (simple grid)
        List<Seat> seats = new ArrayList<>();
        
        // Create AC seats (first 20% of seats)
        int acSeats = (int) (totalSeats * 0.2);
        for (int i = 1; i <= acSeats; i++) {
            seats.add(new Seat("AC-" + i, "AC"));
        }
        
        // Create Sleeper seats (remaining seats)
        for (int i = 1; i <= (totalSeats - acSeats); i++) {
            seats.add(new Seat("SL-" + i, "Sleeper"));
        }
        
        // Mark some seats as booked based on availability
        int bookedCount = totalSeats - availableSeats;
        for (int i = 0; i < bookedCount && i < seats.size(); i++) {
            seats.get(i).setBooked(true);
        }
        
        // Create seat buttons
        for (Seat seat : seats) {
            Button seatButton = new Button(seat.getSeatNumber());
            seatButton.setPrefSize(80, 40);
            seatButton.setStyle("-fx-font-size: 10px;");

            if (seat.isBooked()) {
                seatButton.setStyle("-fx-background-color: #ff6b6b; -fx-text-fill: white;");
                seatButton.setDisable(true);
            } else {
                seatButton.setStyle("-fx-background-color: #51cf66; -fx-text-fill: white;");
                seatButton.setOnAction(e -> handleSeatClick(seat));
            }

            seatContainer.getChildren().add(seatButton);
        }
        
        // Add legend
        Label legend = new Label("Green: Available | Red: Booked");
        legend.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        seatContainer.getChildren().add(legend);
    }

    private void handleSeatClick(Seat seat) {
        if (selectedSeats.contains(seat)) {
            // Deselect seat if already selected
            selectedSeats.remove(seat);
        } else {
            // Select seat if not already selected
            selectedSeats.add(seat);
        }
        
        // Update selected seat label
        if (selectedSeatLabel != null) {
            if (selectedSeats.isEmpty()) {
                selectedSeatLabel.setText("No seats selected");
            } else {
                StringBuilder seatList = new StringBuilder("Selected Seats: ");
                for (int i = 0; i < selectedSeats.size(); i++) {
                    seatList.append(selectedSeats.get(i).getSeatNumber());
                    if (i < selectedSeats.size() - 1) {
                        seatList.append(", ");
                    }
                }
                selectedSeatLabel.setText(seatList.toString());
            }
        }
        errorLabel.setText("");
    }

    @FXML
    private void handleProceedToPayment() throws IOException {
        if (selectedSeats != null && !selectedSeats.isEmpty()) {
            MainApp.setRoot("payment");
        } else {
            if (errorLabel != null) {
                errorLabel.setText("Please select at least one seat.");
            }
        }
    }

    // Payment Methods
    private void loadPaymentView() {
        Train train = TrainController.getSelectedTrain();
        if (train != null && selectedSeats != null && !selectedSeats.isEmpty()) {
            double totalAmount = train.getPrice().doubleValue() * selectedSeats.size();
            amountLabel.setText("Amount to Pay: $" + totalAmount + " (" + selectedSeats.size() + " seat(s))");
        } else {
            amountLabel.setText("Amount: $0.00");
        }
    }
    
    @FXML
    private void handlePayment() throws IOException {
        try {
            Train train = TrainController.getSelectedTrain();
            LocalDate travelDate = TrainController.getSelectedTravelDate();
            
            if (train == null || travelDate == null || selectedSeats == null || selectedSeats.isEmpty()) {
                errorLabel.setText("Missing booking information.");
                return;
            }
            
            // Create bookings for all selected seats
            List<Booking> bookings = new ArrayList<>();
            for (Seat seat : selectedSeats) {
                Booking booking = bookingService.bookTicket(
                    train.getId(), 
                    "Passenger", // You can get this from login
                    travelDate
                );
                if (booking != null) {
                    bookings.add(booking);
                }
            }
            
            if (!bookings.isEmpty()) {
                confirmedBooking = bookings.get(0); // Store first booking for display
                MainApp.setRoot("ticket");
            } else {
                errorLabel.setText("Booking failed. Please try again.");
            }
            
        } catch (Exception e) {
            errorLabel.setText("Payment error: " + e.getMessage());
        }
    }

    // Ticket Methods
    private void loadTicketView() {
        if (confirmedBooking != null) {
            Train train = TrainController.getSelectedTrain();
            StringBuilder seatsInfo = new StringBuilder();
            if (selectedSeats != null && !selectedSeats.isEmpty()) {
                for (int i = 0; i < selectedSeats.size(); i++) {
                    seatsInfo.append(selectedSeats.get(i).getSeatNumber());
                    if (i < selectedSeats.size() - 1) {
                        seatsInfo.append(", ");
                    }
                }
            }
            
            String details = "🎫 BOOKING CONFIRMED 🎫\n\n" +
                    "Booking ID: " + confirmedBooking.getId() + "\n" +
                    "Train: " + train.getTrainNumber() + " - " + train.getTrainName() + "\n" +
                    "Route: " + train.getSource() + " to " + train.getDestination() + "\n" +
                    "Travel Date: " + confirmedBooking.getTravelDate() + "\n" +
                    "Seat(s): " + (seatsInfo.length() > 0 ? seatsInfo.toString() : "N/A") + "\n" +
                    "Number of Seats: " + (selectedSeats != null ? selectedSeats.size() : 1) + "\n" +
                    "Passenger: Passenger Name\n" +
                    "Amount Paid: $" + confirmedBooking.getAmount() + "\n" +
                    "Status: " + confirmedBooking.getStatus() + "\n\n" +
                    "🎉 Happy Journey! 🎉";
            ticketDetailsLabel.setText(details);
        }
    }

    @FXML
    private void handleHome() throws IOException {
        // Reset selections
        selectedSeat = null;
        if (selectedSeats != null) {
            selectedSeats.clear();
        }
        confirmedBooking = null;
        MainApp.setRoot("train");
    }
}
