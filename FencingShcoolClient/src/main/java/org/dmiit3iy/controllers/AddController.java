package org.dmiit3iy.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.dmiit3iy.App;
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
            String experience = textFieldVar.getText();
            if (surname.isEmpty() || name.isEmpty() || patronymic.isEmpty() || experience.isEmpty()) {
                App.showMessage("Alyarma!", "All field must be filled in!", Alert.AlertType.ERROR);
                return;
            }
            if (!experience.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                App.showMessage("Alyarma!", "experience field must be a number!", Alert.AlertType.ERROR);
                textFieldVar.clear();
                return;
            }
            Trainer trainer = new Trainer(surname, name, patronymic, Double.parseDouble(experience));
            try {
                this.trainerRepository.post(trainer);
                App.showMessage("Success", "Success adding a trainer", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                App.showMessage("Warning", "Error adding a trainer", Alert.AlertType.WARNING);
            }

        } else {
            String surname = textFieldSurname.getText();
            String name = textFieldName.getText();
            String patronymic = textFieldPatronymic.getText();
            String phone = textFieldVar.getText();
            if (surname.isEmpty() || name.isEmpty() || patronymic.isEmpty() || phone.isEmpty()) {
                App.showMessage("Alyarma!", "All field must be filled in!", Alert.AlertType.ERROR);
                return;
            }
            if (!phone.matches("\\d{11}")) {
                App.showMessage("Alyarma!", "Enter the number in the format: 89190000001", Alert.AlertType.ERROR);
                textFieldVar.clear();
                return;
            }
            Apprentice apprentice = new Apprentice(surname, name, patronymic, phone);
            try {
                this.apprenticeRepository.post(apprentice);
                App.showMessage("Success", "Success adding a apprentice", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                App.showMessage("Warning", "Error adding a apprentice", Alert.AlertType.WARNING);
            }
        }
        textFieldVar.clear();
        textFieldName.clear();
        textFieldPatronymic.clear();
        textFieldSurname.clear();

    }
}
