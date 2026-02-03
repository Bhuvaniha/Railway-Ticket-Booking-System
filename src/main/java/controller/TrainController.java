package controller;

import app.model.Train;
import app.service.TrainService;
import app.database.DatabaseSchema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import app.MainApp;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TrainController {

    @FXML
    private TextField sourceField;
    @FXML
    private TextField destinationField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Train> trainTable;
    @FXML
    private TableColumn<Train, String> trainIdColumn;
    @FXML
    private TableColumn<Train, String> trainNameColumn;
    @FXML
    private TableColumn<Train, String> sourceColumn;
    @FXML
    private TableColumn<Train, String> destColumn;
    @FXML
    private TableColumn<Train, Integer> seatsColumn;
    @FXML
    private Button selectButton;
    @FXML
    private Label errorLabel;

    // Store selected train and travel date so SeatSelection can access it
    private static Train selectedTrain;
    private static LocalDate selectedTravelDate;
    private TrainService trainService;

    public static Train getSelectedTrain() {
        return selectedTrain;
    }
    
    public static LocalDate getSelectedTravelDate() {
        return selectedTravelDate;
    }

    @FXML
    public void initialize() {
        // Initialize database and service
        DatabaseSchema.createTables();
        DatabaseSchema.insertSampleData();
        trainService = new TrainService();
        
        // Set up table columns
        trainIdColumn.setCellValueFactory(new PropertyValueFactory<>("trainNumber"));
        trainNameColumn.setCellValueFactory(new PropertyValueFactory<>("trainName"));
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        destColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        
        // Set date picker to today by default
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void handleSearch() {
        String source = sourceField.getText();
        String dest = destinationField.getText();
        LocalDate travelDate = datePicker.getValue();

        if (source == null || source.trim().isEmpty() || dest == null || dest.trim().isEmpty()) {
            errorLabel.setText("Please enter both source and destination.");
            return;
        }
        
        if (travelDate == null) {
            errorLabel.setText("Please select a travel date.");
            return;
        }
        
        if (travelDate.isBefore(LocalDate.now())) {
            errorLabel.setText("Travel date cannot be in the past.");
            return;
        }

        try {
            List<Train> results = trainService.searchTrains(source.trim(), dest.trim());
            ObservableList<Train> observableList = FXCollections.observableArrayList(results);
            
            // Update available seats for each train based on travel date
            for (Train train : observableList) {
                int availableSeats = trainService.checkAvailableSeats(train.getId());
                train.setAvailableSeats(availableSeats);
            }
            
            trainTable.setItems(observableList);

            if (results.isEmpty()) {
                errorLabel.setText("No trains found for this route.");
            } else {
                errorLabel.setText("Found " + results.size() + " train(s) for this route.");
            }
        } catch (Exception e) {
            errorLabel.setText("Error searching trains: " + e.getMessage());
        }
    }

    @FXML
    private void handleSelectTrain() throws IOException {
        Train train = trainTable.getSelectionModel().getSelectedItem();
        LocalDate travelDate = datePicker.getValue();
        
        if (train != null) {
            if (travelDate == null) {
                errorLabel.setText("Please select a travel date first.");
                return;
            }
            
            int availableSeats = trainService.checkAvailableSeats(train.getId());
            
            if (availableSeats <= 0) {
                errorLabel.setText("No seats available on this train for the selected date.");
                return;
            }
            
            selectedTrain = train;
            selectedTravelDate = travelDate;
            
            errorLabel.setText("Selected: " + train.getTrainName() + " - Available seats: " + availableSeats);
            
            // Navigate to seat selection
            MainApp.setRoot("seat");
        } else {
            errorLabel.setText("Please select a train first.");
        }
    }
}
