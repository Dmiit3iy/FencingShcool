package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dmiit3iy.Program;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.retrofit.ApprenticeRepository;

import java.io.IOException;

public class AddApprentice {
    public TextField textFieldSurname;
    public TextField textFieldName;
    public TextField textFieldPatronymic;
    public TextField textFieldPhoneNumber;

    ApprenticeRepository apprenticeRepository = new ApprenticeRepository();

    public void buttonAddApprentice(ActionEvent actionEvent) throws IOException {
        String surname = textFieldSurname.getText();
        String name = textFieldName.getText();
        String patronymic = textFieldPatronymic.getText();
        String phone = textFieldPhoneNumber.getText();

        Apprentice apprentice = new Apprentice(surname,name,patronymic,phone);
        try {
            this.apprenticeRepository.post(apprentice);
            Program.showMessage("Success", "Success adding a apprentice", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            Program.showMessage("Warning", "Error adding a apprentice", Alert.AlertType.WARNING);
        } finally {
            textFieldPhoneNumber.clear();
            textFieldName.clear();
            textFieldPatronymic.clear();
            textFieldSurname.clear();

            Stage stage = (Stage) textFieldName.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/main.fxml"));
            Stage stage1 = new Stage(StageStyle.DECORATED);
            stage1.setScene(new Scene(loader.load()));
            stage1.show();

        }
    }
}
