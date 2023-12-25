package org.dmiit3iy.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dmiit3iy.Program;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.retrofit.ApprenticeRepository;
import org.dmiit3iy.retrofit.TrainerRepository;

import java.io.IOException;

public class AddController {
    @FXML
    public TextField textFieldSurname;
    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldPatronymic;

    @FXML
    public TextField textFieldVar;
    @FXML
    public Label labelVar;
    @FXML
    public RadioButton radButtonTrainer;
    @FXML
    public RadioButton radButtonApprentice;
    ToggleGroup group = new ToggleGroup();
    TrainerRepository trainerRepository = new TrainerRepository();
    ApprenticeRepository apprenticeRepository = new ApprenticeRepository();

    @FXML
    void initialize() {
        radButtonTrainer.setToggleGroup(group);
        radButtonApprentice.setToggleGroup(group);
        radButtonTrainer.setSelected(true);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> changed, Toggle oldValue, Toggle newValue) {
                RadioButton selectedBtn = (RadioButton) newValue;
                if (selectedBtn.getText().equals("Ученик")) {
                    labelVar.setText("Номер телефона");
                } else {
                    labelVar.setText("Опыт работы");
                }
            }
        });
    }


    public void buttonCreate(ActionEvent actionEvent) throws IOException {
        RadioButton selection = (RadioButton) group.getSelectedToggle();
        if (selection.getText().equals("Тренер")) {

            String surname = textFieldSurname.getText();
            String name = textFieldName.getText();
            String patronymic = textFieldPatronymic.getText();
            double experience = Double.parseDouble(textFieldVar.getText());

            Trainer trainer = new Trainer(surname, name, patronymic, experience);
            try {
                this.trainerRepository.post(trainer);
                Program.showMessage("Success", "Success adding a trainer", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                Program.showMessage("Warning", "Error adding a trainer", Alert.AlertType.WARNING);
            }

        } else {
            String surname = textFieldSurname.getText();
            String name = textFieldName.getText();
            String patronymic = textFieldPatronymic.getText();
            String phone = textFieldVar.getText();

            Apprentice apprentice = new Apprentice(surname, name, patronymic, phone);
            try {
                this.apprenticeRepository.post(apprentice);
                Program.showMessage("Success", "Success adding a apprentice", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                Program.showMessage("Warning", "Error adding a apprentice", Alert.AlertType.WARNING);
            }
        }
        textFieldVar.clear();
        textFieldName.clear();
        textFieldPatronymic.clear();
        textFieldSurname.clear();

        Stage stage = (Stage) textFieldVar.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/main.fxml"));
        Stage stage1 = new Stage(StageStyle.DECORATED);
        stage1.setScene(new Scene(loader.load()));
        stage1.show();
    }


}
