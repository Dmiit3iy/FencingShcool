package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.dmiit3iy.model.User;

import java.util.prefs.Preferences;

public class MainController {

    public Button buttonLogOff;
    User user;

    public void initData(User user) {
        this.user = user;
    }

    @FXML
    public void buttonLogOff(ActionEvent actionEvent) {
        Preferences userlog = Preferences.userRoot();
        userlog.put("authorization", "null");
        Preferences userIDlog = Preferences.userRoot();
        userlog.putBoolean("authorization", false);
        userIDlog.put("userID","-1");
        Stage stage1 = (Stage) buttonLogOff.getScene().getWindow();
        stage1.close();
    }
}
