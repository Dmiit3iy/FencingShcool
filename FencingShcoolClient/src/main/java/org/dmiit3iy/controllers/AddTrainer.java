package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.dmiit3iy.Program;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.retrofit.TrainerRepository;

import java.io.IOException;

public class AddTrainer {
    public TextField textFieldSurname;
    public TextField textFieldName;
    public TextField textFieldPatronymic;
    public TextField textFieldExperience;

    TrainerRepository trainerRepository = new TrainerRepository();

    public void buttonCreate(ActionEvent actionEvent) throws IOException {
        String surname = textFieldSurname.getText();
        String name = textFieldName.getText();
        String patronymic = textFieldPatronymic.getText();
        double experience = Double.parseDouble(textFieldExperience.getText());

        Trainer trainer = new Trainer(surname, name, patronymic, experience);
        try {
            this.trainerRepository.post(trainer);
            Program.showMessage("Success", "Success adding a trainer", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            Program.showMessage("Warning", "Error adding a trainer", Alert.AlertType.WARNING);
        } finally {
            textFieldExperience.clear();
            textFieldName.clear();
            textFieldPatronymic.clear();
            textFieldSurname.clear();

            Stage stage = (Stage) textFieldExperience.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/main.fxml"));
            Stage stage1 = new Stage(StageStyle.DECORATED);
            stage1.setScene(new Scene(loader.load()));
            stage1.show();

        }

    }
}
