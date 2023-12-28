package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.dmiit3iy.model.Apprentice;

public class ApprenticeController {
    public TextField textFieldSurname;
    public TextField textFieldName;
    public TextField textFieldPatronymic;
    public TextField textFieldPhone;
    public ListView listViewTraining;

    private Apprentice apprentice;

    @FXML
    public void buttonUpdateApprentice(ActionEvent actionEvent) {
    }

    @FXML
    public void buttonRemoveApprentice(ActionEvent actionEvent) {
    }

    @FXML
    public void buttonAddTraining(ActionEvent actionEvent) {
    }

    @FXML
    public void buttonRemoveTraining(ActionEvent actionEvent) {
    }

    public void initData(Apprentice apprentice) {
        this.apprentice=apprentice;
        this.textFieldName.setText(apprentice.getName());
        this.textFieldPatronymic.setText(apprentice.getPatronymic());
        this.textFieldSurname.setText(apprentice.getSurname());
        this.textFieldPhone.setText(apprentice.getPhoneNumber());
    }
}
