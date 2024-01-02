package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dmiit3iy.App;
import org.dmiit3iy.model.User;
import org.dmiit3iy.retrofit.UserRepository;

import java.io.IOException;

public class RegistrationController {
    UserRepository userRepository;

    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldName;
    @FXML
    public PasswordField textFieldPassword;

    @FXML
    public void buttonRegistration(ActionEvent actionEvent) {
        userRepository = new UserRepository();
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();
        String name = textFieldName.getText();
        User user = new User(login, password, name);
        try {
            userRepository.post(user);
            App.showMessage("Success", "the user has been successfully registered", Alert.AlertType.INFORMATION);
            App.closeWindow(actionEvent);
            App.openWindow("authorization.fxml", "", null);
        } catch (IOException e) {
            App.showMessage("Warning", "User is not added", Alert.AlertType.WARNING);
        }
    }
}
