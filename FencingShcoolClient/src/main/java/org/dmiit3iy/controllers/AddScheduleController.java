package org.dmiit3iy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.retrofit.ScheduleRepository;
import org.dmiit3iy.util.Constants;
import org.dmiit3iy.util.Utils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AddScheduleController {
    private TrainerSchedule trainerSchedule;
    @FXML
    public ComboBox<String> comboBoxDays;
    @FXML
    public ComboBox<LocalTime> comboBoxStartTime;
    @FXML
    public ComboBox<LocalTime> comboBoxEndTime;

    ScheduleRepository scheduleRepository = new ScheduleRepository();

//TODO дописать проверки!!!
    public void buttonAddSchedule(ActionEvent actionEvent) throws IOException {
        String day = Utils.convertDaysToEnglish(comboBoxDays.getSelectionModel().getSelectedItem());
        LocalTime start = comboBoxStartTime.getSelectionModel().getSelectedItem();
        LocalTime end = comboBoxEndTime.getSelectionModel().getSelectedItem();
        scheduleRepository.post(trainerSchedule.getId(), day,start,end);
        comboBoxDays.getItems().clear();
        comboBoxEndTime.getItems().clear();
    }

    public void initData(TrainerSchedule trainerSchedule) {
        this.trainerSchedule = trainerSchedule;


    }


    @FXML
    void initialize() {
        List<String> daysRussia = new ArrayList<>();
        for (int i = 0; i < Constants.dayWeek.length; i++) {
            daysRussia.add(Utils.convertDaysToRussia(Constants.dayWeek[i]));
        }
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
