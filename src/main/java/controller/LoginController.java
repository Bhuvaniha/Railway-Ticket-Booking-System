package controller;

import model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import app.MainApp;
import model.DataStore;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField userIdField;

    @FXML
    private Label errorLabel;

    @FXML
    private void handleLogin() throws IOException {
        String userId = userIdField.getText();
        User user = DataStore.getInstance().authenticate(userId);

        if (user != null) {
            DataStore.getInstance().setCurrentUser(user);
            MainApp.setRoot("train");
        } else {
            errorLabel.setText("Invalid User ID. Try U001 or U002.");
        }
    }
}
