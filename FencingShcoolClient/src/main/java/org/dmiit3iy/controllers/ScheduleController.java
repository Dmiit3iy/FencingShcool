package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.dmiit3iy.Program;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.retrofit.ScheduleRepository;
import org.dmiit3iy.retrofit.TrainerRepository;

import java.io.IOException;

public class ScheduleController {
    public TableView<TrainerSchedule> tableViewSchedule;

    private ScheduleRepository scheduleRepository = new ScheduleRepository();
    private TrainerRepository trainerRepository = new TrainerRepository();
    private Trainer trainer;
    @FXML
    public TextField textFieldSurname = new TextField();
    @FXML
    public TextField textFieldName = new TextField();
    @FXML
    public TextField textFieldPatronymic = new TextField();
    @FXML
    public TextField textFieldExperience = new TextField();

    @FXML

    public void buttonAddSchedule(ActionEvent actionEvent) {
    }

    @FXML
    public void buttonRemoveSchedule(ActionEvent actionEvent) {
    }

    @FXML
    public void buttonUpdateTrainer(ActionEvent actionEvent) throws IOException {
        trainer.setName(textFieldName.getText());
        trainer.setSurname(textFieldSurname.getText());
        trainer.setPatronymic(textFieldPatronymic.getText());
        trainer.setExperience(Double.parseDouble(textFieldExperience.getText()));
        this.trainerRepository.put(trainer);
        Program.showMessage("Success", "the trainer has been successfully update", Alert.AlertType.INFORMATION);
        clearFields();
    }

    @FXML
    public void buttonRemoveTrainer(ActionEvent actionEvent) throws IOException {
        this.trainerRepository.delete(trainer.getId());
        Program.showMessage("Success", "the trainer has been successfully remove", Alert.AlertType.INFORMATION);
        clearFields();

    }


    public void initData(Trainer trainer) {
        this.trainer = trainer;

        textFieldSurname.setText(trainer.getSurname());
        textFieldName.setText(trainer.getName());
        textFieldExperience.setText(String.valueOf(trainer.getExperience()));
        textFieldPatronymic.setText(trainer.getPatronymic());
    }

    @FXML
    void initialize() {
//        TableColumn<TrainerSchedule,String> dayOfTheWeek = new TableColumn<>("День недели");
//        dayOfTheWeek.setCellValueFactory(new PropertyValueFactory<>());
//        TableColumn<TrainerSchedule, LocalTime> timeStart = new TableColumn<>("Время начала работы");
//        TableColumn<TrainerSchedule,LocalTime> timeEnd = new TableColumn<>("Время окончания работы");
//        tableViewSchedule.setItems();
    }


    public void clearFields() {
        textFieldSurname.clear();
        textFieldName.clear();
        textFieldPatronymic.clear();
        textFieldExperience.clear();

    }

}
