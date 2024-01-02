package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.dmiit3iy.App;
import org.dmiit3iy.model.User;
import org.dmiit3iy.retrofit.UserRepository;

import java.io.IOException;
import java.net.ConnectException;
import java.util.prefs.Preferences;

public class AuthorizationController {
    @FXML
    public TextField textFieldLogin;
    @FXML
    public TextField textFieldPassword;
    private String login;
    private String password;
    private UserRepository userRepository;

    private Preferences pref = Preferences.userNodeForPackage(App.class);

    @FXML
    public void buttonEnter(ActionEvent actionEvent) throws IOException {
        login = textFieldLogin.getText();
        password = textFieldPassword.getText();
        userRepository = new UserRepository();
        User user = null;
        try {
            user = userRepository.get(login, password);
            pref.putLong("userID", user.getId());
            if (user != null) {

                App.openWindow("main.fxml", "Main window", user);
                App.closeWindow(actionEvent);

            } else {
                App.showMessage("Mistake", "Incorrect login or password", Alert.AlertType.ERROR);
            }
        } catch (ConnectException e) {
            App.showMessage("Warning", "There is no connection to the server", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void buttonRegistration(ActionEvent actionEvent) throws IOException {
        App.openWindow("registration.fxml","Registration window",null);
    }
}
