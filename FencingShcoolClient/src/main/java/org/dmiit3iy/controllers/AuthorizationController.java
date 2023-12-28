package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    UserRepository userRepository;

    @FXML
    public void buttonEnter(ActionEvent actionEvent) throws IOException {
        login = textFieldLogin.getText();
        password = textFieldPassword.getText();
        userRepository = new UserRepository();
        User user = null;
        try {
            user = userRepository.get(login, password);

            if (user != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/main.fxml"));
                Stage stage = new Stage(StageStyle.DECORATED);
                stage.setScene(new Scene(loader.load()));

                MainController mainController =loader.getController();
                mainController.initData(user);

                Preferences userlog = Preferences.userRoot();
                Preferences userIDlog = Preferences.userRoot();
                String userID = String.valueOf(user.getId());
                userlog.putBoolean("authorization", true);
                userIDlog.put("userID", userID);
                textFieldPassword.clear();
                textFieldLogin.clear();
                stage.show();
                Stage stage1 = (Stage) textFieldLogin.getScene().getWindow();
                stage1.close();
            } else {
                App.showMessage("Mistake", "Incorrect login or password", Alert.AlertType.ERROR);
            }
        } catch (ConnectException e) {
            App.showMessage("Warning", "There is no connection to the server", Alert.AlertType.WARNING);
        }

    }

    @FXML
    public void buttonRegistration(ActionEvent actionEvent) throws IOException {
        //Stage stage1 = (Stage) textFieldLogin.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/registration.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        stage.show();
        Stage stage1 =(Stage) textFieldLogin.getScene().getWindow();
        stage1.close();
    }
}
