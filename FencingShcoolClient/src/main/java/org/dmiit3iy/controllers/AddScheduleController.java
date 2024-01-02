package org.dmiit3iy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import org.dmiit3iy.App;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.retrofit.ScheduleRepository;
import org.dmiit3iy.util.Constants;


import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddScheduleController {
    private TrainerSchedule trainerSchedule;
    private Trainer trainer;
    @FXML
    public ComboBox<String> comboBoxDays;
    @FXML
    public ComboBox<LocalTime> comboBoxStartTime;
    @FXML
    public ComboBox<LocalTime> comboBoxEndTime;

    ScheduleRepository scheduleRepository = new ScheduleRepository();

    public void buttonAddSchedule(ActionEvent actionEvent) throws IOException {
        if (comboBoxDays.getSelectionModel().getSelectedItem() != null && comboBoxEndTime.getSelectionModel().getSelectedItem() != null
                && comboBoxStartTime.getSelectionModel().getSelectedItem() != null) {
            String day = Constants.weekDays.get(comboBoxDays.getSelectionModel().getSelectedItem());
            LocalTime start = comboBoxStartTime.getSelectionModel().getSelectedItem();
            LocalTime end = comboBoxEndTime.getSelectionModel().getSelectedItem();

            if (start.isBefore(end) && start != end) {
                scheduleRepository.post(trainer.getId(), day, start, end);
                comboBoxDays.getItems().clear();
                comboBoxEndTime.getItems().clear();
            } else {
                App.showMessage("Wow!", "The start time of the working day must start before the end time of the working day", Alert.AlertType.INFORMATION);
            }
        } else {
            App.showMessage("Wow!", "It is necessary to select all checkbox", Alert.AlertType.INFORMATION);
        }
    }

    public void initData(TrainerSchedule trainerSchedule, Trainer trainer) {
        this.trainerSchedule = trainerSchedule;
        this.trainer = trainer;
    }


    @FXML
    void initialize() {
        Set<String> daysRussia = Constants.weekDays.keySet();
        ObservableList<String> days = FXCollections.observableArrayList(daysRussia);
        comboBoxDays.setItems(days);

        List<LocalTime> listLocalTime = new ArrayList<>();
        LocalTime startWorkingDay = LocalTime.of(07, 00);
        for (int i = 0; i < 29; i++) {
            listLocalTime.add(startWorkingDay);
            startWorkingDay = startWorkingDay.plusMinutes(30);
        }

        ObservableList<LocalTime> times = FXCollections.observableArrayList(listLocalTime);
        comboBoxStartTime.setItems(times);
        comboBoxEndTime.setItems(times);
    }
}
