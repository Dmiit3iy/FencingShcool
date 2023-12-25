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
import org.dmiit3iy.Program;
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
            Program.showMessage("Success", "the user has been successfully registered", Alert.AlertType.INFORMATION);
            Stage stage = (Stage) textFieldLogin.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/authorization.fxml"));
            Stage stage1 = new Stage(StageStyle.DECORATED);
            stage1.setScene(new Scene(loader.load()));
            stage1.show();
        } catch (IOException e) {
            Program.showMessage("Warning", "User is not added", Alert.AlertType.WARNING);
        }
        finally {
            textFieldLogin.clear();
            textFieldName.clear();
            textFieldPassword.clear();
        }

    }
}
